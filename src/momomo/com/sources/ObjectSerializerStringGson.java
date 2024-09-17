package momomo.com.sources;

import momomo.com.annotations.informative.Overridable;
import momomo.com.exceptions.$RuntimeException;
import momomo.com.Gson;
import momomo.com.Strings;

/**
 * 
 * This serializer using Google Gson to serialize objects. It will write the type of class and "{json:string}", and then recreate it using google gson, using the stored clas to recreate the type from the json the stored {json:string} 
 * 
 * First line the classname is written. 
 * Then the {json:string} comes after.
 * 
 * @author Joseph S.
 */
public interface ObjectSerializerStringGson extends ObjectSerializer {
    public static final ObjectSerializerStringGson DEFAULT = new ObjectSerializerStringGson() {};

    @Overridable
    default com.google.gson.Gson gson() {
        return Gson.ALL.gson;
    }

    @Override
    default Object $deserializeVal$(byte[] bytes) {
        String text = deserializeKey(bytes);

        StringBuilder klassName = new StringBuilder();
        int n = 0, i = -1; while ( ++i < text.length() ) {
            char c = text.charAt(i);

            if ( c == '\n' ) {
                break;
            }

            klassName.append(c);
        }

        try {
            Class<?> klass = Class.forName(klassName.toString());

            CharSequence json = text.subSequence(i+1, text.length());

            return gson().fromJson(json.toString(), klass);
        }
        catch( ClassCastException | ClassNotFoundException e) {
            throw new $RuntimeException(e);
        }
    }

    @Override
    default byte[] $serializeVal$(Object object) {
        StringBuilder sb = new StringBuilder();
        sb.append(object.getClass().getName());

        sb.append(Strings.NEWLINE);

        sb.append(gson().toJson(object));

        return serializeKey(sb.toString());
    }
}
