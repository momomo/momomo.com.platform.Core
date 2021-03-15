package momomo.com.sources;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * To be implemented by classes that implements CharSequence to allow a class ouput just a string for the entire instance rather than fields as well
 * 
 * @author Joseph S.
 */
public abstract class GsonAdapterCharSequence<T extends java.lang.CharSequence> extends TypeAdapter<T> {
    protected abstract T newInstance(java.lang.CharSequence val);

    @Override
    public T read(JsonReader in) throws IOException {
        return newInstance( in.nextString() );
    }

    @Override
    public void write(JsonWriter out, T val) throws IOException {
        out.value(val.toString());
    }
}
