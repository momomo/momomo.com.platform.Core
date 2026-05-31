package momomo.com;

import java.io.File;
import java.util.Arrays;

/**
 * Lower case and upper case method names differ. 
 * 
 * hashcode(..)  returns an always positive hashcode
 * hashCode(...) returns +- Integer.MAX_VALUE
 * 
 * @author Joseph S.
 */
public class Hashcodes { private Hashcodes(){}
    
    /////////////////////////////////////////////////////////////////////
    // object
    /////////////////////////////////////////////////////////////////////
    
    /**
     * Standard hashCode
     */
    public static int hashCode(Object obj) {
        return obj.hashCode();
    }
    
    /**
     * lowercased hashcode for hashcodes that are always > 0 to INTEGER.MAX_VALUE * 2  
     */
    public static long hashcode(Object obj) {
        return hashcode( hashCode(obj) );
    }
    
    /////////////////////////////////////////////////////////////////////
    // int
    /////////////////////////////////////////////////////////////////////
    
    /**
     * lowercased hashcode for hashcodes that are always > 0 to INTEGER.MAX_VALUE * 2  
     */
    public static int hashCode(int hashCode) {
        return hashCode;   // Nothing to do, already is. Added for consistency alone.
    }
    
    /**
     * lowercased hashcode for hashcodes that are always > 0 to INTEGER.MAX_VALUE * 2  
     */
    public static long hashcode(int hashCode) {
        return ((long) hashCode) + Integer.MAX_VALUE + 1L;
    }
    
    /////////////////////////////////////////////////////////////////////
    // characters
    /////////////////////////////////////////////////////////////////////
    
    /**
     * lowercased hashcode for hashcodes that are always > 0 to INTEGER.MAX_VALUE * 2
     */
    public static long hashcode(CharSequence characters) {
        return hashcode( hashCode(characters) );
    }
    
    public static int hashCode(CharSequence characters) {
        return hashCode( (Object)characters.toString() );
    }
    
    /**
     * More randomized hashcode using shah256 and then java hashcode to ensure hashcodes that are always > 0 to INTEGER.MAX_VALUE * 2
     * where using java's Object hashcode will only result in small difference in output if there are small differences in input, whereas this version will produce a more 'random' value.
     */    
    public static long hashcodeSHA256(CharSequence characters) {
        return hashcode( hashCodeSHA256(characters) );
    }
    
    /**
     * More randomized hashcode using shah256 and then java hashcode to ensure hashcodes that are always > 0 to INTEGER.MAX_VALUE * 2
     * where using java's Object hashcode will only result in small difference in output if there are small differences in input, whereas this version will produce a more 'random' value.
     */
    public static int hashCodeSHA256(CharSequence characters) {
        return hashCode( Hasher.sha256(characters).bytes );
    }
    
    /////////////////////////////////////////////////////////////////////
    // file
    /////////////////////////////////////////////////////////////////////
    
    /**
     * @return the hashcode based on the file's byte contents
     */
    public static long hashcode(File file) {
        return hashcode( hashCode(file) );
    }
    public static int hashCode(File file) {
        return hashCode( IO.bytes(file) );
    }
    
    /**
     * @return the hashcode based on the file's byte contents
     */
    public static long hashcodeSHA256(File file) {
        return hashcode( hashCodeSHA256(file) );
    }
    public static int hashCodeSHA256(File file) {
        return hashCode( Hasher.sha256(file).bytes );
    }
    
    /////////////////////////////////////////////////////////////////////
    // bytes
    /////////////////////////////////////////////////////////////////////
    
    /**
     * lowercased hashcode for hashcodes that are always > 0 to INTEGER.MAX_VALUE * 2  
     */
    public static long hashcode(byte[] bytes) {
        return hashcode( hashCode(bytes) );
    }
    
    /**
     * Standard hashCode less or larger than zero
     */
    public static int hashCode(byte[] bytes) {
        return Arrays.hashCode(bytes);
    }
    
    /**
     * @return the hashcode based on the file's byte contents
     */
    public static long hashcodeSHA256(byte[] bytes) {
        return hashcode( hashCodeSHA256(bytes) );
    }
    public static int hashCodeSHA256(byte[] bytes) {
        return hashCode( Hasher.sha256(bytes) );
    }
    
}
