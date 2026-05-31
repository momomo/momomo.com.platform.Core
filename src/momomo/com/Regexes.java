package momomo.com;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Notes 
 *  https://regex101.com/r/vT9pX4/8
 *
 * Regex related operations.
 * 
 * Also see {@link momomo.com.sources.RegexReplacor}
 * 
 * @author Joseph S.
 */
public final class Regexes {
    
    // Just a reference on what to use, as incorrect use can cause serious problems.
    // There are many other combos that are less good, and some that are fatal and will result in stackoverflow errors.
    // Example of other less good combos: (?:.|\\s)*? , (?s:.*?) , (?:.*\s?)*
    // Should possibly be followed by a non greedy ?
    public static final String DOTALL = "[\\s\\S]*";
    
    /////////////////////////////////////////////////////////////////////
    
    public static String group(Matcher matcher, int group) {
        try {
            return matcher.group(group);
        }
        catch (Throwable e) {
            return null;
        }
    }
    
    public static String group(Matcher matcher, CharSequence group) {
        try {
            return matcher.group(group.toString());
        }
        catch (Throwable e) {
            return null;
        }
    }
    
    /////////////////////////////////////////////////////////////////////
    
    public static String multiline(CharSequence regex) {
        return "(?ms)" + regex;
    }
    
    /////////////////////////////////////////////////////////////////////
    
    /**
     * matches differs from find in that the entire regex has to match
     * 
     * @return null if no match, otherwise the matcher
     */
    public static Matcher matches(CharSequence text, Pattern pattern) {
        Matcher matcher = pattern.matcher(text);
        if ( matcher.matches() ) {
            return matcher;
        }
        return null;
    }
    
    /**
     * Regexes.matches()
     * Regexes.matches.any
     */
    public static final class matches {

        /**
         * matches any portion of the regex within text
         * Use return value as: matcher.group(1) ... matcher.group(2);
         */
        public static Matcher any(CharSequence text, CharSequence regex) {
            return any(text, pattern(regex) );
        }
    
        public static Matcher any(CharSequence text, Pattern pattern) {
            Matcher matcher = pattern.matcher(text);
            return matcher.find() ? matcher : null;
        }
    }
    
    /////////////////////////////////////////////////////////////////////
    
    public static Pattern pattern(CharSequence regex) {
        return Pattern.compile(regex.toString());
    }
    
    /**
     * Regexes.pattern()
     * Regexes.pattern.multiline
     * Regexes.pattern.or
     */
    public static final class pattern {
        /**
         * Note that multiline will match ^ $ against each line as well as begging and end of text
         */
        public static Pattern multiline(CharSequence regex) {
            return pattern(Regexes.multiline(regex));
        }
    
        public static Pattern or(CharSequence ... regexes) {
            StringBuilder sb = new StringBuilder(regexes.length * 100).append(regexes[0]);
            int i = 0; while (++i < regexes.length) {
                sb.append("|").append(regexes[i]);
            }
            return pattern(sb);
        }
    }
    
    /////////////////////////////////////////////////////////////////////
}
