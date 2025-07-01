package momomo.com.sources;

/**
 * Example to use: 
 *   log.setInstance( 
 *      new LogSpeaks()
 *      .speak( Runtimes.class, Terminal.class )
 *   ); 
 *
 * This class will be silenced by default, unless a particular class has been said to allow to speak.
 * 
 * @see LogCustom
 * @see LogSpeaks
 *
 * @author Joseph S.
 */
public final class LogSilent extends LogCustom {
    
    public LogSilent speaks(Object ... include) {
        super.add(include); return this;
    }
    
    @Override
    protected boolean shoudLog(Object object) {
        return contains(object);
    }
    
}
