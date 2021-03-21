
package momomo.com.collections;

import java.util.Collection;
import java.util.Iterator;

/**
 * @see $IndexedLinkedHashSet
 * 
 * Concurrent by simply using synchronized. 
 * 
 * @author Joseph S.
 */
public class $ConcurrentIndexedLinkedHashSet<T> extends $IndexedLinkedHashSet<T> {
    
    @Override
    public synchronized boolean add(T e) {
        return super.add(e);
    }
    
    @Override
    public synchronized boolean remove(Object o) {
        return super.remove(o);
    }
    
    @Override
    public synchronized boolean containsAll(Collection<?> c) {
        return super.containsAll(c);
    }
    
    @Override
    public synchronized void clear() {
        super.clear();
    }
    
    @Override
    public synchronized boolean removeAll(Collection<?> c) {
        return super.removeAll(c);
    }
    
    @Override
    public synchronized boolean retainAll(Collection<?> c) {
        return super.retainAll(c);
    }
    
    @Override
    public synchronized boolean addAll(Collection<? extends T> c) {
        return super.addAll(c);
    }
    
    @Override
    public synchronized int size() {
        return super.size();
    }
    
    @Override
    public synchronized boolean isEmpty() {
        return super.isEmpty();
    }
    
    @Override
    public synchronized boolean contains(Object o) {
        return super.contains(o);
    }
    
    @Override
    public synchronized Iterator<T> iterator() {
        return super.iterator();
    }
    
    @Override
    public synchronized Object[] toArray() {
        return super.toArray();
    }
    
    @Override
    public synchronized <X> X[] toArray(X[] a) {
        return super.toArray(a);
    }
    
    @Override
    public synchronized T get(int index) {
        return super.get(index);
    }
    
    @Override
    public synchronized T getAt(int at) {
        return super.last();
    }
    
    @Override
    public synchronized T last() {
        return super.last();
    }
    
    @Override
    public synchronized T removeLast() {
        return super.removeLast();
    }
    
    @Override
    public synchronized T removeAt(int at) {
        return super.removeAt(at);
    }
    
}
