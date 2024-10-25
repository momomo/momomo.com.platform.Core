package momomo.com.sources;

import momomo.com.Regexes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Joseph S.
 */
public abstract class RegexReplacor<E extends Exception> {
    
    protected final StringBuilder out;
    protected final String        text;
    protected final Pattern       pattern;
    protected final Matcher       matcher;
    
    public RegexReplacor(Pattern regex, String text) {
        this(regex, new StringBuilder(), text);
    }
    
    public RegexReplacor(Pattern regex, StringBuilder out, String text) {
        this.out     = out;
        this.text    = text;
        this.pattern = regex;
        this.matcher = regex.matcher(text);
    }
    
    protected abstract void match() throws E;
    
    public final StringBuilder go() throws E {
        
        int from = 0; while (matcher.find()) {
            
            out(beforeMatch());
            
            out( atMatch( text.substring(from, matcher.start()) ));
            
            out(afterMatch());
            
            match();
            
            from = matcher.end();
        }
        
        out( beforeCompletion() );
        out( atCompletion(text.substring(from)) );
        out( afterCompletion() );
        
        return out;
    }
    
    protected final String group(String group) {
        return Regexes.group(matcher, group);
    }
    
    protected final String group(int group) {
        return Regexes.group(matcher, group);
    }
    
    /////////////////////////////////////////////////////////////////////
    
    /**
     * Per match. 
     */
    protected String beforeMatch() {
        return "";
    }
    
    /**
     * Per match. 
     */
    protected String atMatch(String match) {
        return match.trim();
    }
    
    /**
     * Per match.  
     */
    protected String afterMatch() {
        return "";
    }
    
    /////////////////////////////////////////////////////////////////////
    
    /**
     * On end result. 
     */
    protected String beforeCompletion() {
        return "";
    }
    
    /**
     * On end result. 
     */
    protected String atCompletion(String text) {
        return text.trim();
    }
    
    /**
     * On end result. 
     */
    protected String afterCompletion() {
        return "";
    }
    
    /////////////////////////////////////////////////////////////////////
    
    protected final void out(Object text) {
        this.out.append(text);
    }
}
