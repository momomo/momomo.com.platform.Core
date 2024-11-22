package momomo.com;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 
 * Operations on arrays [] and a one stop reference to things performed on Lists  
 * 
 * @author Joseph S.
 */
public final class $Arrays { private $Arrays() {}
    
    /////////////////////////////////////////////////////////////////////
    
    public static <T, E extends Exception> void each(T[] array, Lambda.V1E<T, E> lambda) throws E {
        each(array, lambda.R2E());
    }
    
    public static <T, E extends Exception> void each(T[] array, Lambda.V2E<T, Integer, E> lambda) throws E {
        each(array, lambda.R2E());
    }
    
    public static <T, E extends Exception> void each(T[] array, Lambda.R1E<Boolean, T, E> lambda) throws E {
        each(array, lambda.R2E());
    }
    
    public static <T, E extends Exception> void each(T[] array, Lambda.R2E<Boolean, T, Integer, E> lambda) throws E {
        int i = -1; while ( ++i < array.length ) {
            if ( Is.False(lambda.call( array[i], i) ) ) { return; };    
        }
    }
    
    /////////////////////////////////////////////////////////////////////
    
    /**
     * Returns the last item of an array. 
     */
    @SafeVarargs
    public static <T> T last( T... split ) {
        return split[split.length - 1];
    }
    
    /////////////////////////////////////////////////////////////////////
    // Merge
    /////////////////////////////////////////////////////////////////////
    
    /**
     * Will only work with Object types, not primitive types. Use java.util.Arrays.appropiate for primitive type manipulation. 
     * Java can be quite annoying at times!
     */
    public static <T> T[] merge(T[] a, T[] b){
        T[] to = create(a, a.length + b.length);
        
        copy(to, a);
        copy(to, b);
        
        return to;
    }
    
    /**
     * Will work on all types, including primitive types.
     *
     * Params is checked during runtime to ensure they are of the same type and are arrays. 
     *
     * @param a must be an array
     * @param b must be an array
     *
     * @return new array containing the contents of both arrays
     */
    public static <T> T merge(T a, T b) {
        Class<?> aClass = a.getClass(), bClass = b.getClass();
        
        if (!aClass.isArray() || !bClass.isArray()) {
            throw new IllegalArgumentException();
        }
        
        Class<?> useClass;
        Class<?> aClassArray = aClass.getComponentType();
        Class<?> bClassArray = bClass.getComponentType();
        
        if (aClassArray.isAssignableFrom(bClassArray)) {
            useClass = aClassArray;
        } else if (bClassArray.isAssignableFrom(aClassArray)) {
            useClass = bClassArray;
        } else {
            throw new IllegalArgumentException();
        }
        
        int al = Array.getLength(a);
        int bl = Array.getLength(b);
        
        T result = (T) Array.newInstance(useClass, al + bl);
        System.arraycopy(a, 0, result, 0, al);
        System.arraycopy(b, 0, result, al, bl);
        
        return result;
    }
    
    /////////////////////////////////////////////////////////////////////
    // Join
    /////////////////////////////////////////////////////////////////////
    
    /**
     * Joins two byte arrays
     */
    public static byte[] join(final byte[] a, byte[] b) {
        byte[] joined = Arrays.copyOf(a, a.length + b.length);
        
        System.arraycopy(b, 0, joined, a.length, b.length);
        
        return joined;
    }
    /**
     * Joins one to many arrays together
     */
    public static <T> T[] join(T[]... arrays) {
        int l = 0;
        for (T[] array : arrays) {
            l += array.length;
        }
        
        @SuppressWarnings("unchecked")
        T[] total = (T[]) Array.newInstance(arrays[0].getClass().getComponentType(), l);
        
        int offset = 0;
        for (T[] array : arrays) {
            System.arraycopy(array, 0, total, offset, array.length);
            offset += array.length;
        }
        return total;
    }
    
    /////////////////////////////////////////////////////////////////////
    
    /**
     * Returns true if any value in any of the two arrays exists in the other
     */
    public static boolean intersects( Object[] as, Object[] bs ) {
        if (as == null || as.length == 0 || bs == null || bs.length == 0) return false;
        
        int i = 0, j = 0;
        
        Object a = as[i++], b;
        
        // No we have to pay the 'price' to create the HashSet
        HashSet<Object> bees = new HashSet<>();
        while (j < bs.length) {
            if ( a.equals(b = bs[j++]) ) return true;
            bees.add(b);
        }
        
        // All values in b should now exists in the HashSet, continue with the a from i = 1
        while (i < as.length) {
            if ( bees.contains(as[i++]) ) return true;
        }
        
        return false;
    }
    
    /////////////////////////////////////////////////////////////////////
    
    /**
     * Will only work with Object types, not primitive types. Use java.util.Arrays.appropiate for primitive type manipulation. 
     * Java can be quite annoying at times!
     */
    public static <T> T[] create(Class<? extends Object[]> klass, int length) {
        return (T[]) Array.newInstance(klass.getComponentType(), length);
    }
    
    /**
     * Will only work with Object types, not primitive types. Use java.util.Arrays.appropiate for primitive type manipulation. 
     * Java can be quite annoying at times!
     */
    public static <T> T[] create(T[] instance, int length) {
        return create(instance.getClass(), length);
    }
    
    /////////////////////////////////////////////////////////////////////
    
    /**
     * Will only work with Object types, not primitive types. Use java.util.Arrays.appropiate for primitive type manipulation. 
     * Java can be quite annoying at times!
     */
    public static <T> T[] copy(T[] to, T[] from) {
        System.arraycopy(from, 0, to, 0, from.length); return to;
    }
    
    /////////////////////////////////////////////////////////////////////
    
    /**
     * Shuffles an array of characters
     * 
     * @return a shuffled array
     */
    public static char[] shuffle(char[] characters) {
        ArrayList<Character> lst = new ArrayList<>();
        
        for (char c : characters) {
            lst.add(c);
        }
        
        Collections.shuffle(lst);
        
        char[] chars = new char[lst.size()];
        int i = -1;
        while ( ++i < lst.size() ) {
            chars[i] = lst.get(i);
        }
        
        return chars;
    }
    
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    
    /**
     * Different from java.util.Arrays.asList(...) which does not return a resizable ArrayList and but the private innerclass kind of the java.util.Arrays class.
     * 
     * @see Arrays#asList(Object...) 
     */
    public static <T> ArrayList<T> toList(T ... tees ) {
        ArrayList<T> lst = new ArrayList<>(tees.length);
        for (T t : tees) {
            lst.add(t);
        }
        return lst;
    }
    
    /**
     * The arguments, will be read key, val, key, val alternating and a hashmap will be returned containing the {key, val} pairs
     */
    public static HashMap<Object, Object> toMap(Object ... args ) {
        HashMap<Object, Object> map = new HashMap<>();
        int                     i   = 0;
        
        while( i < args.length ) {
            
            Object a = args[i], b = args[i+1];
            
            map.compute( a, ( k, v ) -> {
                Object r = b;
                
                if ( v != null ) {
                    
                    if ( !(v instanceof ArrayList ) ) {
                        r = new ArrayList<>(); ((ArrayList)r).add(v);
                    }
                    
                    ((ArrayList)r).add(b);
                }
                
                return r;
            });
            
            i = i + 2;
        }
        return map;
    }
    
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    
}
