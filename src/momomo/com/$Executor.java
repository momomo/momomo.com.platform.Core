package momomo.com;

import momomo.com.sources.LockCount;
import momomo.com.annotations.informative.Beta;
import momomo.com.collections.$LinkedBlockingQueue;
import momomo.com.exceptions.$RuntimeException;
import momomo.com.sources.Wrap;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Lots of functionality for executor related stuff.
 * 
 * Concurrently safe. 
 * 
 * Example  
 *      executor = new $Executor("NASDAQ_DOWNLOADER", $Runtimes.getAvailableProcessors()).setRate(1000, TimeUnit.HOURS) // 1000 per hour usingsay 16 threads
 *      
 *      // When a warning starts to occur, we can pause
 *      executor.pauseOn(TimeUnit.MINUTES.toMillis(10))
 *      executor.pauseOff()
 *      
 *      // When a warning starts to occur, we can modify the rate any time
 *      // Amount of requests we want to ALLOW per TimeUnit 
 *      executor.setRate( ... )
 *      
 *      // Amount of time between each request
 *      executor.setInterval( ... )
 *      
 *      executor.increment()
 *      executor.decrement()
 *      
 *      // Wait until decrement() == 0 
 *      executor.await() 
 *      
 *      executor.async( ... ) 
 *      
 * @author Joseph S.
 */
@Beta("Could need some refactoring and better method delegation since implementation is kind of old but still good.") public final class $Executor {
    private final $ThreadFactory  threadFactory;
    private final Queue<Runnable> queue;                        // Currenty not used but is the queue passed to ThreadPoolExecutor so we have a reference to it here
    
    private final LockCount counter   = new LockCount();
    private final AtomicLong completed = new AtomicLong();
    
    public  final String     name;
    private       long       interval         = 0;
    private       Long       originalInterval = null;
    
    private final AtomicBoolean pause        = new AtomicBoolean(false);
    private final AtomicBoolean invervalSync = new AtomicBoolean(true );
    private final AtomicLong    intervalLock = new AtomicLong   (0);
    
    private final AbstractExecutorService executor;
    
    public $Executor(String name, int threads, BlockingQueue<Runnable> queue) {
        this.name          = name;
        this.queue         = queue;
        this.threadFactory = new $ThreadFactory(name);
        this.executor      = new ThreadPoolExecutor(threads, threads, 0L, TimeUnit.MILLISECONDS, queue, this.threadFactory);
        
        Runtimes.onShutdown(() -> {
            $Executors.shutdown(this.name, this.executor);
        });
    }
    
    public $Executor(String name, int threads, int maxSize) {
        this(name, threads, new $LinkedBlockingQueue<>(maxSize));
    }
    
    public $Executor(String name, int threads) {
        this(name, threads, new $LinkedBlockingQueue<>(Integer.MAX_VALUE));
    }
    
    /////////////////////////////////////////////////////////////////////
    public $Executor async(Lambda.V1E<Integer, ? extends Exception> onEach) {
        return async(0, 1, null, onEach, null, null, null, null);
    }
    public $Executor async(Lambda.VE<? extends Exception> onEach) {
        return async((i) -> { onEach.call(); } );
    }
    /////////////////////////////////////////////////////////////////////
    
    /////////////////////////////////////////////////////////////////////
    public $Executor async(
        int start, int end,
        Lambda.V1E<Integer, ? extends Exception> onEach,
        Double onPercentage,
        Lambda.VE<? extends Exception> onPercentageRun
    ) {
        return async(start, end, onEach, onPercentage, onPercentageRun, null);
    }
    public $Executor async(
        int start, int end,
        Lambda.V1E<Integer, ? extends Exception> onEach,
        Double onPercentage,
        Lambda.VE<? extends Exception> onPercentageRun,
        Lambda.VE<? extends Exception> onComplete
    
    ) {
        return async(start, end, null, onEach, onPercentage, onPercentageRun, onComplete, null);
    }
    
    /**
     * Unfortunately, executor uses the offer method which only places stuff at the end of the runnable queue, \
     * meaning there is no easy way or good way to place runnables at the beginning of it.
     *
     * However this can be done by adding a flag and changing the offer method and using a LinkedBlockingDeque instead, however that doesnt sound to sexy either and potentially dangerous.
     *
     * Instead we just execute it using the common thread pool.
     *
     * @deprecated do not use until it does what it says using LinkedBlockingDeque
     */
    public $Executor asyncImmediately(Lambda.VE<? extends Exception> onEach) {
        increment();
        Threads.async( () -> {
            try {
                onAsyncImmediately(onEach);
            }
            finally {
                decrement();
            }
        } ); return this;
    }
    
