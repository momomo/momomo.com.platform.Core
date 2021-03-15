package momomo.com.sources;

import momomo.com.Lambda;
import momomo.com.annotations.informative.Private;
import momomo.com.IO;

import java.io.IOException;

/**
 * See {@link momomo.com.IO.Iterate} for direct usage
 * 
 * @author Joseph S.
 */
public interface IteratorZip extends IteratorBase<Zip, IteratorZipEntry, IOException> {
    
    @Override
    default <E1 extends Exception> void each(Zip zip, Lambda.R1E<Boolean, ? super IteratorZipEntry, E1> lambda) throws IOException, E1 {
        zip.each(lambda);
    }
    
    @Override
    @Private default Zip from(CharSequence url) {
        return IO.toZip(url);
    }
    
}
