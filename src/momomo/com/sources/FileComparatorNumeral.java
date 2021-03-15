package momomo.com.sources;

/**
 * Intended to be used when desiring to sort file system (think >> ls) with ... comment ...  
 * 
 * @author Joseph S.
 */
public final class FileComparatorNumeral<T> extends FileComparatorBase<T> {
    
    @Override
    public int compare(String a, String b) {
        return compare(a, b, ignoreCase);
    }
    
    protected static int compare(String a, String b, boolean ignoreCase) {
        if (ignoreCase) {
            a = a.toLowerCase();
            b = b.toLowerCase();
        }
        
        char ac, bc;
        boolean aIsNumber, bIsNumber, bothNumbers, wasCompared = false;
        int lastCompared = 0, al = a.length(), bl = b.length(), to = Math.min(al, bl);
        
        for (int i = 0; i < to; i++) {
            
            ac = a.charAt(i);
            bc = b.charAt(i);
            aIsNumber = Character.isDigit(ac);
            bIsNumber = Character.isDigit(bc);
            bothNumbers = aIsNumber && bIsNumber;
            
            if (wasCompared) {
                if (bothNumbers) {
                    if (lastCompared == 0) {
                        lastCompared = ac - bc;
                    }
                } else if (aIsNumber) {
                    return 1;
                } else if (bIsNumber) {
                    return -1;
                } else if (lastCompared == 0) {
                    if (ac != bc) {
                        return ac - bc;
                    }
                    
                    wasCompared = false;
                } else {
                    return lastCompared;
                }
            } else if (bothNumbers) {
                wasCompared = true;
                
                if (lastCompared == 0) {
                    lastCompared = ac - bc;
                }
                
            } else if (ac != bc) {
                return ac - bc;
            }
        }
        
        if (wasCompared) {
            if (al > bl && Character.isDigit(a.charAt(bl))) {
                return 1;  // a has bigger size, thus b is smaller
            } else if (bl > al && Character.isDigit(b.charAt(al))) {
                return -1;  // b has bigger size, thus a is smaller
            } else if (lastCompared == 0) {
                return al - bl;
            } else {
                return lastCompared;
            }
        } else {
            return al - bl;
        }
    }
}
