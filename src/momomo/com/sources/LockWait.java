package momomo.com.sources;

/**
 * <p>
 * This lock is different from ReentrantLock in that it blocks all threads, even the first one.
 * <p>
 * Is appropiate when another thread might do the unlocking and you wish to put yourself in a wait condition.
 * <p>
 * Synchronized here might be unnessary but better be safe than sorry.
 * 
 * Seems this lock now just delegates to LockCount and is intended to only lock and unlock rather than increment and decerement as well.  
 *
 * @author Joseph S.
 */
public final class LockWait implements Lock<LockWait>, Lock.Await {
    private final LockCount delegate;
    
    public LockWait() {
        delegate = new LockCount();
    }
    
    @Override
    public LockWait cancel() {
        delegate.cancel(); return this;
    }
    
    @Override
    public LockWait lock() {
        delegate.lock(); return this;
    }
    
    @Override
    public LockWait unlock() {
        delegate.unlock(); return this;
    }
    
    @Override
    public Status await(Long start, long remaining) {
        return delegate.await(start, remaining);
    }
    
}

