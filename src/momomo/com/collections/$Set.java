package momomo.com.collections;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author Joseph S.
 *
 * ChainAbleHashSet basically which differs from HashSet in that it is chainable for methods that normally returns void.
 * 
 * Can be used with other Sets types than HashSet.
 *
 * Possible to do: 
 *   set.add(...).add(...).remove(...)
 *
 * We can't implement collection or abstract set, because then void add could not be chained which is the entire purpose for this implementation here.
 * 
 * Method clone, normally part of HashSet has been removed since they can not easily be supported for all Set types.
 *
 * @author Joseph S.
 */
public final class $Set<E> implements Iterable<E> {
	private final Set<E> delegate;

	public $Set() {
		this(new HashSet<E>());
	}
	public $Set(Set<E> delegate) {
		this.delegate = delegate;
	}

	public Iterator<E> iterator() {
		return delegate.iterator();
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

	public $Set<E> add(E e) {
		delegate.add(e); return this;
	}

	public boolean remove(Object o) {
		return delegate.remove(o);
	}

	public $Set<E> clear() {
		delegate.clear(); return this;
	}

	public Spliterator<E> spliterator() {
		return delegate.spliterator();
	}

	@Override
	public boolean equals(Object o) {
		return delegate.equals(o);
	}

	@Override
	public int hashCode() {
		return delegate.hashCode();
	}

	public boolean removeAll(Collection<?> c) {
		return delegate.removeAll(c);
	}

	public Object[] toArray() {
		return delegate.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return delegate.toArray(a);
	}

	public boolean containsAll(Collection<?> c) {
		return delegate.containsAll(c);
	}

	public boolean addAll(Collection<? extends E> c) {
		return delegate.addAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return delegate.retainAll(c);
	}

	@Override
	public String toString() {
		return delegate.toString();
	}

	public boolean removeIf(Predicate<? super E> filter) {
		return delegate.removeIf(filter);
	}

	public Stream<E> stream() {
		return delegate.stream();
	}

	public Stream<E> parallelStream() {
		return delegate.parallelStream();
	}

	public void forEach(Consumer<? super E> action) {
		delegate.forEach(action);
	}
    
    public Set<E> asSet() {
        return Collections.unmodifiableSet(delegate);
    }
}

