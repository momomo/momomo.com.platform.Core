package momomo.com;

import momomo.com.exceptions.$RuntimeException;

import java.util.Map;

import static momomo.com.$Maps.map;

/**
 * @see Yarn.$ which contains what you are likely to ever need unless, you'd like to support "#{}" or similar instead of "${}". 
 * 
 * Simple template engine for things like creating a string, such as 
 * 
 *    "My name is ${firstname} ${lastname}"
 *
 * String = Thong = Yarn = Wire
 * 
 * Example: 
 * 
 *    Yarn.$.create("""
 *       id        character varying(255)      NOT NULL  ,
 *       before    character varying(255)                ,               
 *       
 *       CONSTRAINT ${table}_pkey PRIMARY KEY (id)       ,
 *       CONSTRAINT ${table}_ukey UNIQUE      (before)   ,
 *       
 *       FOREIGN KEY (before) REFERENCES ${table}(id)    
 *    """, "table", "mytable")
 *    
 * Is concurrently safe, and therefore can be used from a SINGLETON
 * 
 *    new Yarn("#{", "}") to use something else than the bundled dollar based one which has available as a static default nested within.
 *    
 * Note, for the default, dollar based one Yarn.$ we also have a quick method to do equivalent of Yarn.$.var() with Yarn.$() to create the variables on the fly and reduce clutter a bit.  
 * 
 * @author Joseph S.
 */
public final class Yarn {
    public static final Yarn $ = new Yarn("${", "}");
    
    public static String $(Object object) {
        return $.var(object);
    }
    public static String $(Object object, CharSequence append) {
        return $.var(object, append);
    }
    
    /////////////////////////////////////////////////////////////////////
    // Below is the instance implementation, above statics related.
    /////////////////////////////////////////////////////////////////////
    
    private final CharSequence left,      right;
    private final char[]       leftChars, rightChars;

    public Yarn(CharSequence left, CharSequence right) {
        this.left       = left;
        this.right      = right;

        this.leftChars  = left.toString().toCharArray();
        this.rightChars = right.toString().toCharArray();
    }

    public String create(CharSequence text, CharSequence key, Object val) {
        return create(text, map(key, val));
    }

    public String create(CharSequence text, CharSequence key, Object val, Object k, Object... v) {
        return create(text, map(key, val, k, v));
    }

    public String create(CharSequence text, Map map) {
        return create(text, map, true);
    }

    public String create(CharSequence text, Map<?, ?> map, boolean preserveNulls) {
        return create(text, text, map, preserveNulls);
    }

    private String create(CharSequence original, CharSequence text, Map<?, ?> map, boolean preserveNulls) {
        char[] chars  = text.toString().toCharArray();
        int    length = chars.length;
        int    i      = 0;

        StringBuilder replacement = new StringBuilder(length*2);
        while ( i < length) {

            if ( Is.Equal(leftChars, chars, i) ) {
                i = i+ leftChars.length;

                // Look for the variable
                StringBuilder variable = new StringBuilder();
                while ( i < length ) {

                    if ( Is.Equal(rightChars, chars, i) ) {
                        i = i + rightChars.length;

                        Object val = null;
                        // Variable cannot be just ${}, but should be at least ${v}
                        if ( variable.length() > 0 ) {
                            String key;
                            val = map.get ( key = variable.toString() );

                            // Empty strings for null variables
                            if (val != null) {
                                replacement.append(
                                    // Recursively look for variables within the variable
                                    create(
                                        original, val.toString(), map, preserveNulls
                                    )
                                );
                            }
                            else if ( preserveNulls ) {
                                replacement.append(left).append(key).append(right);
                            }

                            break;
                        }
                        else {
                            throw new $RuntimeException("Error parsing String, no key was provided for String " + original);
                        }
                    }
                    else {
                        variable.append( chars[i] );
                    }

                    i++;
                }

                continue;       // Skip incrementation below

            }
            else {
                replacement.append( chars[i] );
            }

            i++;
        }

        return replacement.toString();
    }

    /**
     * @return the object wrapped by for example ${dollar}
     */
    public String var(Object object) {
        return left + object.toString() + right;
    }
    
    public String var(Object object, CharSequence append) {
        return var(object) + Is.Or(append, "");
    }
    
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    
}
