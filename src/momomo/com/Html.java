package momomo.com;

import momomo.com.sources.HtmlChars;
import momomo.com.sources.HtmlDecoder;

/**
 * Escape and unescape HTML from/to strings.  
 * 
 * @author Joseph S.
 */
public final class Html { private Html(){}

    public static final HtmlChars CHARS = new HtmlChars();
    
    public static String unescape(String input) {
        return new HtmlDecoder(CHARS, input).decode();
    }
    
    /**
     * Encode a string to the "x-www-form-urlencoded" form, enhanced
     * with the UTF-8-in-URL proposal. This is what happens:
     * <p/>
     * <ul>
     * <li><p>The ASCII characters 'a' through 'z', 'A' through 'Z',
     * and '0' through '9' remain the same.
     * <p/>
     * <li><p>The unreserved characters - _ . ! ~ * ' ( ) remain the same.
     * <p/>
     * <li><p>The space character ' ' is converted into a plus sign '+'.
     * <p/>
     * <li><p>All other ASCII characters are converted into the
     * 3-character string "%xy", where xy is
     * the two-digit hexadecimal representation of the character
     * code
     * <p/>
     * <li><p>All non-ASCII characters are encoded in two steps: first
     * to a sequence of 2 or 3 bytes, using the UTF-8 algorithm;
     * secondly each of these bytes is encoded as "%xx".
     * </ul>
     *
     * @param input The string to be encoded
     * @return The encoded string
     */
    public static String escape(String input) {
        if (input == null) {
            return null;
        }
        StringBuilder escaped = new StringBuilder(input.length() * 2);
        for (int i = 0; i < input.length(); i++) {
            char character = input.charAt(i);
            
            String reference = CHARS.convertToReference(character);
            if (reference != null) {
                escaped.append(reference);
            } else {
                escaped.append(character);
            }
        }
        return escaped.toString();
    }
    
}
