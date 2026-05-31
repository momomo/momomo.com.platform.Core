package momomo.com.collections;

import momomo.com.$Maps;
import momomo.com.Is;
import momomo.com.Lambda;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A quick use, dynamic kind map that allows you to call getString(key), getBoolean(key) ... which will automatically convert the value to the desired known format.
 *
 * Very useful for user input parsing. 
 *
 * @author Joseph S.
 */
public class $MapQuick<K, V> implements Map<K, V> {
    protected final Map<K, V> map;
    
    public $MapQuick(K key, V val) {
        this(); this.set(key, val);
    }
    public $MapQuick(K key, V val, K k, Object... args ) {
        this(); this.put(key, val, k, args);
    }
    
    public $MapQuick(Map<K, V> map) {
        this.map = map;
    }
    
    public $MapQuick() {
        this( new HashMap<K, V>() );
    }
    
    public Integer getInteger(K key) {
        return Getters.Statics.getInteger(map, key);
    }
    
    public Date getDate(K key) {
        return Getters.Statics.getDate(map, key);
    }
    
    public Double getDouble(K key) {
        return Getters.Statics.getDouble(map, key);
    }
    
    public String getString(K key) {
        return Getters.Statics.getString(map, key);
    }
    
    public boolean getBoolean(K key, boolean whenNull) {
        return Getters.Statics.getBoolean(map, key, whenNull);
    }
    public Long getLong(K key) {
        return Getters.Statics.getLong(map, key);
    }
    public Boolean getBoolean(K key) {
        return Getters.Statics.getBoolean(map, key);
    }
    public boolean isFalse(K key) {
        return Getters.Statics.isFalse(map, key);
    }
    public boolean isTrue(K key) {
        return Getters.Statics.isTrue(map, key);
    }
    
    public V get(Object key) {
        return Getters.Statics.get(map, (K) key);
    }
    
    /**
     * Similar to get, only it casts to the type is inferred on the call. Useful when we know the type for sure to be the type we desire. 
     */
    public <T> T cast(String key) {
        return (T) get((Object)key);
    }
    
    public $MapQuick<K, V> set(K key, V val) {
        map.put(key, val); return this;
    }
    
    public $MapQuick<K, V> put(K key, V val, K k, Object... args) {
        $Maps.put(map, key, val, k, args); return this;
    }
    
    public $MapQuick<K, V> merge(Map<K, V> two) {
        return $Maps.merge(this, two);
    }
    public $MapQuick<K, V> merge(K key, V val, K k, Object ... args) {
        return $Maps.merge(this, $Maps.map(key, val, k, args) );
    }
    public $MapQuick<K, V> merge(K key, V val) {
        return $Maps.merge(this, $Maps.map(key, val, null, (Object[]) null) );
    }
    
    public $MapQuick<K, V> copy() {
        $Maps.merge(new $MapQuick<>(), this); return this;
    }
    
