/* Copyright(C) 2014 - 2020 Momomo LTD. Proprietary and confidential. Usage of this file on any medium without a written consent by Momomo LTD. is strictlys prohibited. All Rights Reserved. */
package momomo.com;

import com.sun.net.httpserver.Headers;
import momomo.com.collections.$Map;
import momomo.com.exceptions.$MapsCloneException;
import momomo.com.exceptions.$MapsCloneGuranteeException;

import javax.management.openmbean.TabularDataSupport;
import javax.print.attribute.standard.PrinterStateReasons;
import javax.script.SimpleBindings;
import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AuthProvider;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.jar.Attributes;

/**
 * Operations on Map and a one stop reference to things performed on Maps.   
 *
 * @author Joseph S.
 */
public final class $Maps {
    
    private $Maps() { }
    
    /////////////////////////////////////////////////////////////////////
    
    private static final boolean MERGE_OVERWRITE = true;
    private static final boolean MERGE_DEEP      = true;
    
    private static final boolean PUT_OVERWRITE = true;
    private static final boolean PUT_DEEP      = true;
    
    private static final boolean COPY_OVERWRITE    = true;
    private static final boolean COPY_DEEP         = false;
    public  static final boolean COPY_CLONE_STRICT = true;
    
    /////////////////////////////////////////////////////////////////////
    
    public static <K, V> ConcurrentHashMap<K, V> concurrent() {
        return new ConcurrentHashMap<>();
    }

    /////////////////////////////////////////////////////////////////////
    // map
    /////////////////////////////////////////////////////////////////////
    
    /**
     * Create a map
     * 
     * Generic type of map is inferred by call
     */
    public static <K, V> HashMap<K, V> map() {
        return new HashMap<>();
    }
    
    /**
     * Create a hashmap and put the val at key
     * 
     * Generic type of map is defined by type of params key and val
     */
    public static <K, V> HashMap<K, V> map(K key, V val) {
        return map(key, val, null, (Object[]) null);
    }
    
    /**
     * Create a hashmap and put the val at key. Then proceed to put alternating val to previous arg as key. 
     * 
     * Example
     *      $Maps.put('a', '1', 'b', '2', 'c', '3', 'd', '4') ==> {a:1, b:2, c:3, d:4}
     * 
     * Generic type of map is defined by type of params key and val     
     */
    public static <K, V> HashMap<K, V> map( K key, V val, K k, Object ... args ) {
        return map(new HashMap<>(), key, val, k, args);
    }
    
    /**
     * On existing map of any type, put the val at key. Then proceed to put alternating val to previous arg as key.
     * 
     * Type of the map is defined by M
     */
    public static <M extends Map<K, V>, K, V> M map( M map, K key, V val, K k, Object... args ) {
        return put(map, key, val, k, args);
    }
    
    /////////////////////////////////////////////////////////////////////
    // put
    /////////////////////////////////////////////////////////////////////
    
    public static <M extends Map<K, V>, K, V> M put( M map, K key, V val, K k, Object ... args ) {
        return $put$(map, key, val, k, args, PUT_OVERWRITE, PUT_DEEP);
    }
    
    /**
     * Base put. Private.  
     */
    private static <M extends Map<K, V>, K, V> M $put$(M map, K key, V val, K k, Object[] args, boolean overwrite, boolean deep) {
        if ( key != null ) {
            $put$(map, key, val, overwrite);
        }
        
        if ( args == null ) return map;
        
        if ( k != null ) {
            if (args.length > 0) {
                $merge$(map, k, (V) args[0], overwrite, deep);
            }
        }
        
        int i = 1;
        while ( i < args.length ) {
            $merge$(map, (K) args[i++], (V) args[i++], overwrite, deep);
        }
        
        return map;
    }
    
    /**
     * Base put. Private.
     */
    private static <M extends Map<K, V>, K, V> M $put$(M map, K key, V val, boolean overwrite) {
        if ( overwrite || !map.containsKey(key) ) {
            map.put(key, val);
        }
        return map;
    }
    
    /////////////////////////////////////////////////////////////////////
    // merge
    /////////////////////////////////////////////////////////////////////
    
    /**
     * On existing map of any type, put the val at key. 
     * If the key is already taken, it will override it, unless the val is also map, in which case it will merge the values onto the previous one.
     */
    public static <M extends Map<K, V>, K, V> M merge(M one, K k, V v) {
        return merge(one, k, v, null, (Object[]) null);
    }
    
