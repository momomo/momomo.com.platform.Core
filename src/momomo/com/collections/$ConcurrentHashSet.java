package momomo.com.collections;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * ConcurrenHashSet using a delegated ConcurrentHashMap
 *
 * We do not fully implement the set interface to allow for better perfomance, 
 * since some methods can not be easily performed without full synchronization on everything else.
 * 
 * @author Joseph S.
 */
public class $ConcurrentHashSet<E> {
    private final ConcurrentHashMap<E, Boolean> delegate = new ConcurrentHashMap<>();
    
    public Boolean compute(E key, BiFunction<? super E, ? super Boolean, ? extends Boolean> remappingFunction) {
        return delegate.compute(key, remappingFunction);
    }
    
    public Boolean computeIfAbsent(E key, Function<? super E, ? extends Boolean> mappingFunction) {
        return delegate.computeIfAbsent(key, mappingFunction);
    }
    
    public int size() {
        return delegate.size();
    }
    
    public boolean isEmpty() {
        return delegate.isEmpty();
    }
    
    public boolean contains(Object o) {
        return delegate.contains(o);
    }
    
    public Iterator<E> iterator() {
        return delegate.keySet().iterator();
    }
    
    public Object[] toArray() {
        return delegate.keySet().toArray();
    }
    
    public <T> T[] toArray(T[] a) {
        return delegate.keySet().toArray(a);
    }
    
    public boolean add(E e) {
        return delegate.put(e, Boolean.TRUE) != null;      // Return true if there was a previous value
    }
    
    public boolean remove(Object o) {
        return delegate.remove(o);
    }
    
    public void clear() {
        delegate.clear();
    }
    
    public Spliterator<E> spliterator() {
        return delegate.keySet().spliterator();
    }
}
