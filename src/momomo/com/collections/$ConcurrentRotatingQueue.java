package momomo.com.collections;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A queue, that rotates on access, so that we can use a base set of configuration and then rotate it's use. 
 * 
 * @author Joseph S.
 */
public final class $ConcurrentRotatingQueue<T> {
    private final ConcurrentLinkedQueue<T> queue;
    
    public $ConcurrentRotatingQueue() {
        queue = new ConcurrentLinkedQueue<>();
    }
    
    public $ConcurrentRotatingQueue(T... tees) {
        this();
        add(tees);
    }
    
    public synchronized $ConcurrentRotatingQueue add(T ... objects) {
        for (T object : objects) {
            add(object);
        }
        return this;
    }
    
    public synchronized T next() {
        T object = queue.poll();
        add(object);    // Add last again
        return object;
    }
    
    private synchronized void add(T object) {
        queue.add(object);
    }
    
}