    /**
     * On existing map of any type, put the val at key. Then proceed to put alternating val to previous arg as key.
     * If the key is already taken, it will override it, unless the val is also map, in which case it will merge the values onto the previous one.
     */
    public static <M extends Map<K, V>, K, V> M merge(M one, K k, V v, K key, Object ... args) {
        return merge( one, map(k, v, key, args) );
    }
    
    /**
     * On existing map of any type, merge the second map.
     * If the key is already taken on one, it will override it, unless the val is also map, in which case it will merge the values onto the previous one.
     */
    public static <M extends Map<K, V>, K, V> M merge(M one, Map<K, V> two) {
        return merge(one, two, MERGE_OVERWRITE, MERGE_DEEP);
    }
    
    /**
     * On existing map of any type, merge the second map.
     * If the key is already taken on one, it will override it, unless the val is also map, in which case it will merge the values onto the previous one.
     * 
     * @param overwrite determines precedence, and if a value exists in one, should take precedence or not. By default, any value in two overwrites
     */
    public static <M extends Map<K, V>, K, V> M merge(M one, Map<K, V> two, boolean overwrite) {
        return merge(one, two, overwrite, MERGE_DEEP);
    }
    
    /**
     * On existing map of any type, merge the second map.
     * If the key is already taken on one, it will override it, unless the val is also map, in which case it will merge the values onto the previous one.
     * 
     * @param overwrite determines precedence, and if a value exists in one, should take precedence or not. By default, any value in two overwrites
     * @param deep whether to merge two vals if val encountered in one is a map. By default we deep merge. 
     */
    public static <M extends Map<K, V>, K, V> M merge(M one, Map<K, V> two, boolean overwrite, boolean deep) {
        if (two != null && two.size() > 0) {
            for (Map.Entry<K, V> entry : two.entrySet()) {
                K key = entry.getKey();
                V val = entry.getValue();
                
                $put$(one, key, val, null, null, overwrite, deep);
            }
        }
        
        return one;
    }
    
