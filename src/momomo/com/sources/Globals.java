package momomo.com.sources;

import momomo.com.$Environment;
import momomo.com.Is;

import java.util.HashMap;

public class Globals {
    
    public static final String SERVER_URL = "/server/";
    public static final String ASSETS     = "assets/";
    public static final String ASSETS_URL = SERVER_URL + ASSETS;
    
    public static final String MMM_DATABASE_SERVER_URL               = "momomo.com.database.server.url";
    public static final String MMM_DATABASE_SERVER_PORT              = "momomo.com.database.server.port";
    public static final String MMM_DATABASE_SERVER_PASSWORD          = "momomo.com.database.server.password";
    public static final String MMM_DATABASE_ENVIRONMENT              = "momomo.com.database.environment";
    public static final String MMM_DATABASE_DROP_ALL                 = "momomo.com.database.drop.all";
    
    public static       boolean SQL_LOGGING = false; // !Is.Production();
    
    public static class Configurable {
        public static final Sysprop ALLOW_DEVELOPMENT_OVER_PRODUCTION       = $Environment.Configurable.ALLOW_DEVELOPMENT_OVER_PRODUCTION;
    
        public static final Sysprop DATABASE_ENVIRONMENT                    = new Sysprop(MMM_DATABASE_ENVIRONMENT    , $Environment.active());
        public static final Sysprop DATABASE_DROP_ALL                       = new Sysprop(MMM_DATABASE_DROP_ALL       , !Is.Production() );
        public static final Sysprop DATABASE_SERVER_URL                     = new Sysprop(MMM_DATABASE_SERVER_URL     , "localhost");
        public static final Sysprop DATABASE_SERVER_PORT                    = new Sysprop(MMM_DATABASE_SERVER_PORT    , false ? "9001" : "5432");
        public static final Sysprop DATABASE_SERVER_PASSWORD                = new Sysprop(MMM_DATABASE_SERVER_PASSWORD, "postgres");
    }
}
