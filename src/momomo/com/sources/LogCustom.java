package momomo.com.sources;

import momomo.com.Lambda;
import momomo.com.log;
import org.apache.log4j.Logger;

import java.util.HashSet;

/**
 * @see momomo.com.log
 * @see LogSilent
 * @see LogSpeaks
 *  
 * @author Joseph S.
 */
abstract class LogCustom extends log {
    protected final HashSet<Object> rules = new HashSet<>();
    
    protected final LogCustom add(Object ... rules) {
        for (Object rule : rules) {
            this.rules.add(rule);
        }
        return this;
    }
    
    @Override
    protected final void log(Lambda.V2<Logger, String> lambda, Object... objects) {
        if (  shoudLog(objects[0]) ) {
            super.log(lambda, objects);
        }
        
        // Do nothing
    }
    
    protected final boolean contains(Object object) {
        return rules.contains(object);
    }
    
    protected abstract boolean shoudLog(Object object);
}
