package momomo.com.sources;

/**
 * Our base comparator class for files, which compares based on toString()
 * 
 * @author Joseph S.
 */
public abstract class FileComparatorBase<T> implements java.util.Comparator<T> {
    protected final boolean ignoreCase;
    
    public FileComparatorBase() {
        this(true);
    }
    
    public FileComparatorBase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }
    
    @Override
    public int compare(T a, T b) {
        return this.compare(a.toString(), b.toString());
    }
    
    protected abstract int compare(String a, String  b);
}
