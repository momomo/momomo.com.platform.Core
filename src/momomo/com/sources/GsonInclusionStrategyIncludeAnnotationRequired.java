package momomo.com.sources;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import momomo.com.annotations.$Include;

import java.util.Collection;
import java.util.Map;

/**
 * Include only content explicitly annotated with @$Include 
 * 
 * @author Joseph S.
 */
public class GsonInclusionStrategyIncludeAnnotationRequired implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getAnnotation($Include.class) == null;
    }

    @Override
    public boolean shouldSkipClass(Class<?> f) {
        return f.getAnnotation($Include.class) == null || !(Map.class.isAssignableFrom(f) && Collection.class.isAssignableFrom(f));
    }
}
