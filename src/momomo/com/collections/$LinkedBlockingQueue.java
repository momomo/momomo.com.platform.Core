package momomo.com.collections;

import momomo.com.log;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * This LinkedBlockinQueue overrides the offer method which normally waits and blocks execution when size limit is reached instead of rejecting the work which this one does.
 *
 * Is better to use to prevent recursive deadlocks. 
 *
 * @author Joseph S.
 */
public class $LinkedBlockingQueue<E> extends LinkedBlockingQueue<E> {
    
    public $LinkedBlockingQueue(int maxSize) {
        super(maxSize);
    }
    
    @Override
    public boolean offer(E e) {
        try {
            put(e);                 // turn offer() and add() into blocking calls (unless interrupted)
            return true;
        } catch (InterruptedException exception) {
            log.error(getClass(), exception);
            Thread.currentThread().interrupt();
        }
        return false;
    }
}