    @Override
    public int size() {
        return map.size();
    }
    
    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }
    
    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }
    
    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }
    
    @Override
    public V put(K key, V value) {
        return map.put(key, value);
    }
    
    @Override
    public V remove(Object key) {
        return map.remove(key);
    }
    
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }
    
    @Override
    public void clear() {
        map.clear();
    }
    
    @Override
    public Set<K> keySet() {
        return map.keySet();
    }
    
    @Override
    public Collection<V> values() {
        return map.values();
    }
    
    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }
    
    @Override
    public boolean equals(Object o) {
        return map.equals(o);
    }
    
    @Override
    public int hashCode() {
        return map.hashCode();
    }
    
    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return map.getOrDefault(key, defaultValue);
    }
    
    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        map.forEach(action);
    }
    
    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        map.replaceAll(function);
    }
    
    @Override
    public V putIfAbsent(K key, V value) {
        return map.putIfAbsent(key, value);
    }
    
    @Override
    public boolean remove(Object key, Object value) {
        return map.remove(key, value);
    }
    
    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        return map.replace(key, oldValue, newValue);
    }
    
    @Override
    public V replace(K key, V value) {
        return map.replace(key, value);
    }
    
    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        return map.computeIfAbsent(key, mappingFunction);
    }
    
    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return map.computeIfPresent(key, remappingFunction);
    }
    
    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return map.compute(key, remappingFunction);
    }
    
    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return map.merge(key, value, remappingFunction);
    }
    
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    
    /**
     * @author Joseph S.
     *
     * Since the statics are a mirror, it makes little (not any) sense for these to be on an instance class although calling is slighlty easier then.
     * Consider ignoring, alternatively creating an implementing interface, ie $HashMap extends java.util.HashMap implements below without map in parameters being required. 
     */
    public static interface Getters {
        default <K, V> Integer getInteger(Map<K, V> m, K key){
            return Statics.getInteger(m, key);
        }
        default <K, V> Long getLong(Map<K, V> m, K key){
            return Statics.getLong(m, key);
        }
        default <K, V> Double getDouble(Map<K, V> m, K key){
            return Statics.getDouble(m, key);
        }
        default <K, V> String getString(Map<K, V> m, K key) {
            return Statics.getString(m, key);
        }
        default <K, V> Date getDate(Map<K, V> m, K key) {
            return Statics.getDate(m, key);
        }
        default <V> V getCasted(Map m, Object key) {
            return Statics.getCasted(m, key);
        }
        
        default <K, V> boolean getBoolean(Map<K, V> m, K key, boolean whenNull){
            return Statics.getBoolean(m, key, whenNull);
        }
        default <K, V> Boolean getBoolean(Map<K, V> m, K key){
            return Statics.getBoolean(m, key);
        }
        default <K, V> boolean isFalse(Map<K, V> m, K key){
            return Statics.isFalse(m, key);
        }
        default <K, V> boolean isTrue(Map<K, V> m, K key){
            return Statics.isTrue(m, key);
        }
        
        /////////////////////////////////////////////////////////////////////
        // get below
        /////////////////////////////////////////////////////////////////////
        
        default <V> V get(Map<String, V> m, Class<?> key) {
            return Statics.get(m, key);
        }
        default <V> V get(Map<String, V> m, Enum key) {
            return Statics.get(m, key);
        }
        default <K, V> V get(Map<K, V> m, K key) {
            return Statics.get(m, key);
        }
        default <K, V, E extends Exception> void get(Map<K, V> m, K key, Lambda.V1E<V, E> lambda) throws E {
            Statics.get(m, key, lambda);
        }
        default <K, V, R, E extends Exception> R get(Map<K, V> m, K key, Lambda.R1E<R, V, E> lambda) throws E {
            return Statics.get(m, key, lambda);
        }
        default <V, E extends Exception> void get(Map<String, V> m, Class<?> key, Lambda.V1E<V, E> lambda) throws E {
            Statics.get(m, key, lambda);
        }
        default <V, R, E extends Exception> R get(Map<String, V> m, Class<?> key, Lambda.R1E<R, V, E> lambda) throws E {
            return Statics.get(m, key, lambda);
        }
        
        /////////////////////////////////////////////////////////////////////
        // has below
        /////////////////////////////////////////////////////////////////////
        
        default <V> boolean has(Map<String, V> m, Class<?> key) {
            return Statics.has(m, key);
        }
        default <V> boolean has(Map<String, V> m, Enum key) {
            return Statics.has(m, key);
        }
        default <V> boolean has(Map<String, V> m, String key) {
            return Statics.has(m, key);
        }
        default <K, V> boolean has(Map<K, V> m, K key) {
            return Statics.has(m, key);
        }
        
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        
        /**
         * Delegated to from the interface as well as used from static access. 
         *
         * Separated into a class of its own to avoid having to modify method names from the ones defined in the interface. 
         *
         * @author Joseph S.
         */
        public static final class Statics {
            
            public static <K, V> Integer getInteger(Map<K, V> m, K key) {
                if ( m == null ) return null;
                
                Object val = m.get(key);
                if ( Is.Ok(val) ) {
                    if (val instanceof String) {
                        val = Integer.parseInt((String) val);
                    }
                    
                    return (Integer) val;
                }
                
                return null;
            }
            
            public static <K, V> Long getLong(Map<K, V> m, K key) {
                if ( m == null) return null;
                
                Object val = m.get(key);
                if ( Is.Ok(val) ) {
                    if (val instanceof String) {
                        val = Long.parseLong((String) val);
                    }
                    
                    return (Long) val;
                }
                
                return null;
            }
            
            public static <K, V> Double getDouble(Map<K, V> m, K key) {
                if ( m == null) return null;
                
                Object val = m.get(key);
                if ( Is.Ok(val) ) {
                    if (val instanceof String) {
                        val = Double.parseDouble((String) val);
                    }
                    
                    return (Double) val;
                }
                
                return null;
            }
            
            public static <K, V> String getString(Map<K, V> m, K key) {
                Object val = get(m, key);
                
                if ( val != null ) {
                    if ( val instanceof String ) {
                        return (String) val;
                    }
                }
                return null;
            }
            
            public static <K, V> Date getDate(Map<K, V> m, K key) {
                Object val = get(m, key);
                
                if ( Is.Ok(val) ) {
                    if (val instanceof String) {
                        Calendar cal = Calendar.getInstance();
                        
                        cal.setTimeInMillis( Long.parseLong((String) val) );
                        
                        val = cal.getTime();
                    }
                    else if ( val instanceof Double ) {
                        val = new Date( ((Double) val).longValue() );
                    }
                    else if ( val instanceof Long ) {
                        val = new Date( (Long) val );
                    }
                    return (Date) val;
                }
                return null;
            }
            
            public static <K, V> boolean getBoolean(Map<K, V> m, K key, boolean whenNull) {
                return Objects.requireNonNullElse(getBoolean(m, key), whenNull);
            }
            
            public static <K, V> Boolean getBoolean(Map<K, V> m, K key) {
                if ( m == null) return null;
                
                Object val = m.get(key);
                if ( val != null ) {
                    if ( val instanceof Boolean ) {
                        return (Boolean) val;
                    }
                    else
                    if (val instanceof String) {
                        if ( "true".equals(val) ) {
                            return true;
                        }
                    }
                    else
                    if ( val instanceof Number ){
                        return ((Number) val).shortValue() == 1;
                    }
                }
                
                return null;
            }
            
            public static <K, V> boolean isFalse(Map<K, V> m, K key) {
                return Is.False( getBoolean(m, key) );
            }
            
            public static <K, V> boolean isTrue(Map<K, V> m, K key) {
                return Is.True( getBoolean(m, key) );
            }
            
            /////////////////////////////////////////////////////////////////////
            // get below
            /////////////////////////////////////////////////////////////////////
            
            public static <V> V get(Map<String, V> m, Class<?> key) {
                return get(m, key.getName());
            }
            public static <V> V get(Map<String, V> m, Enum key) {
                return get(m, key.toString());
            }
            public static <V> V get(Map<String, V> m, String key) {
                return $get$(m, key);
            }
            public static <K, V> V get(Map<K, V> m, K key) {
                return $get$(m, key);
            }
            private static <K, V> V $get$(Map<K, V> m, K key) {
                if ( m == null) return null;
                return (V) m.get(key);
            }
            public static <V> V getCasted(Map m, Object key) {
                return (V) $get$(m, key);
            }
            
            public static <V, E extends Exception> void get(Map<String, V> m, Enum key, Lambda.V1E<V, E> lambda) throws E {
                get(m, key.toString(), lambda.R1E());
            }
            public static <V, E extends Exception> void get(Map<String, V> m, Class<?> key, Lambda.V1E<V, E> lambda) throws E {
                get(m, key, lambda.R1E());
            }
            public static <V, E extends Exception> void get(Map<String, V> m, String key, Lambda.V1E<V, E> lambda) throws E {
                get(m, key, lambda.R1E());
            }
            public static <K, V, E extends Exception> void get(Map<K, V> m, K key, Lambda.V1E<V, E> lambda) throws E {
                get(m, key, lambda.R1E());
            }
            
            public static <V, R, E extends Exception> R get(Map<String, V> m, Enum key, Lambda.R1E<R, V, E> lambda) throws E {
                return get(m, key.toString(), lambda);
            }
            public static <V, R, E extends Exception> R get(Map<String, V> m, Class<?> key, Lambda.R1E<R, V, E> lambda) throws E {
                return get(m, key.getName(), lambda);
            }
            public static <V, R, E extends Exception> R get(Map<String, V> m, String key, Lambda.R1E<R, V, E> lambda) throws E {
                return $get$(m, key, lambda);
            }
            public static <K, V, R, E extends Exception> R get(Map<K, V> m, K key, Lambda.R1E<R, V, E> lambda) throws E {
                return $get$(m, key, lambda);
            }
            private static <K, V, R, E extends Exception> R $get$(Map<K, V> m, K key, Lambda.R1E<R, V, E> lambda) throws E {
                V p = get(m, key);
                if ( p != null ){
                    return lambda.call(p);
                }
                return null;
            }
            
            /////////////////////////////////////////////////////////////////////
            // has below
            /////////////////////////////////////////////////////////////////////
            
            public static <V> boolean has(Map<String, V> m, Class<?> key) {
                return has(m, key.getName());
            }
            public static <V> boolean has(Map<String, V> m, Enum key) {
                return has(m, key.toString());
            }
            public static <V> boolean has(Map<String, V> m, String key) {
                return $has$(m, key);
            }
            public static <K, V> boolean has(Map<K, V> m, K key) {
                return $has$(m, key);
            }
            private static <K, V> boolean $has$(Map<K, V> m, K key) {
                if ( m == null ) return false;
                return m.containsKey(key);
            }
        }
        
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
    }
    
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    
    /**
     * @author Joseph S.
     */
    public static interface Setters {
        
        /////////////////////////////////////////////////////////////////////
        // Setters for various properties, Example: super.setDate(m, Enum.Key.date, entity::setDate);
        /////////////////////////////////////////////////////////////////////
        
        default <K, V> void setDate(Map<String, V> m, Enum key, Lambda.V1<Date> lambda) {
            Statics.setDate(m, key, lambda);
        }
        default <K, V> void setDate(Map<K, V> m, K key, Lambda.V1<Date> lambda) {
            Statics.setDate(m, key, lambda);
        }
        
        default <V> void setDouble( Map<String, V> m, Enum key, Lambda.V1<Double> lambda ) {
            Statics.setDouble(m, key, lambda);
        }
        default <K, V> void setDouble( Map<K, V> m, K key, Lambda.V1<Double> lambda ) {
            Statics.setDouble(m, key, lambda);
        }
        
        default <V> void setLong ( Map<String, V> m, Enum key, Lambda.V1<Long> lambda ) {
            Statics.setLong(m, key, lambda);
        }
        default <K, V> void setLong ( Map<K, V> m, K key, Lambda.V1<Long> lambda ) {
            Statics.setLong(m, key, lambda);
        }
        
        default <V> void setInteger ( Map<String, V> m, Enum key, Lambda.V1<Integer> lambda ) {
            Statics.setInteger(m, key, lambda);
        }
        default <K, V> void setInteger ( Map<K, V> m, K key, Lambda.V1<Integer> lambda ) {
            Statics.setInteger(m, key, lambda);
        }
        
        default <V> void setString ( Map<String, V> m, Enum key, Lambda.V1<String> lambda ) {
            Statics.setString(m, key, lambda);
        }
        default <K, V> void setString ( Map<K, V> m, K key, Lambda.V1<String> lambda ) {
            Statics.setString(m, key, lambda);
        }
        
        default <V> void setBoolean ( Map<String, V> m, Enum key, Lambda.V1<Boolean> lambda ) {
            Statics.setBoolean(m, key, lambda);
        }
        default <K, V> void setBoolean ( Map<K, V> m, K key, Lambda.V1<Boolean> lambda ) {
            Statics.setBoolean(m, key, lambda);
        }
        
        default <V> void set(Map<String, V> m, Enum key, Lambda.V1<V> lambda) {
            Statics.set(m, key, lambda);
        }
        default <K, V> void set(Map<K, V> m, K key, Lambda.V1<V> lambda) {
            Statics.set(m, key, lambda);
        }
        
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        
        /**
         * @author Joseph S.
         */
        public static final class Statics {
            public static <K, V> void setDate(Map<K, V> m, K key, Lambda.V1<Date> lambda) {
                Date val = Getters.Statics.getDate(m, key);
                if ( val != null ) {
                    lambda.call(val);
                }
            }
            public static <V> void setDate(Map<String, V> m, Enum key, Lambda.V1<Date> lambda) {
                setDate(m, key.toString(), lambda);
            }
            
            public static <V> void setDouble(Map<String, V> m, Enum key, Lambda.V1<Double> lambda) {
                setDouble(m, key.toString(), lambda);
            }
            public static <K, V> void setDouble(Map<K, V> m, K key, Lambda.V1<Double> lambda) {
                Double val = Getters.Statics.getDouble(m, key);
                if ( val != null ) {
                    lambda.call(val);
                }
            }
            
            public static <V> void setLong(Map<String, V> m, Enum key, Lambda.V1<Long> lambda) {
                setLong(m, key.toString(), lambda);
            }
            public static <K, V> void setLong(Map<K, V> m, K key, Lambda.V1<Long> lambda) {
                Long val = Getters.Statics.getLong(m, key);
                if ( val != null ) {
                    lambda.call(val);
                }
            }
            
            public static <V> void setInteger(Map<String, V> m, Enum key, Lambda.V1<Integer> lambda) {
                setInteger(m, key.toString(), lambda);
            }
            public static <K, V> void setInteger(Map<K, V> m, K key, Lambda.V1<Integer> lambda) {
                Integer val = Getters.Statics.getInteger(m, key);
                if ( val != null ) {
                    lambda.call(val);
                }
            }
            
            public static <V> void setString(Map<String, V> m, Enum key, Lambda.V1<String> lambda) {
                setString(m, key.toString(), lambda);
            }
            public static <K, V> void setString(Map<K, V> m, K key, Lambda.V1<String> lambda) {
                String val = Getters.Statics.getString(m, key);
                if ( val != null ) {
                    lambda.call( val );
                }
            }
            
            public static <V> void setBoolean(Map<String, V> m, Enum key, Lambda.V1<Boolean> lambda) {
                setBoolean(m, key.toString(), lambda);
            }
            public static <K, V> void setBoolean(Map<K, V> m, K key, Lambda.V1<Boolean> lambda) {
                Boolean val = Getters.Statics.getBoolean(m, key);
                if ( val != null ) {
                    lambda.call(val);
                }
            }
            
            public static <V> void set(Map<String, V> m, Enum key, Lambda.V1<V> lambda) {
                set(m, key.toString(), lambda);
            }
            public static <K, V> void set(Map<K, V> m, K key, Lambda.V1<V> lambda) {
                V v = Getters.Statics.get(m, key);
                if ( v != null ) {
                    lambda.call(v);
                }
            }
        }
        
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////
        
    }
    
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
}
