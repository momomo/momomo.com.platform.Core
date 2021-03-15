package momomo.com;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Joseph S.
 */
public class $Executors {
    
    /**
     * Used by our executor service implementation
     */
    public static void shutdown(String name, ExecutorService executor) {
        String tmp = "Executor: " + name + " -> ";
        
        try {
            log.info(Threads.class, tmp, "Shutting down.");
            
            executor.shutdown();
            
            if ( !executor.awaitTermination (Runtimes.OUR_SHUTDOWN_HOOK_LAST_WAIT_FIRST /2, TimeUnit.MILLISECONDS) ) {
                log.info(Threads.class, tmp, "Shutdown could not be accomplished gracefully. Terminating using force: executor.shutdownNow()");
                executor.shutdownNow();
            }
            else {
                log.info(Threads.class, tmp, "Gracefully shutdown.");
            }
            
        } 
        catch (Throwable e) {
            log.info( Threads.class, tmp, "Shutting down executor failed completely. We should in theory never end up here!" );
        }
    }

    /**
     * Mostly for reference. 
     */
    @momomo.com.annotations.informative.Development public static final class Development {
        
        public static ForkJoinPool common() {
            return ForkJoinPool.commonPool();
        }

        /**
         * Executors.newFixedThreadPool(1);
         */
        public static ThreadPoolExecutor threadpool(int minThreads, int maxThreads, long keepAlive ) {
            return new ThreadPoolExecutor( minThreads, maxThreads, keepAlive, TimeUnit.SECONDS, new LinkedBlockingQueue<>() );  
        }

        public static ScheduledThreadPoolExecutor scheduled(int threads) {
            return new ScheduledThreadPoolExecutor(threads);
        }

        public static void setThreads(ThreadPoolExecutor executor, int threads){
            if ( executor.getCorePoolSize() != threads ) {
                executor.setCorePoolSize(threads);
            }
            if ( executor.getMaximumPoolSize() != threads ) {
                executor.setMaximumPoolSize(threads);
            }
        } 
    }
    
    
}
