package momomo.com.sources;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public final class GsonAdapterUnquoted extends TypeAdapter<Unquoted> {
    public static final TypeAdapter<Unquoted> SINGLETON = new GsonAdapterUnquoted();
    
    @Override
    public Unquoted read(JsonReader in) throws IOException {
        in.beginObject();
        String a = in.nextString();
        in.endObject();

        return new Unquoted(a);
    }

    @Override
    public void write(JsonWriter out, Unquoted value) throws IOException {
        out.jsonValue(value.toString());
    }
}
