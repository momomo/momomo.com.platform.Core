package momomo.com;

import java.util.UUID;

public class Randoms { private Randoms(){}
    
    public static int Integer() {
        return Integer(Integer.MAX_VALUE);
    }
    
    public static int Integer(int max) {
        return Integer(0, max);
    }
    
    public static int Integer(int min, int max) {
        return (int) Long(min, max);
    }
    
    public static long Long() {
        return Long(0, Long.MAX_VALUE);
    }
    
    public static long Long(long max) {
        return Long(0, max);
    }
    
    public static long Long(long min, long max) {
        return min + Math.round( Math.random() * ( (max - min) ) );
    }
    
    public static String UUID() {
        return UUID.randomUUID().toString();
    }
    
    public static String String() {
        return String("", "");
    }
    public static String String(String prepend, String append) {
        return prepend + Long() + append; 
    }
    
}
