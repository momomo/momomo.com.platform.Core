package momomo.com.collections;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * Easier to remember name for a concurrent array list.
 * 
 * Do not use CopyOnWriteArrayList which can be ineffecient on insertion. 
 * 
 * Simply delegates to a SynchronizedList
 * 
 * @author Joseph S.
 */
public final class $ConcurrentList<E> implements List<E>, RandomAccess, Cloneable, java.io.Serializable {
    private final List<E> delegate;
    
    public $ConcurrentList() {
        this(new ArrayList<E>());
    }
    
    public $ConcurrentList(List<E> list) {
        this.delegate = Collections.synchronizedList(list);
    }
    
    @Override
    public int size() {
        return delegate.size();
    }
    
    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }
    
    @Override
    public boolean contains(Object o) {
        return delegate.contains(o);
    }
    
    @Override
    public Iterator<E> iterator() {
        return delegate.iterator();
    }
    
    @Override
    public Object[] toArray() {
        return delegate.toArray();
    }
    
    @Override
    public <T> T[] toArray(T[] a) {
        return delegate.toArray(a);
    }
    
    @Override
    public boolean add(E e) {
        return delegate.add(e);
    }
    
    @Override
    public boolean remove(Object o) {
        return delegate.remove(o);
    }
    
    @Override
    public boolean containsAll(Collection<?> c) {
        return delegate.containsAll(c);
    }
    
    @Override
    public boolean addAll(Collection<? extends E> c) {
        return delegate.addAll(c);
    }
    
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return delegate.addAll(index, c);
    }
    
    @Override
    public boolean removeAll(Collection<?> c) {
        return delegate.removeAll(c);
    }
    
    @Override
    public boolean retainAll(Collection<?> c) {
        return delegate.retainAll(c);
    }
    
    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        delegate.replaceAll(operator);
    }
    
    @Override
    public void sort(Comparator<? super E> c) {
        delegate.sort(c);
    }
    
    @Override
    public void clear() {
        delegate.clear();
    }
    
    @Override
    public boolean equals(Object o) {
        return delegate.equals(o);
    }
    
    @Override
    public int hashCode() {
        return delegate.hashCode();
    }
    
    @Override
    public E get(int index) {
        return delegate.get(index);
    }
    
    @Override
    public E set(int index, E element) {
        return delegate.set(index, element);
    }
    
    @Override
    public void add(int index, E element) {
        delegate.add(index, element);
    }
    
    @Override
    public E remove(int index) {
        return delegate.remove(index);
    }
    
    @Override
    public int indexOf(Object o) {
        return delegate.indexOf(o);
    }
    
    @Override
    public int lastIndexOf(Object o) {
        return delegate.lastIndexOf(o);
    }
    
    @Override
    public ListIterator<E> listIterator() {
        return delegate.listIterator();
    }
    
    @Override
    public ListIterator<E> listIterator(int index) {
        return delegate.listIterator(index);
    }
    
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return delegate.subList(fromIndex, toIndex);
    }
    
    @Override
    public Spliterator<E> spliterator() {
        return delegate.spliterator();
    }
    
    @Override
    public <T> T[] toArray(IntFunction<T[]> generator) {
        return delegate.toArray(generator);
    }
    
    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        return delegate.removeIf(filter);
    }
    
    @Override
    public Stream<E> stream() {
        return delegate.stream();
    }
    
    @Override
    public Stream<E> parallelStream() {
        return delegate.parallelStream();
    }
    
    @Override
    public void forEach(Consumer<? super E> action) {
        delegate.forEach(action);
    }
}

