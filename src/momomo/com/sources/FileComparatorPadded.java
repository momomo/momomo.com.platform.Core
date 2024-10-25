package momomo.com.sources;

/**
 * Intended to be used when desiring to sort file system (think >> ls) with ... comment ... 
 * 
 * @author Joseph S.
 */
public final class FileComparatorPadded<T> extends FileComparatorBase<T> {
    
    @Override
    protected int compare(String a, String b) {
        int al = a.length(), bl = b.length(), min = Math.min(al, bl), max = Math.max(al, bl);
        
        StringBuilder sba = new StringBuilder(max), sbb = new StringBuilder(max);
        
        boolean pa = pad(sba, max - al);
        append(sba, a);
        
        boolean pb = pad(sbb, max - bl);
        append(sbb, b);
        
        String sa = sba.toString(), sb = sbb.toString();
        
        if (sa.length() == sb.length() && (pb || pa)) {
            return FileComparatorNumeral.compare(a, b, ignoreCase);
        }
        
        return sa.compareTo(sb);
    }
    
    private boolean pad(StringBuilder sb, int diff) {
        int i = -1;
        while (++i < diff) {
            sb.append(" ");
        }
        
        return i > 0;
    }
    
    private boolean append(StringBuilder sb, String s) {
        int i = -1, l = s.length();
        while (++i < l) {
            sb.append(
                ignoreCase ? Character.toLowerCase(s.charAt(i)) : s.charAt(i)
            );
        }
        return i > 0;
    }
}
