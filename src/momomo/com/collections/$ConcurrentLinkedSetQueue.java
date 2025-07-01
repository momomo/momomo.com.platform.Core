package momomo.com.collections;

import java.util.*;

/**
 * An implementation of a queue that keeps track of insertion order and rejects duplicates.
 * 
 * @author Joseph S.
 */
public final class $ConcurrentLinkedSetQueue<E> implements Set<E>, Deque<E> {
    private final Set<E>   set   = new HashSet<> ( );
    private final Deque<E> queue = new LinkedList<> ( );
    
    /////////////////////////////////////////////////////////////////////
    // Set interface
    /////////////////////////////////////////////////////////////////////
    
    public synchronized boolean addAll(Collection<? extends E> c) {
        return addAllCount(c) > 0;
    }
    
    /**
     * Counts the number of added elements
     */
    public synchronized long addAllCount(Collection<? extends E> c) {
        long modified = 0;
        for (E e : c) {
            if ( add(e) ) {
                ++modified;
            }
        }
        
        return modified;
    }
    
    @Override
    public synchronized boolean add(E e) {
        return addLastPrivate(e);
    }
    
    @Override
    public synchronized void clear() {
        queue.clear();
        set.clear();
    }
    
    @Override
    public synchronized int size() {
        return queue.size();
    }
    
    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
    @Override
    public synchronized Iterator<E> iterator() {
        return queue.iterator();
    }
    
    @Override
    public synchronized boolean containsAll(Collection<?> c) {
        return set.containsAll(c);
    }
    
    @Override
    public synchronized boolean contains(Object o) {
        return set.contains(o);
    }
    
    @Override
    public synchronized Object[] toArray() {
        return queue.toArray();
    }
    
    @Override
    public synchronized <T> T[] toArray(T[] a) {
        return queue.toArray(a);
    }
    
    /////////////////////////////////////////////////////////////////////
    // Queue interface
    /////////////////////////////////////////////////////////////////////
    
    @Override
    public synchronized boolean offer(E e) {
        return offerLast(e);
    }
    
    @Override
    public synchronized E poll() {
        return pollFirst();
    }
    
    @Override
    public synchronized E element() {
        return queue.element();
    }
    
    @Override
    public synchronized E peek() {
        return peekFirst();
    }
    
    @Override
    public synchronized  E remove() {
        return removeFirst();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private synchronized boolean addLastPrivate(E e) {
        return set.add(e) && queue.add(e);
    }
    
    @Override
    public synchronized void addLast(E e) {
        addLastPrivate(e);
    }
    
    @Override
    public synchronized void addFirst(E e) {
        if ( set.add(e) ) {
            queue.addFirst(e);
        }
    }
    
    @Override
    public synchronized boolean offerFirst(E e) {
        return set.add(e) && queue.offerFirst(e);
    }
    
    @Override
    public synchronized boolean offerLast(E e) {
        return set.add(e) && queue.offerLast(e);
    }
    
    @Override
    public synchronized E removeFirst() {
        return throwIfNull( pollFirst() );
    }
    
    @Override
    public synchronized E pollFirst() {
        return removeIfNotNull( queue.pollFirst() );
    }
    
    @Override
    public synchronized E removeLast() {
        return throwIfNull( pollLast() );
    }
    
    @Override
    public synchronized E pollLast() {
        return removeIfNotNull( queue.pollLast() );
    }
    
    @Override
    public synchronized E getFirst() {
        return throwIfNull( peekFirst() );
    }
    
    @Override
    public synchronized E getLast() {
        return throwIfNull( peekLast() );
    }
    
    @Override
    public synchronized E peekFirst() {
        return queue.peekFirst();
    }
    
    @Override
    public synchronized E peekLast() {
        return queue.peekLast();
    }
    
    @Override
    public synchronized void push(E e) {
        addFirst(e);
    }
    
    @Override
    public synchronized E pop() {
        return removeFirst();
    }
    
    @Override
    public synchronized Iterator<E> descendingIterator() {
        return queue.descendingIterator();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private synchronized E throwIfNull(E e) {
        if ( e != null ) {
            return e;
        }
        else {
            throw new NoSuchElementException();
        }
    }
    
    private synchronized E removeIfNotNull(E e) {
        if ( e != null ) {
            set.remove(e);
        }
        
        return e;
    }
    
    
    
    
    
    
    
    
    
    @Override
    public boolean removeLastOccurrence(Object o) {
        throw new UnsupportedOperationException();      // TODO
    }
    
    @Override
    public boolean removeFirstOccurrence(Object o) {
        throw new UnsupportedOperationException();      // TODO
    }
    
    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();      // TODO
    }
    
    /**
     * Expensive remove on queue o(n^2)
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();      // TODO
    }
    
    /**
     * Expensive remove on queue, o(n)
     */
    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();      // TODO
    }
    
}
