package momomo.com.sources;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import momomo.com.annotations.$Exclude;
import momomo.com.annotations.$Include;

import java.lang.reflect.Modifier;

/**
 * Privates included, unless @{@link $Exclude} is present
 */
public class GsonInclusionStrategyAll implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        // TODO Check for class level annotations as well. If exclude, then include must be present
        if (f.getAnnotation($Exclude.class) != null) return true;

        return false;
    }

    @Override
    public boolean shouldSkipClass(Class<?> f) {
        // TODO If exlude on class level is defined then a nested field can still be included as of now?
        int modifier = f.getModifiers();
        
        if (f.getAnnotation($Exclude.class) != null) return true;
        if (f.getAnnotation($Include.class) != null || modifier == Modifier.PUBLIC) return false;

        return modifier == Modifier.PRIVATE || modifier == Modifier.PROTECTED;
    }
}
