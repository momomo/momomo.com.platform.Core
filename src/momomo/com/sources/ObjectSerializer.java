package momomo.com.sources;

import momomo.com.annotations.informative.Final;
import momomo.com.annotations.informative.Overriden;
import momomo.com.exceptions.$IOException;
import momomo.com.exceptions.$RuntimeException;
import momomo.com.$Bytes;
import momomo.com.Strings;

import java.io.IOException;

public interface ObjectSerializer {

    Object $deserializeVal$(byte[] bytes ) throws Exception;
    byte[] $serializeVal$  (Object object) throws Exception;

    @Final default Object deserializeVal(byte[] bytes) {
        if ( bytes == null ) return null;
        try {
            return $deserializeVal$(bytes);
        } catch (IOException e) {
            throw new $IOException(e);
        } catch (Exception e) {
            throw new $RuntimeException(e);
        }
    }
    
    @Overriden
    default String deserializeKey(byte[] bytes) {
        return $Bytes.toString(bytes, Strings.CHARSET);
    }

    /////////////////////////////////////////////////////////////////////

    @Final default byte[] serializeVal(Object object) {
        try {
            return $serializeVal$(object);
        } catch (IOException e) {
            throw new $IOException(e);
        } catch (Exception e) {
            throw new $RuntimeException(e);
        }
    }

    @Overriden
    default byte[] serializeKey(String object) {
        return Strings.toBytes(object, Strings.CHARSET);
    }

    /////////////////////////////////////////////////////////////////////
    
}
