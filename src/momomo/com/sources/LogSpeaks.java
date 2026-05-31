package momomo.com.sources;

/**
 * 
 * Example to use: 
 *   log.setInstance( 
 *      new LogSpeaks()
 *      .silence( Runtimes.class, Terminal.class)
 *   ); 
 *   
 * This class will speak by default, unless a particular class has been silenced.    
 * 
 * @author Joseph S.
 */
public final class LogSpeaks extends LogCustom {
    public LogSpeaks silence(Object ... silence) {
        super.add(silence); return this;
    }
    
    @Override
    protected boolean shoudLog(Object object) {
        return !contains(object);
    }
}
