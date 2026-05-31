package momomo.com.collections;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * ChainAbleArrayList basically which differs from ArrayList in that it is chainable for methods that normally returns void.
 * 
 * Can be used with other List types than ArrayList. 
 * 
 * Possible to do: 
 *   list.add(...).add(...).remove(...)
 *
 * We can't implement collection or abstract list, because then void add could not be chained which is the entire purpose for this implementation here.
 * 
 * Methods ensureCapacity, trimToSize, and clone, normally part of ArrayList has been removed since they can not easily be supported for all List types. 
 *
 * @author Joseph S.
 */
public final class $List<E> implements Iterable<E>, RandomAccess, java.io.Serializable {
    protected final List<E> delegate;
    
    public $List() {
        this(0);
    }
    
    public $List(int initialCapacity) {
        this(new ArrayList<>(initialCapacity));
    }
    
    public $List(Collection<? extends E> collection) {
        this( new ArrayList<>(collection) );
    }
    
    public $List(List<E> list) {
        this.delegate = list;
    }
    
    public void forEach(Consumer<? super E> action) {
        delegate.forEach(action);
    }
    
    public boolean removeIf(Predicate<? super E> filter) {
        return delegate.removeIf(filter);
    }
    
    public int lastIndexOf(Object o) {
        return delegate.lastIndexOf(o);
    }
    
    public boolean remove(Object o) {
        return delegate.remove(o);
    }
    
    public E get(int index) {
        return delegate.get(index);
    }
    
    public E remove(int index) {
        return delegate.remove(index);
    }
    
    public boolean addAll(Collection<? extends E> c) {
        return delegate.addAll(c);
    }
    
    // Added to take iterable instead and to chain
    public $List<E> addAll(Iterable<E> c) {
        for ( E e : c ) {
            delegate.add(e);
        }
        return this;
    }
    
    public boolean addAll(int index, Collection<? extends E> c) {
        return delegate.addAll(index, c);
    }
    
    public Stream<E> parallelStream() {
        return delegate.parallelStream();
    }
    
    public boolean containsAll(Collection<?> c) {
        return delegate.containsAll(c);
    }
    
    public Stream<E> stream() {
        return delegate.stream();
    }
    
    public Object[] toArray() {
        return delegate.toArray();
    }
    
    public boolean removeAll(Collection<?> c) {
        return delegate.removeAll(c);
    }
    
    public $List<E> add(int index, E element) {
        delegate.add(index, element);
        return this;
    }
    
    public List<E> subList(int fromIndex, int toIndex) {
        return delegate.subList(fromIndex, toIndex);
    }
    
    public ListIterator<E> listIterator(int index) {
        return delegate.listIterator(index);
    }
    
    public boolean retainAll(Collection<?> c) {
        return delegate.retainAll(c);
    }
    
    public E set(int index, E element) {
        return delegate.set(index, element);
    }
    
    public boolean contains(Object o) {
        return delegate.contains(o);
    }
    
    public boolean isEmpty() {
        return delegate.isEmpty();
    }
    
    public Iterator<E> iterator() {
        return delegate.iterator();
    }
    
    public $List<E> sort(Comparator<? super E> c) {
        delegate.sort(c);
        return this;
    }
    
    public ListIterator<E> listIterator() {
        return delegate.listIterator();
    }
    
    public <T> T[] toArray(T[] a) {
        return delegate.toArray(a);
    }
    
    public $List<E> clear() {
        delegate.clear();
        return this;
    }
    
    public $List<E> replaceAll(UnaryOperator<E> operator) {
        delegate.replaceAll(operator);
        return this;
    }
    
    public Spliterator<E> spliterator() {
        return delegate.spliterator();
    }
    
    public $List<E> add(E e) {
        delegate.add(e);
        return this;
    }
    
    public int indexOf(Object o) {
        return delegate.indexOf(o);
    }
    
    public int size() {
        return delegate.size();
    }
}
