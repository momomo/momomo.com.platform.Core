package momomo.com;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class function basically replaces static { code here } blocks use in a class which can become rather complex if you need to execute different things at different times
 *
 * Note, if you insist on using calling Call.once(Class::MethodReference) it can only be safely done from the same location.
 *
 * So
 *      void configure() {
 *          Call.once(Class::someMethod);
 *          Call.once(Class::someMethod);
 *          Call.once(Class::someMethod);
 *      }
 *
 * won't work since it is basically the same as
 *
 *  void configure() {
 *      Call.once(()->{
 *          Class.someMethod();
 *      });
 *
 *      Call.once(()->{
 *          Class.someMethod();
 *      });
 *
 *      Call.once(()->{
 *          Class.someMethod();
 *      });
 *  }
 *
 *  and the lambdas hashcode is used to figure out if something has already been executed or not
 *
 * However
 *  void configure() {
 *     abc();
 *     abc();
 *     abc();
 *  }
 *
 *  void abc() {
 *    Call.once(()->{
 *       Class.someMethod();
 *    });
 *  }
 *
 *  @author Joseph S.
 */
public final class Call {
    
    private static final ConcurrentHashMap<Lambda.VE<? extends Exception>, AtomicBoolean> CACHE = new ConcurrentHashMap<>();
    
    public static <E extends Exception> void once(Lambda.VE<E> lambda) throws E {
        once(lambda, null);
    }
    
    public static <E extends Exception> void once(Lambda.VE<E> lambda, String key) throws E {
        once(lambda, false, key);
    }
    
    public static <E extends Exception> void once(Lambda.VE<E> lambda, boolean force) throws E {
        once(lambda, force, null);
    }
    
    public static <E extends Exception> void once(Lambda.VE<E> lambda, boolean force, String key) throws E {
        if (Is.Ok(key)) log.info(Runtimes.class, "Attempting to execute once: " + key);
        
        // We do not execute in compute as that would lock up the map and lead to deadlock issues for nested recalls to here with the same key
        if (!CACHE.computeIfAbsent(lambda, k -> new AtomicBoolean()).getAndSet(true) || force == true) {
            if (Is.Ok(key)) log.info(Runtimes.class, "Began    Executing once: " + key);
            lambda.call();
            if (Is.Ok(key)) log.info(Runtimes.class, "Finished Executing once: " + key);
        }
    }
    
}
