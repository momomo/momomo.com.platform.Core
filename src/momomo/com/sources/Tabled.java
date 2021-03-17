package momomo.com.sources;

import momomo.com.$Lists;
import momomo.com.Strings;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * This implementation generate table column like structures
 * 
 * Example
 * 
 *  new Tabled()
 *      .row("Hi", "My", "name", "is", "Joseph")
 *      .row("Hello", "Mine", "name", "bist", "Joseph")
 *  ;
 *  
 *  would give
 *      Hi    My   name is   Joseph 
 *      Hello Mine name bist Joseph 
 *  
 *  Whereas 
 *  
 *  new Tabled(new Params().pad(Pad.LEFT).separation(2))
 *      .row("Hi", "My", "name", "is", "Joseph")
 *      .row("Hello", "Mine", "name", "bist", "Joseph")
 * ;     
 * 
 * would give 
 * 
 *      Hi    My  name    is  Joseph
 *   Hello  Mine  name  bist  Joseph
 * 
 * @author Joseph S.
 */
public class Tabled {
    private final ArrayList<Integer> lengths = new ArrayList<>();
    private final ArrayList<Row>     rows    = new ArrayList<>();
    
    private final Pad pad;
    private final int separation;
    
    /////////////////////////////////////////////////////////////////////
    public static final class Params {
        private Pad pad        = Pad.RIGHT;
        private int separation = 1;
        
        public Params pad(Pad pad) {
            this.pad = pad; return this;
        }
        
        public Params separation(int separation) {
            this.separation = separation; return this;
        }
    }
    public Tabled() {
        this(new Params());
    }
    public Tabled(Params params) {
        this.pad        = params.pad;
        this.separation = params.separation;
    }
    
    /////////////////////////////////////////////////////////////////////
    
    public Tabled row(List<CharSequence> columns) {
        return row($Lists.toArray(CharSequence.class, columns));
    }
    
    public Tabled row(CharSequence ... columns) {
        int i = -1; while ( ++i < columns.length ) {
            
            CharSequence column = columns[i];
            int    length = column.length();
            
            if ( lengths.size() <= i ) {
                lengths.add(length);
            }
            else {
                Integer previous = lengths.get(i);
                if ( length > previous) {
                    lengths.set(i, length);
                }
            }
        }
        
        rows.add( new Row(columns) ); return this;
    }
    
    /////////////////////////////////////////////////////////////////////
    
    public String toString() {
        return toString(new StringBuilder());
    }
    
    public String toString(StringBuilder sb) {
        return toString("", sb);
    }
    public String toString(String prepend, StringBuilder sb) {
        
        for (Row row : rows) {
            
            sb.append(prepend);
            
            int i = -1; while ( ++i < row.columns.length ) {
                
                CharSequence column = row.columns[i];
                
                int pad        = this.lengths.get(i) - column.length() + separation;
                String padding = Strings.multiply(" ", pad);
                
                if ( pad > 0 ) {
                    if ( this.pad == Pad.RIGHT ) {
                        column = column + padding;
                    }
                    else if (this.pad == Pad.LEFT ) {
                        column = padding + column;
                    }
                }
                
                sb.append(column);
            }
            
            sb.append(Strings.NEWLINE);
        }
        
        return sb.toString();
    }
    
    /////////////////////////////////////////////////////////////////////
    
    enum  Pad {
        RIGHT, LEFT
    }
    
    /////////////////////////////////////////////////////////////////////
    
    private static final class Row {
        private final CharSequence[] columns;
        
        public Row(CharSequence ... columns) {
            this.columns = columns;
        }
    }
    
    /////////////////////////////////////////////////////////////////////
    
    @momomo.com.annotations.informative.Development public static final class Development {
        public static void main(String[] args) {
            addRows(new Tabled());
            addRows(new Tabled(new Params().pad(Pad.LEFT).separation(2)));
        }
        
        private static void addRows(Tabled table) {
            table
                .row("Hi", "My", "name", "is", "Joseph")
                .row("Hello", "Mine", "name", "bist", "Joseph")
            ;
            System.out.println( table.toString() );
        }
        
    }
    
    /////////////////////////////////////////////////////////////////////
    
}
