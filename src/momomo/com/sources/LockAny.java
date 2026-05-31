package momomo.com.sources;

import momomo.com.Lambda;
import momomo.com.annotations.informative.Overridable;
import momomo.com.exceptions.$InterruptedException;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Can easily lock/unlock on object by containing them in a concurrent hashmap, which can be locked, awaited, and unlocked on the fly, rather than having to declare wrapper classes for each containing the object and the lock.
 * 
 * Example: 
 * 
 *  private final $LockMany locks = new $LockMany() {
 *     @Override
 *     protected void onLock() {
 *         onLock.publish();
 *     }
 *     
 *     @Override
 *     protected void onUnlock() {
 *         onUnlock.publish();
 *     }
 *  }.lock(process).lock(connection);
 *  
 *  locks.unlock(process);
 *  locks.lock(connection);
 *  
 *  locks.unlock(process);
 *  locks.unlock(connection);
 *
 * @author Joseph S.
 */
public class LockAny {
    private final ConcurrentHashMap<Object, Boolean> locks = new ConcurrentHashMap<>();
    
    private volatile boolean isLocked = false;
    
    public final LockAny await() {
        synchronized (locks) {
            while ( isLocked ) {
                try {
                    locks.wait();
                } catch ( InterruptedException e ) {
                    throw new $InterruptedException(e);
                }
            }
        }
        return this;
    }
    
    public final LockAny lock(Object key) {
        return lock(key, null);
    }
    
    public final LockAny lock(Object key, Lambda.V onLock) {
        locks.compute(key, (k, lock) -> {
            
            synchronized (locks) {
                isLocked = true;
                
                if ( onLock != null ) onLock.call();
                
                onLock();
            }
            
            return Boolean.TRUE;
        });
        
        return this;
    }
    
    /**
     * Does not lock on creation, default is unlocked
     */
    public final LockAny add(Object key) {
        locks.computeIfAbsent(key, k -> {
            return Boolean.FALSE;
        });
        
        return this;
    }
    
    public final void unlock(Object key) {
        unlock(key, null);
    }
    
    public final void unlock(Object key, Lambda.V onUnlocked) {
        locks.computeIfPresent(key, (k, lock) -> {
            
            // No others are locked, so we unlock
            if ( locks.size() == 1 ) {
                
                synchronized (locks) {
                    isLocked = false;
                    locks.notifyAll();
                }
                
                if ( onUnlocked != null ) onUnlocked.call();
                
                onUnlock();
            }
            
            return null;    // Remove
        });
        
    }
    
    public final boolean isLocked() {
        return isLocked;
    }
    
    @Overridable
    protected void onUnlock() {}
    
    @Overridable
    protected void onLock() {}
    
}
