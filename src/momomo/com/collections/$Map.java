package momomo.com.collections;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * ChainAbleHashMap by default which differs from HashMap in that it is chainable for methods that normally returns void.
 * 
 * Can be used with other Map types than HashMap.
 *
 * Possible to do: 
 *   map.put(...).put(...).remove(...)
 *
 * We can't implement collection or abstract map, because then void add could not be chained which is the entire purpose for this implementation here.
 * 
 * Method clone, normally part of HashMap has been removed since they can not easily be supported for all Map types.
 *
 * @author Joseph S.
 */
public final class $Map<K, V> implements Serializable {
    private final Map<K, V> map;
    
    public $Map() {
        this(new HashMap<K, V>());
    }
    
    public $Map(Map<K, V> map) {
        this.map = map;
    }
    
    public int size() {
        return map.size();
    }
    
    public boolean isEmpty() {
        return map.isEmpty();
    }
    
    public V get(Object key) {
        return map.get(key);
    }
    
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }
    
    public V put(K key, V value) {
        return map.put(key, value);
    }
    
    public $Map<K, V> putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m); return this;
    }
    
    public V remove(Object key) {
        return map.remove(key);
    }
    
    public $Map<K, V> clear() {
        map.clear(); return this;
    }
    
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }
    
    public Set<K> keySet() {
        return map.keySet();
    }
    
    public Collection<V> values() {
        return map.values();
    }
    
    public Set<Map.Entry<K, V>> entrySet() {
        return map.entrySet();
    }
    
    public V getOrDefault(Object key, V defaultValue) {
        return map.getOrDefault(key, defaultValue);
    }
    
    public V putIfAbsent(K key, V value) {
        return map.putIfAbsent(key, value);
    }
    
    public boolean remove(Object key, Object value) {
        return map.remove(key, value);
    }
    
    public boolean replace(K key, V oldValue, V newValue) {
        return map.replace(key, oldValue, newValue);
    }
    
    public V replace(K key, V value) {
        return map.replace(key, value);
    }
    
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        return map.computeIfAbsent(key, mappingFunction);
    }
    
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return map.computeIfPresent(key, remappingFunction);
    }
    
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return map.compute(key, remappingFunction);
    }
    
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return map.merge(key, value, remappingFunction);
    }
    
    public $Map<K, V> forEach(BiConsumer<? super K, ? super V> action) {
        map.forEach(action); return this;
    }
    
    public $Map<K, V> replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        map.replaceAll(function); return this;
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
    public String toString() {
        return map.toString();
    }
    
}
