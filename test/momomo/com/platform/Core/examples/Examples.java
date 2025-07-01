package momomo.com.platform.Core.examples;

import momomo.com.Is;
import momomo.com.Yarn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static momomo.com.$Maps.copy;
import static momomo.com.$Maps.map;
import static momomo.com.$Maps.merge;

public class Examples {
    
    public static void main(String[] args) {
        capitals();
    }
    
    public static void capitals() {
        HashMap<String, String> europe = map(
            "Sweden" , "Stockholm",
            "France" , "Paris",
            "Germany", "Berlin"
        );
        
        merge(europe, "Poland", "Warsaw");
        merge(europe, "Norway", "Oslo");
        
        HashMap<String, String> africa = map(
            "Morocco", "Rabat",
            "Egypt"  , "Cairo"
        );
        
        // Copy europe, then merge africa onto the copy
        HashMap<String, String> world = merge(copy(europe), africa);
    
        /////////////////////////////////////////////////////////////////////
    
        System.out.println("Europe: " + europe.size());
        System.out.println("Africa: " + africa.size());
        System.out.println("World : " + world.size());
        
        for (Map.Entry<String, String> entry : world.entrySet()) {
            System.out.println("Capital in country " + entry.getKey() + " is " + entry.getValue() );
        }
    }
    
    public static void yarn() {
        String[] maps = "AbstractMap, Attributes, AuthProvider, ConcurrentHashMap, ConcurrentSkipListMap, EnumMap, HashMap, Hashtable, Headers, IdentityHashMap, LinkedHashMap, PrinterStateReasons, Properties, Provider, RenderingHints, SimpleBindings, TabularDataSupport, TreeMap, UIDefaults, WeakHashMap".split(", ");
        for (String map : maps) {
            System.out.println(Yarn.$.create("""
            if ( map instanceof ${map} ) { 
                return ((${map}) map).clone();  
            } 
            """, "map", map));
        }
    }
    
    public void is() {
        Object              obj   = null;
        Boolean             bool  = null;
        String[]            array = {};
        ArrayList<String>   list  = new ArrayList<>();
        Map<String, String> map   = new HashMap<>();
    
        Is.Ok(obj);   // false
        Is.Ok(bool);  // false
        Is.Ok(array); // false
        Is.Ok(list);  // false
        Is.Ok(map);   // false  
        Is.Ok("");    // false
        Is.Ok(0);     // true
    
        list.add("1");
        Is.Ok(list);  // true
    
        map.put("a", "1");
        Is.Ok(map);   // true
    
        obj = list;
        Is.Ok(obj);   // true
    
        obj = map;
        Is.Ok(obj);   // true
    
        obj = "hello";
        Is.Ok(obj);   // true
    
        obj = Boolean.TRUE;
        Is.Ok(obj);   // true
        
        
        
    }
    
}
