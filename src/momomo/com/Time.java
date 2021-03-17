package momomo.com;

import java.sql.Timestamp;

/**
 * @author Joseph S.
 */
public class Time {
    
    public static Timestamp stamp() {
        return new Timestamp(System.currentTimeMillis());
    }
    
}
