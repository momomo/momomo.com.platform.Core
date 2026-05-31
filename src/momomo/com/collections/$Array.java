package momomo.com.collections;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/**
 * Basically a public copy of java.util.Arrays.asList but is a public class rather than private which is a prerequisite to use when passing java objects to JXBrowser at least.  
 *
 * @author Joseph S.
 */
public final class $Array<E> extends AbstractList<E> implements RandomAccess, java.io.Serializable {
    private static final long serialVersionUID = -2764017481208945198L;
    
    protected final E[] delegate;
    
    public $Array(E[] delegate) {
        this.delegate = Objects.requireNonNull(delegate);
    }
    
    @Override
    public int size() {
        return delegate.length;
    }
    
    @Override
    public Object[] toArray() {
        return delegate.clone();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        int size = size();
        if (a.length < size)
            return Arrays.copyOf(this.delegate, size,
                (Class<? extends T[]>) a.getClass());
        System.arraycopy(this.delegate, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }
    
    @Override
    public E get(int index) {
        return delegate[index];
    }
    
    @Override
    public E set(int index, E element) {
        E oldValue = delegate[index];
        delegate[index] = element;
        return oldValue;
    }
    
    @Override
    public int indexOf(Object o) {
        E[] a = this.delegate;
        if (o == null) {
            for (int i = 0; i < a.length; i++)
                if (a[i] == null)
                    return i;
        }else {
            for (int i = 0; i < a.length; i++)
                if (o.equals(a[i]))
                    return i;
        }
        return -1;
    }
    
    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }
    
    @Override
    public Spliterator<E> spliterator() {
        return Spliterators.spliterator(delegate, Spliterator.ORDERED);
    }
    
    @Override
    public void forEach(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        for (E e : delegate) {
            action.accept(e);
        }
    }
    
    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        Objects.requireNonNull(operator);
        E[] a = this.delegate;
        for (int i = 0; i < a.length; i++) {
            a[i] = operator.apply(a[i]);
        }
    }
    
    @Override
    public void sort(Comparator<? super E> c) {
        Arrays.sort(delegate, c);
    }
}
