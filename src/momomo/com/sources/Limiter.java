package momomo.com.sources;

import momomo.com.Lambda;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Is to be used when a certain action should not be invoked again if interval since last time has not elapsed.
 * 
 * It will cache the return value, so multiple calls will still be able to get last invoked return value.
 * 
 * So all calls in between will simply be ignored, and the return value from the last invocation will be used for those requiring a return value. 
 *
 * Note, we use nano resolution!
 * 
 * Example  
 *   LIMITER = new Limiter.VE(100000, (...) -> { ... })
 *   
 *   LIMITER.call()
 *   
 * Note, that this is not the same as throttling.    
 *
 * @author Joseph S.
 */
public abstract class Limiter<R, P, E extends Exception> {
    private static final boolean SYNCHRONIZE = false;
    
    private final AtomicLong last;
    private final long       nanos;
    private final boolean    synchronize;
    
    protected final Lambda.R1E<R, P, E> lambda;
    private         R                   r;
    
    protected Limiter(long nanos, Lambda.R1E<R, P, E> lambda, boolean syncronize) {
        this.last        = new AtomicLong();
        this.nanos       = nanos;
        this.lambda      = lambda;
        this.synchronize = syncronize;
    }
    protected Limiter(long nanos, Lambda.V1E<P, E> lambda, boolean syncronize) {
        this(nanos, lambda.R1E(), syncronize);
    }
    
    public R call() throws E {
        return call(null);
    }
    
    // Syncrhonize can be set to ensure that all calls has to complete before the next one, whereas without the nano time will always ensure a call, even if there is already a running, previous call
    public R call(P param) throws E {
        if ( synchronize ) {
            synchronized(last) {
                return privat(param);
            }
        }
        else {
            return privat(param);
        }
    }
    
    private R privat( P param ) throws E {
        if ( System.nanoTime() - last.get() < nanos ) {
            return r;
        }
        
        last.set(System.nanoTime());
        return r = lambda.call(param);
    }
    
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    
    public static final class VE<E extends Exception> extends Limiter<Object, Object, E> {
        public VE(long nanos, Lambda.VE<E> lambda) {
            this(nanos, lambda, SYNCHRONIZE);
        }
        public VE(long nanos, Lambda.VE<E> lambda, boolean synchronize) {
            super(nanos, lambda.V1E(), synchronize);
        }
    }
    
    /////////////////////////////////////////////////////////////////////
    
    public static final class V1E<P, E extends Exception> extends Limiter<Object, P, E> {
        public V1E(long nanos, Lambda.V1E<P, E> lambda) {
            this(nanos, lambda, SYNCHRONIZE);
        }
        public V1E(long nanos, Lambda.V1E<P, E> lambda, boolean synchronize) {
            super(nanos, lambda, synchronize);
        }
    }
    
    /////////////////////////////////////////////////////////////////////
    
    public static final class RE<R, E extends Exception> extends Limiter<R, Object, E> {
        public RE(long nanos, Lambda.RE<R, E> lambda) {
            this(nanos, lambda, SYNCHRONIZE);
        }
        public RE(long nanos, Lambda.RE<R, E> lambda, boolean synchronize) {
            super(nanos, lambda.V1E(), synchronize);
        }
    }
    
    /////////////////////////////////////////////////////////////////////
    
    public static final class R1E<R, P, E extends Exception> extends Limiter<R, P, E> {
        public R1E(long nanos, Lambda.R1E<R, P, E> lambda) {
            this(nanos, lambda, SYNCHRONIZE);
        }
        public R1E(long nanos, Lambda.R1E<R, P, E> lambda, boolean synchronize) {
            super(nanos, lambda, synchronize);
        }
    }
    
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    
}
