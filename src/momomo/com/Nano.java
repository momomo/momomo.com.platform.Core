//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// https://github.com/momomo/momomo.com.platform.Nanotime
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package momomo.com;

import momomo.com.sources.Nanotime;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * @see momomo.com.sources.Nanotime
 * 
 * @author Joseph S.
 */
public final class Nano { private Nano(){}
    
    /////////////////////////////////////////////////////////////////////

    /**
     * Returns higher time precision than System.currentTimeMillis() in nano seconds expressed in a long 
     */
    public static long time() {
        return Nanotime.getInstance().get();
    }
    
    /////////////////////////////////////////////////////////////////////
    
    /**
     * Returns higher time precision than System.currentTimeMillis() in nano seconds, sets and returns a Timestamp which has support for nanosecond resolution
     * 
     * toString() -> 2021-03-25 22:15:28.986068681 
     */
    public static Timestamp timestamp() {
        return Nanotime.getInstance().timestamp();
    }
    
    /////////////////////////////////////////////////////////////////////
    
    /**
     * toString() -> 2021-03-25T21:18:49.431440982Z 
     */
    public static Instant instant() {
        return Nanotime.getInstance().instant();
    }
    
    /////////////////////////////////////////////////////////////////////
    /**
     * toString() -> 2021-03-25T21:15:28.989876426
     */
    public static LocalDateTime datetime() {
        return Nanotime.getInstance().datetime();
    }
    
    public static LocalDateTime datetime(ZoneOffset zone) {
        return Nanotime.getInstance().datetime(zone);
    }
    
    /////////////////////////////////////////////////////////////////////
    /**
     * toString() -> 21:18:34.260363177
     */
    public static LocalTime localtime() {
        return Nanotime.getInstance().localtime();
    }
    
    public static LocalTime localtime(ZoneId zone) {
        return Nanotime.getInstance().localtime(zone);
    }
    
    /////////////////////////////////////////////////////////////////////
    
    /**
     * toString() -> 2021-03-25T21:18:49.434622190Z
     */
    public static OffsetDateTime offsettime() {
        return Nanotime.getInstance().offsettime();
    }
    
    public static OffsetDateTime offsettime(ZoneOffset zone) {
        return Nanotime.getInstance().offsettime(zone);
    }
    
    /////////////////////////////////////////////////////////////////////
    
    /**
     * toString() -> 2021-03-25T21:18:49.434488996Z
     */
    public static ZonedDateTime zonedtime() {
        return Nanotime.getInstance().zonedtime();
    }
    
    public static ZonedDateTime zonedtime(ZoneId zone) {
        return Nanotime.getInstance().zonedtime(zone);
    }
    
    /////////////////////////////////////////////////////////////////////
    
    private static void main(String[] args) {
        System.out.println(timestamp());
        System.out.println(datetime());
        System.out.println(localtime());
        System.out.println(instant());
        System.out.println(zonedtime());
        System.out.println(offsettime());
    }
}
    