    /**
     * Base merge. Private.  
     */
    private static <M extends Map<K, V>, K, V> M $merge$(M one, K key, V val, boolean overwrite, boolean deep) {
        if ( key == null ) return one;
        
        V v = one.get(key);
        
        // If deep is true and both values are maps
        if (deep && v instanceof Map && val instanceof Map ) {
            merge(
                (Map<K, V>) v,
                (Map<K, V>) val,
                overwrite, deep
            );
            
            return one;
        }
        
        return $put$(one, key, val, overwrite);
    }
    
    
    public static <M extends Map<K, V>, K, V> Map<K, V> copy(M map) {
        // HashMap
        if ( map instanceof LinkedHashMap ) {
            return copy((LinkedHashMap<K, V>) map);
        }
    
        // HashMap
        if (map instanceof HashMap ) {
            return copy((HashMap<K, V>) map);
        }
        
        if (map instanceof ConcurrentHashMap ) {
            return copy((ConcurrentHashMap<K, V>) map);
        }
    
        if (map instanceof ConcurrentSkipListMap) {
            return copy((ConcurrentSkipListMap<K, V>) map);
        }
    
        if ( map instanceof TreeMap ) {
            return copy((TreeMap<K, V>) map);
        }
    
        if ( map instanceof IdentityHashMap ) {
            return copy((IdentityHashMap<K, V>) map);
        }
    
        if ( map instanceof WeakHashMap) {
            return copy((WeakHashMap<K, V>) map);
        }
    
        if (map instanceof EnumMap) {
            return copy((EnumMap) map);
        }
    
        // Hashtable
        if ( map instanceof Properties ) {
            return (Map<K,V>) copy((Properties) map);
        }
    
        // Hashtable
        if ( map instanceof Hashtable) {
            return copy((Hashtable<K, V>) map);
        }
    
        if ( map instanceof SimpleBindings) {
            return (Map<K, V>) copy((SimpleBindings) map);
        }
    
        if ( map instanceof Attributes) {
            return (Map<K, V>) copy((Attributes) map);
        }
    
        if ( map instanceof Headers ) {
            return (Map<K, V>) copy((Headers) map);
        }
    
        if ( false ) {
            // No need to support these. But since implemented we leave them off.
            if ( map instanceof AuthProvider) {
                return (Map<K, V>) ((AuthProvider) map).clone();
            }
            if (map instanceof UIDefaults) {
                return (Map<K, V>) ((UIDefaults) map).clone();
            }
            if (map instanceof PrinterStateReasons) {
                return (Map<K, V>) ((PrinterStateReasons) map).clone();
            }
            if (map instanceof RenderingHints) {
                return (Map<K, V>) ((RenderingHints) map).clone();
            }
            if (map instanceof TabularDataSupport) {
                return (Map<K, V>) ((TabularDataSupport) map).clone();
            }
        }
    
        // If instance of Clonable, we attempt to invoke it using reflection first. If it  
        try {
            return clone(map, COPY_CLONE_STRICT);
        }
        catch (Throwable e) {
            // It will fail if clone is not there, null, or if method is not public. If we fail, then we do things manually below for anything without a public clonable. 
        }
        
        // If nothing matched up to here, we fall back to creating a hashmap out of the original map which could not pass our default checks 
        return copy(new HashMap<>(), map);
    }
    
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    
    public static <K, V> LinkedHashMap<K, V> copy(LinkedHashMap<K, V> map) {
        return new LinkedHashMap<>(map);
    }
    public static <K, V> HashMap<K, V> copy(HashMap<K, V> map) {
        return (HashMap<K, V>) map.clone();
    }
    public static <K, V> ConcurrentHashMap<K, V> copy(ConcurrentHashMap<K, V> map) {
        return new ConcurrentHashMap<K, V>(map);
    }
    public static <K, V> ConcurrentSkipListMap<K, V> copy(ConcurrentSkipListMap<K, V> map) {
        return map.clone();
    }
    public static <K, V> TreeMap<K, V> copy(TreeMap<K, V> map) {
        return (TreeMap<K, V>) map.clone();
    }
    public static <K, V> IdentityHashMap<K, V> copy(IdentityHashMap<K, V> map) {
        return (IdentityHashMap<K, V>) map.clone();
    }
    public static <K, V> WeakHashMap<K, V> copy(WeakHashMap<K, V> map) {
        return new WeakHashMap<K, V>(map);
    }
    public static <K extends Enum<K>, V> EnumMap<K, V> copy(EnumMap<K, V> map) {
        return map.clone();
    }
    public static Properties copy(Properties map) {
        return (Properties) map.clone();
    }
    public static <K, V> Hashtable<K, V> copy(Hashtable<K, V> map) {
        return (Hashtable<K, V>) map.clone();
    }
    public static Attributes copy(Attributes map) {
        return (Attributes) map.clone();
    }
    public static SimpleBindings copy(SimpleBindings map) {
        return copy(new SimpleBindings(), map);
    }
    public static Headers copy(Headers map) {
        return merge(new Headers(), map, COPY_OVERWRITE, COPY_DEEP);
    }
    public static <K, V> $Map<K, V> copy($Map<K, V> map) {
        return map.copy();
    }
    
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    
    private static <M extends Map<K, V>, K, V> M copy(M too, Map<K, V> map) {
        return merge(too, map, PUT_OVERWRITE, false);
    }
    
    // public static <K, V> HashMap<K, V> copy(HashMap<K, V> map, Map<K, V> two) {
   //     return merge(copy(map), two);
   // }
   // public static <K, V> HashMap<K, V> copy(HashMap<K, V> map, K k, V v, K key, Object ... args) {
   // }
   // public static <K, V> HashMap<K, V> copy(HashMap<K, V> map, K k, V v, K key, Object ... args) {
   //     return merge(copy(map), k, v, key, args);
   // }
    
    
    /////////////////////////////////////////////////////////////////////
    // clone using reflection on any map
    /////////////////////////////////////////////////////////////////////
    
    /**
     * We recommend you use methods copy over this as {@link $Maps##copy} handles various failures and gurantees a fallback.
     * 
     * By default strict.  
     */
    public static <M extends Map<K, V>, K, V> M clone(M map) {
        return clone(map, true);
    }
    
    /**
     * We recommend you use methods copy over this as {@link $Maps##copy} handles various failures and gurantees a fallback.
     * 
     * @param strict pass true if you do not want it to be strict
     */
    public static <M extends Map<K, V>, K, V> M clone(M map, boolean strict) {
        try {
            Method clone = map.getClass().getMethod("clone");
            
            // true if we may accept the clone method on a super class, which will ineveitably return some other type than the original map type
            if ( !strict || clone.getDeclaringClass().equals(map.getClass()) ) {
                return (M) clone.invoke(map);
            }
        }
        catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new $MapsCloneException(e);
        }
        
        // Will come here only if loose ... 
        throw new $MapsCloneGuranteeException("Could not gurantee the same type");
    }
    
    
     
    
    
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    
}
