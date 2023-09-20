package momomo.com.sources;

/**
 * Use this wrapper to tell json generator not to quote the contents
 * 
 * @see GsonAdapterUnquoted
 * 
 * @author Joseph S.
 */
public final class Unquoted implements $CharSeq {
    private final CharSequence characters;

    public Unquoted(CharSequence characters) {
        this.characters = characters;
    }

    @Override
    public String toString() {
        return characters.toString();
    }
}
