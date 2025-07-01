package momomo.com.sources;

import momomo.com.annotations.informative.Development;
import momomo.com.$Lists;

/**
 * Concurrently safe. 
 * 
 * @see MovingAverageCumulative#example() 
 * 
 * @author Joseph S.
 */
public final class MovingAverageCumulative extends MovingAverage {
    private long n = 0;
    
    @Override
    protected double add(double average, double number) {
        return (n * average + number) / ++n;
    }

    @Development
    private static void example() {
        MovingAverageCumulative average = new MovingAverageCumulative();
        
        for ( Integer number : $Lists.toList(1, 1000) ) {
            System.out.println( average.add(number) );
        }
    }

}
