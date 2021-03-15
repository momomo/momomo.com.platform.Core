package momomo.com.sources;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import momomo.com.Lambda;
import momomo.com.IO;

import java.io.File;
import java.io.FileReader;

/**
 * @author Joseph S.
 */
public class Csv {
    public final char separator, quote;
    
    public final CSVParser parser;
    
    private boolean lowercasedColumnKeys = true;
    
    public Csv(char separator, char quote) {
        this.separator = separator;
        this.quote     = quote;
    
        this.parser    = new CSVParserBuilder().withSeparator(separator).withQuoteChar(quote).build();
    }
    
    
    public Csv lowerCaseColumnKeys(boolean lowercasedColumnKeys) {
        this.lowercasedColumnKeys = lowercasedColumnKeys; return this;
    }
    
    /**
     * Each row except for the first one containing the columns which are lowercased
     */
    public <E extends Exception> void foreach(File file, Lambda.V2E<String[], String[], E> lambda) throws Exception {
        IO.withBomAwareBufferedReader(new FileReader(file), (r) -> {
            CSVReader reader = new CSVReaderBuilder(r).withCSVParser(parser).build();
            
            // First row is the column names
            String[] columns = reader.readNext();
            if ( this.lowercasedColumnKeys ) {
                int i = -1; while ( ++i < columns.length ) {
                    columns[i] = columns[i].toLowerCase();
                }
            }
            
            // Row is an array of values from the row
            String[] row; while ( (row = reader.readNext()) != null ) {
                lambda.call(columns, row);
            }
        });
    }
    
}
