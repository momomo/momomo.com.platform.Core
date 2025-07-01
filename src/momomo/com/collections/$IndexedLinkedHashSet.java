
package momomo.com.collections;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;

/**
 * Allows you to call get with o(1) instead of o(n) to get an instance by index.
 * 
 * Note, remove still costs o(n). 
 *
 * @author Joseph S.
 */
public class $IndexedLinkedHashSet<T> implements Set<T>, Serializable {
    
    protected final ArrayList<T> list = new ArrayList<>( );
    protected final HashSet<T>   set  = new HashSet<>  ( );
    
    @Override
    public boolean add(T e) {
        if ( set.add(e) ) {
            return list.add(e);
        }
        return false;
    }
    
    @Override
    public boolean remove(Object o) {
        if ( set.remove(o) ) {
            return list.remove(o);
        }
        return false;
    }
    
    @Override
    public boolean containsAll(Collection<?> c) {
        return set.containsAll(c);
    }
    
    @Override
    public void clear() {
        set.clear(); list.clear();
    }
    
    @Override
    public boolean removeAll(Collection<?> c) {
        if ( set.removeAll(c) ) {
            return list.removeAll(c);
        }
        return true;
    }
    
    @Override
    public boolean retainAll(Collection<?> c) {
        if ( set.retainAll(c) ) {
            return list.retainAll(c);
        }
        return false;
    }
    
    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean modified = false;
        for (T e : c)
            if (add(e))
                modified = true;
        return modified;
    }
    
    @Override
    public int size() {
        return list.size();
    }
    
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }
    
    @Override
    public boolean contains(Object o) {
        return set.contains(o);
    }
    
    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }
    
    @Override
    public Object[] toArray() {
        return list.toArray();
    }
    
    @Override
    public <X> X[] toArray(X[] a) {
        return list.toArray(a);
    }
    
    public T get(int index) {
        return list.get(index);
    }
    
    public T last() {
        return getAt(size() - 1);
    }
    
    public T getAt(int at) {
        return fits(at) ? get(at) : null; 
    }
    
    public T removeLast() {
        return removeAt(size() - 1);
    }
    
    public T removeAt(int at) {
        if ( fits(at) ) {
            T it = getAt(at); remove(at); return it;
        }
        return null;
    }
    
    /**
     * Ensures it is added last again, regardless if it was already in. 
     * 
     * Note, this requires you to have checked before that the object exists in here. 
     */
    public void removeAdd(T o) {
        // No need to remove from set
    
        boolean added = set.add(o);
        if ( !added ) {
            // Was not added so it already existed. 
            
            // If it already existed and we want to add it last, we need to remove it from the list
            
            // We remove it from list in order to ensure it only exists once when we add it last again below 
            list.remove(o);  
        }
                                
        list.add(o);         
    }
    
    protected boolean fits(int index) {
        return index >= 0 && index < size();
    }
    
}
