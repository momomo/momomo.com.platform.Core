package momomo.com.sources;

import momomo.com.Is;
import momomo.com.Numbers;

/**
 * System.getProperty() | System.setProperty() related. 
 * 
 * A convinient wrapper for setting and getting system property values
 * 
 * Example
 *         public static final Sysprop REDIS_URL      = new Sysprop("momomo.com.redis.url"     , "127.0.0.1");
 *         public static final Sysprop REDIS_PORT     = new Sysprop("momomo.com.redis.port"    , "9002");
 *         public static final Sysprop REDIS_IDLE     = new Sysprop("momomo.com.redis.idle"    , "32");
 *         public static final Sysprop REDIS_PASSWORD = new Sysprop("momomo.com.redis.password", "NMsuiyh2b289BKbjs980HNOLB9982y3g2vj3209_s");
 *    
 *    which can then be used as: 
 *    
 *    REDIS_URL.get()
 *           or 
 *    REDIS_URL.set(...)
 *    
 * If the value has been passed as a system property, from say command line then that takes precedence, otherwise, the value specified in the java code will be used. 
 * This allows for the overriding of properties from the outside, while being able to utilize defaults for development environment and without having to rely on magic configuration files that nobody knows how to configure.  
 * 
 * @author Joseph S.
 */
public final class Sysprop {
    
    private final String property, dephault;
    private String value;
    
    public Sysprop(String property) {
        this(property, null);
    }
    
    public Sysprop(CharSequence property, Object dephault) {
        this.property = property.toString();
        this.dephault = dephault == null ? null : dephault.toString();
    }
    
    public Sysprop set(String value) {
        System.setProperty(property, this.value = value); return this;
    }
    
    public Sysprop setTrue() {
        return set("true");
    }
    
    public Sysprop setFalse() {
        return set("false");
    }
    
    public String get() {
        return get(null);
    }
    
    public String get(String dephault) {
        return value != null ? value : Is.Or(value = System.getProperty(property), Is.Or(dephault, this.dephault));
    }
    
    public boolean isTrue() {
        return equal("true");
    }
    
    public boolean isFalse() {
        return equal("false");
    }
    
    public boolean isNull() {
        return get() == null;
    }
    public boolean isSet() {
        return !isNull();
    }
    
    public boolean equal(String value) {
        return value.equals(get());
    }
    
    public int getInteger() {
        return Numbers.toInt(this.get());
    }
    
}
