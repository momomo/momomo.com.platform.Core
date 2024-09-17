package momomo.com;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Joseph S.
 */
public final class Hasher { private Hasher(){}
    
    public static $Bytes sha256(byte[] bytes) {
        return hash(bytes, "SHA-256");
    }
    public static $Bytes sha256(File file) {
        return hash(file, "SHA-256");
    }
    public static $Bytes sha256(CharSequence text) {
        return hash(text, "SHA-256");
    }
    
    public static $Bytes sha1(File file) {
        return hash(file, "SHA1");
    }
    public static $Bytes sha1(CharSequence text) {
        return hash(text, "SHA1");
    }
    
    public static $Bytes md5(File file) {
        return hash(file, "MD5");
    }
    public static $Bytes md5(CharSequence text) {
        return hash(text, "MD5");
    }
    
    public static $Bytes hash(File file, String algorithm) {
        try (FileInputStream is = new FileInputStream(file); BufferedInputStream bfr = new BufferedInputStream(is)){
            return hash( bfr, algorithm );
        } catch (IOException e) {
            throw Ex.runtime(e);
        }
    }
    
    public static $Bytes hash(CharSequence text, String algorithm) {
        return hash(text, algorithm, Strings.CHARSET);
    }
    
    public static $Bytes hash(CharSequence text, String algorithm, Charset charset) {
        return hash( new ByteArrayInputStream( Strings.toBytes(text, charset) ), algorithm );
    }
    
    public static $Bytes hash(InputStream is, String algorithm) {
        // Did not work!
        // try (InputStream is = new FileInputStream(file); DigestInputStream dis = new DigestInputStream(is, digester)) {}
    
        // Provides more flexibility
        try (is) {
            MessageDigest digester = MessageDigest.getInstance(algorithm);
            
            byte[] buffer = new byte[IO.BUFFER_SIZE];
            
            for (int read; (read = is.read(buffer)) != -1;) {
                digester.update(buffer, 0, read);
            }
    
            return new $Bytes(digester.digest());
            
        } catch (NoSuchAlgorithmException | IOException e) {
            throw Ex.runtime(e);
        }
    }
    
    public static $Bytes hash(byte[] bytes, String algorithm) {
        try {
            MessageDigest digester = MessageDigest.getInstance(algorithm);
            
            digester.update(bytes);
            
            return new $Bytes(digester.digest());
            
        } catch (NoSuchAlgorithmException e) {
            throw Ex.runtime(e);
        }
    }
    
}