    /////////////////////////////////////////////////////////////////////
    
    public CompletableFuture<Void> future(Lambda.VE<? extends Exception> onEach) {
        return CompletableFuture.runAsync(
            createRunnable(
                0, (index) -> onEach.call(),
                null,
                false, null, null, null, false, null, null, null, null
            ),
            executor);
    }
    
    /**
     * Basically, delegate means that we use the executor to queue up for the work, but the work will actually be performed in the original
     * calling thread. The executor will though hold the executor thread until execution has finished.
     * Recursive calls or submitting calls to the same executor will lead to a queue up which eventually put all threads in wait mode, waiting for each other, so be careful with deadlocks.
     * This is true for all recursive calls and the use of executors.
     */
    public <R, E extends Exception> R delegate(Lambda.RE<R, E> lambda) throws E {
        R r;
        final AtomicBoolean         lock    = new AtomicBoolean(false);
        final Wrap<Lambda.RE<R, E>> wrapper = Wrap.it();
        
        try {
            async(() -> {
                // Notify that the runnable is now ready to execute
                synchronized (wrapper) {
                    wrapper.it = lambda;
                    wrapper.notify();
                }
                
                // Wait for the runnable to fully execute
                synchronized (lock) {
                    while (!lock.get()) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new $RuntimeException(e);
                        }
                    }
                }
                
            });
            
            // We wait here until the wrapper has been set. When it does, we exit the while and execute in the originating thread
            synchronized (wrapper) {
                while ( wrapper.it == null ) {
                    try {
                        wrapper.wait();
                    } catch (InterruptedException e) {
                        throw new $RuntimeException(e);
                    }
                }
            }
            
