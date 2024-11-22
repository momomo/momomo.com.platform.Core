/* Copyright(C) 2014 - 2020 Momomo LTD. Proprietary and confidential. Usage of this file on any medium without a written consent by Momomo LTD. is strictly prohibited. All Rights Reserved. */
package momomo.com;

import com.google.gson.ExclusionStrategy;
import com.google.gson.GsonBuilder;
import momomo.com.sources.GsonAdapterUnquoted;
import momomo.com.sources.GsonInclusionStrategyAll;
import momomo.com.sources.GsonInclusionStrategyIncludeAnnotationRequired;
import momomo.com.sources.GsonInclusionStrategyIncludePublicsUnlessExcludeAnnotationPresent;
import momomo.com.sources.Unquoted;
import momomo.com.collections.$MapQuick;

import java.util.HashMap;
import java.util.Map;

/**
 * Example
 *  Gson.PUBLICS.toJson( ... );
 *  Gson.RESTRICTIVE.toJson( ... );
 *  
 * @author Joseph S.
 */
public final class Gson {
    
    private static final GsonInclusionStrategyAll INCLUSION_STRATEGY_ALL = new GsonInclusionStrategyAll();
    public static final Gson ALL        = new Gson(builder(INCLUSION_STRATEGY_ALL).create()                    );
    public static final Gson ALL_PRETTY = new Gson(builder(INCLUSION_STRATEGY_ALL).setPrettyPrinting().create());
    
    private static final GsonInclusionStrategyIncludePublicsUnlessExcludeAnnotationPresent INCLUSION_STRATEGY_PUBLICS = new GsonInclusionStrategyIncludePublicsUnlessExcludeAnnotationPresent();
    public static final Gson PUBLICS        = new Gson(builder(INCLUSION_STRATEGY_PUBLICS).create()                    );
    public static final Gson PUBLICS_PRETTY = new Gson(builder(INCLUSION_STRATEGY_PUBLICS).setPrettyPrinting().create());
    
    private static final GsonInclusionStrategyIncludeAnnotationRequired INCLUSION_STRATEGY_RESTRICTIVE = new GsonInclusionStrategyIncludeAnnotationRequired();
    public static final Gson RESTRICTIVE_PRETTY = new Gson(builder(INCLUSION_STRATEGY_RESTRICTIVE).setPrettyPrinting().create());
    public static final Gson RESTRICTIVE        = new Gson(builder(INCLUSION_STRATEGY_RESTRICTIVE).create()                    );
    
    public final com.google.gson.Gson gson;
    
    private Gson(com.google.gson.Gson gson) {
        this.gson = gson;
    }
    
    public String toJson(Object obj) {
        return gson.toJson(obj);
    }
    
    public <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
    
    public <V> $MapQuick<String, Object> fromJson(String json) {
        return new $MapQuick<>(fromJson(json, 1));
    }
    
    /**
     * @param overload is not used and only added for overloading purposes
     */
    public <V> Map<String, V> fromJson(String json, int overload) {
        Map<String, V> map = fromJson(json, HashMap.class);
        
        if (map == null) {
            map = new HashMap<>();
        }
        
        return map;
    }
    
    public static GsonBuilder builder(ExclusionStrategy strategy) {
        return new GsonBuilder()
            .setExclusionStrategies(strategy)
            .registerTypeAdapter(Unquoted.class, GsonAdapterUnquoted.SINGLETON)
            ;
    }
}
