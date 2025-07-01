package momomo.com.sources;

/**
 * Intended to be used when desiring to sort file system (think >> ls) using a more natural sorting order
 * 
 * @author Joseph S.
 */
public final class FileComparatorNatural<T> extends FileComparatorBase<T> {
    
    @Override
    public int compare(String a, String b) {
        if (ignoreCase) {
            a = a.toLowerCase();
            b = b.toLowerCase();
        }
        
        boolean aIsNumber, bIsNumber, bothNumbers;
        
        char ac, bc;
        int al = a.length(), bl = b.length(), to = Math.min(al, bl);
        
        boolean wasCompared = false; int lastCompared = 0; int i = -1; while (++i < to) {
            ac = a.charAt(i);
            bc = b.charAt(i);
            
            aIsNumber = Character.isDigit(ac);
            bIsNumber = Character.isDigit(bc);
            bothNumbers = aIsNumber && bIsNumber;
            
            if (bothNumbers) {
                if (lastCompared == 0) {
                    wasCompared = true;
                    lastCompared = ac - bc;
                }
                continue;
            }
            
            // There was a number previously. We just look at this last one
            Integer x = magic(wasCompared, lastCompared, al, bl);
            if (x != null) return x;
            
            return ac - bc;
        }
        
        // At this point they are equal. But we look at length again.
        
        Integer x = magic(wasCompared, lastCompared, al, bl);
        if (x != null) return x;
        
        // We now prioritize the shortest one to go ahead
        return bl - al;
    }
    
    private static Integer magic(boolean wasCompared, int lastCompared, int al, int bl) {
        if (wasCompared) {
            
            if (al > bl) {
                return 1;
            }
            
            if (al < bl) {
                return -1;
            }
            
            if (lastCompared != 0) {
                return lastCompared;
            }
            
        }
        return null;
    }
}