            r = wrapper.it.call();
        }
        finally {
            // Release the lock submitted to the executor. Needs to be done after call() has finished executing, and independently of there being an error
            synchronized (lock ){
                lock.set(true);
                lock.notify();
            }
        }
        
        return r;
        
    }
    
    public void delegate(Runnable runnable) {
        delegate(() -> {
            runnable.run();
            return null;
        });
    }
    
    public <T> void await(List<T> list, Lambda.V1<T> lambda) {
        if ( Is.Ok(list) ) {
            await(list.size(), (j) -> {
                lambda.call( list.get(j) );
            });
        }
    }
    
    /**
     * Will wait until all count * lambda.call() has all finished executing.
     */
    public void await(int end, Lambda.V1<Integer> lambda) {
        // We submit each one to the executor, while waiting for all to finish before we release this thread which has to wait below
        AtomicInteger done = new AtomicInteger(end);
        
        int i = 0;
        while ( i < done.get() ) {
            final int j = i;        // Needed to prevent lambda from seeing an altered i
            
            async(() -> {
                try {
                    lambda.call(j);
                }
                finally {
                    Threads.decrement(done);
                }
            });
            
            ++i;
        }
        
        // This thread needs to wait for all downloadables to finish processing before it can continue and release this one. 
        Threads.await(done);
    }
    
    /////////////////////////////////////////////////////////////////////
    
    /////////////////////////////////////////////////////////////////////
    public $Executor async(
        int start, int end,
        
        // Pretty stupid, but it does a check on wether it will actually invoke anything from the async call, and if so, before will be called prior, once.
        Lambda.VE<? extends Exception> before,
        Lambda.V1E<Integer, ? extends Exception> onEach,
        Double onPercentage,
        Lambda.VE<? extends Exception> onPercentageRunLambda,
        Lambda.VE<? extends Exception> onCompleteRunLambda,
        Lambda.VE<? extends Exception> afterEach
    ) {
        AtomicBoolean percentageMet     = new AtomicBoolean ( false );
        AtomicBoolean completeMet       = new AtomicBoolean ( false );
        AtomicInteger done              = new AtomicInteger ( start );
        
        double        onPercentageRunAt = (Is.Or(onPercentage, 0.0) * (end - start)) + start; // When to onPercentageRun
        
        boolean percentageSet           = onPercentageRunLambda != null && onPercentageRunAt > start;
        boolean completeSet             = onCompleteRunLambda != null;
        
        if ( start < end && before != null ) {
            before(before);
        }
        
        Runtimes.pauseIfShuttingDown();
        while (start < end) {
            
            executor.execute(
                createRunnable(
                    start,
                    onEach,
                    done,
                    completeSet, completeMet, end, onCompleteRunLambda,
                    percentageSet, percentageMet, onPercentageRunAt, onPercentageRunLambda, afterEach
                )
            );
            
            start++;
        }
        
        return this;
    }
    
    private Runnable createRunnable(
        int index,
        Lambda.V1E<Integer, ? extends Exception> onEach,
        AtomicInteger done,
        boolean completeSet, AtomicBoolean completeMet, Integer onCompleteRunAt, Lambda.VE<? extends Exception> onCompleteRunLambda,
        boolean percentageSet, AtomicBoolean percentageMet, Double onPercentageRunAt, Lambda.VE<? extends Exception> onPercentageRunLambda, Lambda.VE<? extends Exception> afterEach
    ) {
        increment();
        
        return () -> {
            try {
                intervalWait();
                
                onEach(onEach, index);
                
                if (percentageSet || completeSet) {
                    done.incrementAndGet();
                    
                    if (percentageSet && !percentageMet.get() && done.get() >= onPercentageRunAt) {
                        percentageMet.set(true);
                        
                        onPercentage(onPercentageRunLambda);
                    }
                    
                    if (completeSet && !completeMet.get() && done.get() == onCompleteRunAt) {
                        completeMet.set(true); // Just for clarity
                        
                        onComplete(onCompleteRunLambda);
                    }
                }
                
                if ( afterEach != null ) {
                    afterEach(afterEach);
                }
                
            }
            catch (Throwable e) {
                log.fatal(getClass(), name() + e);     // We should never end up here
            }
            finally {
                decrement();
            }
        };
    }
    
    public final String name() {
        return threadFactory.name;
    }
    
    private void before(Lambda.VE<? extends Exception> before) {
        try {
            pauseCheck();
            Runtimes.pauseIfShuttingDown();
            before.call();
            pauseCheck();
            Runtimes.pauseIfShuttingDown();
            
        } catch (Throwable e) {
            Runtimes.pauseIfShuttingDown();
            log.error(getClass(), "before", e);
        }
    }
    
    private void onEach(Lambda.V1E<Integer, ? extends Exception> onEach, int index) {
        try {
            pauseCheck();
            Runtimes.pauseIfShuttingDown();
            onEach.call(index);
            pauseCheck();
            Runtimes.pauseIfShuttingDown();
            
        } catch (Throwable e) {
            Runtimes.pauseIfShuttingDown();
            log.error(getClass(), "onEach", e);
        }
    }
    private void onPercentage(Lambda.VE<? extends Exception> onPercentageRun) {
        try {
            pauseCheck();
            Runtimes.pauseIfShuttingDown();
            onPercentageRun.call();
            pauseCheck();
            Runtimes.pauseIfShuttingDown();
            
        } catch (Throwable e) {
            Runtimes.pauseIfShuttingDown();
            log.error(getClass(), "onPercentage", e);
        }
    }
    private void onComplete(Lambda.VE<? extends Exception> onComplete) {
        try {
            pauseCheck();
            Runtimes.pauseIfShuttingDown();
            onComplete.call();
            pauseCheck();
            Runtimes.pauseIfShuttingDown();
        } catch (Throwable e) {
            Runtimes.pauseIfShuttingDown();
            log.error(getClass(), "onComplete", e);
        }
    }
    private void afterEach(Lambda.VE<? extends Exception> afterAll) {
        try {
            pauseCheck();
            Runtimes.pauseIfShuttingDown();
            afterAll.call();
            pauseCheck();
            Runtimes.pauseIfShuttingDown();
        } catch (Throwable e) {
            Runtimes.pauseIfShuttingDown();
            log.error(getClass(), "afterAll", e);
        }
    }
    private void onAsyncImmediately(Lambda.VE<? extends Exception> runnable) {
        try {
            pauseCheck();
            Runtimes.pauseIfShuttingDown();
            runnable.call();
            pauseCheck();
            Runtimes.pauseIfShuttingDown();
            
        } catch (Throwable e) {
            Runtimes.pauseIfShuttingDown();
            log.error(getClass(), "asyncFirst", e);
        }
    }
    
    
    /////////////////////////////////////////////////////////////////////
    public $Executor setInterval(long interval, TimeUnit timeUnit) {
        this.interval = timeUnit.toNanos(interval);
        
        if ( this.originalInterval == null ) {
            this.originalInterval = this.interval;
        }
        
        return this;
    }
    
    /**
     * This one allows you to set the number of requests per second that you wish to perform.
     *
     * It differs from setRate(long, TimeUnit) as being the inverse.
     *
     * setInterval(long, TimeUnit) sets the interval for how often threads are allowed to execute, ie every 1 request / 10 seconds.
     *
     * This one lets you say how often per second. For instance 1 request / 10 seconds is the same as a 0.1 requests / second where 0.1 can be supplied here.
     *
     * This is therefore a convience method to prevent the incorrect transformation that are common because of rounding errors.
     */
    public $Executor setRate(long rate, TimeUnit timeUnit) {
        return setInterval(
            toInterval(rate, timeUnit),
            
            TimeUnit.NANOSECONDS
        );
    }
    
    /**
     * If original speed is 5 requests per seconds then 0.2 means minimum of 1 request per second ,and a maximum speed 5.0 means 25 requests per second
     *
     * @param percentage  is the change desired
     *
     * @return true for changed, false for unchanged if boundaries was not adhered to
     */
    public boolean setRate(double percentage, double minimum, double maximum) {
        // No point in altering anything for 1
        if ( percentage != 1 && percentage > 0 && minimum > 0 && maximum > 0 && interval > 0 && originalInterval != null ) {
            return setInterval(
                
                // ceil so that the new value always higher than what floor could have been
                (long) Math.ceil(((double) this.interval) * (1d / percentage)),
                
                // floor so that the speed is not lower than minimum. ie, 1000.1 will become 1000 and not 1001
                (long) Math.floor(this.originalInterval * (1d / minimum)),
                
                // ceil so that fastest speed if 5.1 meaning 200 * 0.1965 = 39.2 is set to 40 so that speed does not go faster by setting it to 39.
                (long) Math.ceil(this.originalInterval * (1d / maximum) ),
                
                TimeUnit.NANOSECONDS
            
            );
        }
        
        return false;
    }
    private boolean setInterval(long newInterval, long minInterval, long maxInterval, TimeUnit timeUnit) {
        if ( newInterval >  minInterval ) {
            newInterval = minInterval;
        }
        if ( newInterval < maxInterval ) {
            newInterval = maxInterval;
        }
        
        if ( newInterval != this.interval ) {
            setInterval(newInterval, timeUnit);
            
            return true;
        }
        
        return false;
    }
    
    public static long toInterval(long rate, TimeUnit timeUnit) {
        return (long) Math.ceil((double) timeUnit.toNanos(1) / rate);
    }
    
    /////////////////////////////////////////////////////////////////////
    
    /////////////////////////////////////////////////////////////////////
    /**
     * Can be used by outside processes to signal that a process is part of this executors workload and should wait for completion of work ( when decrement is called )
     */
    public void increment() {
        log.debug(getClass(), name(), " Increment count: ", counter.increment());
    }
    
    /**
     * Can be used by outside processes to signal that a process is part of this executors workload and should wait for completion of work ( when decrement is called )
     */
    public void decrement() {
        log.debug(getClass(), name(), " Decrement count: ", counter.decrement());
    }
    
    public void await() {
        Ex.interrupted(()-> counter.await());
    }
    /////////////////////////////////////////////////////////////////////
    
    /////////////////////////////////////////////////////////////////////
    // We do allow for the use of this from outside
    // There is no need for a lock since everybody is allowed to wait, either they get in or they wait outside
    public void pauseCheck() throws InterruptedException {
        if ( pause.get() ) {
            synchronized (pause) {
                while (pause.get()) {
                    pause.wait();   // If this is on, then we wait indefentely until someone tells us to wake up
                }
            }
        }
    }
    
    public void pauseOn() {
        pauseOn(null);
    }
    
    /**
     * TODO Should the current thread also sleep, and be woken up later? Perhaps when the thread is part of this executor, which might not be easily detectable.
     */
    public void pauseOn(Long millis) {
        if ( !pause.getAndSet(true) ) {
            if (millis != null) {
                Threads.async(() -> {
                    log.info(getClass(), name(), "Pause on: ", millis);
                    Threads.sleep(millis);
                    log.info(getClass(), name(), "Pause off: ", millis);
                    pauseOff();
                });
            }
        }
    }
    
    public void pauseOff() {
        if ( pause.get() ) {
            synchronized (pause) {
                pause.set(false);
                pause.notifyAll();
            }
        }
    }
    
    public boolean isPaused() {
        return pause.get();
    }
    
    public $Executor intervalWait() throws InterruptedException {
        if (interval > 0) {
            
            // We synchronize on a separate lock so that only one thread at a time can enter this area. Using of a lock is neccessary if wait and notify is to be used,
            // since synchronized(object) .. object.wait will release the synchronized block on object.notify and we must use another one.
            
            synchronized (intervalLock) {
                
                // We synchronize on this object because we also use it to notify when not to wait anymore
                synchronized (invervalSync) {
                    long last      = intervalLock.get();
                    long remaining = interval - (System.nanoTime() - last);
                    
                    // MONITOR_INTERVAL.get() call is needed here and not redundanct. Otherwise race conditions between the LOCK_INTERVAL.lock() in the finally
                    // block and other threads calling intervalSkip() prior might occur
                    while (invervalSync.get() && remaining > 0) {
                        long milli = TimeUnit.NANOSECONDS.toMillis(remaining);
                        
                        // The monitor can for unclear reasons wake up the thread too early indeed, why we need a while loop
                        invervalSync.wait(milli, (int) (remaining - TimeUnit.MILLISECONDS.toNanos(milli)));
                        
                        // If a notify triggered this awaikening, then the monitor flag would be false
                        // If not, we need check the time again to determine if enough time has elapsed
                        if ( invervalSync.get() ) {
                            remaining = interval - (System.nanoTime() - last);
                        } else {
                            remaining = 0;
                        }
                    }
                }
                
                if ( true || !Is.Production() ) {
                    log.info(getClass(), name(), " Time: " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - this.intervalLock.get()) );
                }
                
                intervalLock.set(System.nanoTime());
                invervalSync.set(true);        // Important that we lock immediately upon exit!
            }
            
        }
        
        return this;
    }
    
    /////////////////////////////////////////////////////////////////////
    
    
    /**
     * Can be used to signal from an executing runnable.
     * Note this method is not currently thread safe. If several threads call this "at the same time" a permanent lock might occur.
     * To resolve this one would have to mimic the pattern used in intervalCheck using a reentrant lock prior to the synchronized block.
     */
    public void intervalSkip() {
        if (interval > 0) {
            synchronized (invervalSync) {
                invervalSync.set(false);
                invervalSync.notify();  // Should never be more than one thread waiting, so this should result in releasing the waiting thread
            }
        }
    }
    
    public long getInterval() {
        return interval;
    }
    
    public long getOriginalInterval() {
        return originalInterval;
    }
    
    public long getRate() {
        return toInterval(getInterval(), TimeUnit.NANOSECONDS);
    }
    
    /*******************************************************************************************************************************************************
     *******************************************************************************************************************************************************
     *******************************************************************************************************************************************************
     *******************************************************************************************************************************************************
     *******************************************************************************************************************************************************
     *******************************************************************************************************************************************************
     *******************************************************************************************************************************************************
     *******************************************************************************************************************************************************
     *******************************************************************************************************************************************************
     *******************************************************************************************************************************************************
     *******************************************************************************************************************************************************
     *******************************************************************************************************************************************************
     *******************************************************************************************************************************************************
     *******************************************************************************************************************************************************
     *******************************************************************************************************************************************************
     *******************************************************************************************************************************************************
     *******************************************************************************************************************************************************
     *******************************************************************************************************************************************************
     *******************************************************************************************************************************************************
     *******************************************************************************************************************************************************
     *******************************************************************************************************************************************************
     *******************************************************************************************************************************************************
     *******************************************************************************************************************************************************
     *******************************************************************************************************************************************************
     *******************************************************************************************************************************************************
     *******************************************************************************************************************************************************
     *******************************************************************************************************************************************************
     *******************************************************************************************************************************************************
     *******************************************************************************************************************************************************
     *******************************************************************************************************************************************************
     *******************************************************************************************************************************************************
     *******************************************************************************************************************************************************
     *******************************************************************************************************************************************************/
    
    private static final class $ThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber   = new AtomicInteger ( 1 );
        private        final ThreadGroup   group;
        private        final AtomicInteger threadNumber = new AtomicInteger ( 1 );
        private        final String        name;
        
        private static final String DEFAULT_NAME = "$ThreadFactory";
        
        $ThreadFactory() {
            this(DEFAULT_NAME);
        }
        
        $ThreadFactory(String name) {
            if (name == null) name = DEFAULT_NAME;
            
            SecurityManager securityManager = System.getSecurityManager ( );
            group     = (securityManager != null) ? securityManager.getThreadGroup () : Thread.currentThread().getThreadGroup ( );
            this.name = poolNumber.getAndIncrement() + ":" + name;
        }
        
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(group, runnable, name + ", Thread: " + threadNumber.getAndIncrement(), 0);
    
            // We set all threads to be daemon threads
            Threads.setDaemon(thread);
            
                        /*
                                if (t.isDaemon())
                                t.setDaemon(false);
                        */
            
            if (thread.getPriority() != Thread.NORM_PRIORITY) {
                thread.setPriority(Thread.NORM_PRIORITY);
            }
            return thread;
        }
    }
    
    
    /////////////////////////////////////////////////////////////////////
}
