
package momomo.com.collections;

import java.io.Serializable;
import java.util.*;

/**
 * Allows you to call get with o(1) instead of o(n) to get an instance by index.
 * Note, remove still costs o(n). 
 *
 * @author Joseph S.
 */
public final class $ConcurrentIndexedLinkedHashSet<T> implements Set<T>, Serializable {
    
    private final ArrayList<T> list = new ArrayList<>( );
    private final HashSet<T>   set  = new HashSet<>  ( );
    
    @Override
    public synchronized boolean add(T e) {
        if ( set.add(e) ) {
            return list.add(e);
        }
        return false;
    }
    
    @Override
    public synchronized boolean remove(Object o) {
        if ( set.remove(o) ) {
            return list.remove(o);
        }
        return false;
    }
    
    @Override
    public synchronized boolean containsAll(Collection<?> c) {
        return set.containsAll(c);
    }
    
    @Override
    public synchronized void clear() {
        set.clear(); list.clear();
    }
    
    @Override
    public synchronized boolean removeAll(Collection<?> c) {
        if ( set.removeAll(c) ) {
            return list.removeAll(c);
        }
        return true;
    }
    
    @Override
    public synchronized boolean retainAll(Collection<?> c) {
        if ( set.retainAll(c) ) {
            return list.retainAll(c);
        }
        return false;
    }
    
    @Override
    public synchronized boolean addAll(Collection<? extends T> c) {
        boolean modified = false;
        for (T e : c)
            if (add(e))
                modified = true;
        return modified;
    }
    
    @Override
    public synchronized int size() {
        return list.size();
    }
    
    @Override
    public synchronized boolean isEmpty() {
        return list.isEmpty();
    }
    
    @Override
    public synchronized boolean contains(Object o) {
        return set.contains(o);
    }
    
    @Override
    public synchronized Iterator<T> iterator() {
        return list.iterator();
    }
    
    @Override
    public synchronized Object[] toArray() {
        return list.toArray();
    }
    
    @Override
    public synchronized <X> X[] toArray(X[] a) {
        return list.toArray(a);
    }
    
    public synchronized T get(int index) {
        return list.get(index);
    }
    
}
