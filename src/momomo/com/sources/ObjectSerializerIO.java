package momomo.com.sources;

import momomo.com.Ex;
import momomo.com.IO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Not recommended to use this native serialization
 */
public interface ObjectSerializerIO extends ObjectSerializer {
    ObjectSerializerIO DEFAULT = new ObjectSerializerIO() {};
    
    @Override
    default Object $deserializeVal$(byte[] bytes) {
        return Statics.deserialize( bytes );
    }

    @Override
    default byte[] $serializeVal$(Object bytes) {
        return Statics.serialize( bytes );
    }
    
    public static final class Statics {
        public static byte[] serialize(Object o) {
            try (ByteArrayOutputStream out = new ByteArrayOutputStream(); ObjectOutputStream os = new ObjectOutputStream(out); ) {
                os.writeObject(o);
                return out.toByteArray();
            } catch (IOException e) {
                throw Ex.runtime(e);
            }
        }
    
        public static byte[] serialize(File file, Object o) {
            byte[] bytes = serialize(o); IO.write(file, bytes); return bytes;
        }
    
        public static <T> T deserialize(File file)  {
            return deserialize(IO.bytes(file));
        }
    
        public static <T> T deserialize(byte[] data) {
            try {
                try (ByteArrayInputStream in = new ByteArrayInputStream(data); ObjectInputStream is = new ObjectInputStream(in); ) {
                    return (T) is.readObject();
                }
            } catch (IOException | ClassNotFoundException e) {
                throw Ex.runtime(e);
            }
        }
    }

}
