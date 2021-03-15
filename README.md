## momomo.com.platform.Core

#### This module is essentially what makes the Core of several of momomo.com's public releases

#### Our other GitHub repositories

* [momomo.com.platform.Lambda](https://github.com/momomo/momomo.com.platform.Lambda)  
Contains a bunch of `functional interfaces` similar to `Runnable`, `Supplier`, `Function`, `BiFunction`, `Consumer` `...` and so forth all packed in a easily accessed and understood intuitive pattern.
  
`Lambda.V1E`, `Lambda.V2E`, `Lambda.R1E`, `Lambda.R2E` are used plenty in examples below.

### Maven dependency available on maven central [search.maven.org](https://search.maven.org/search?q=com.momomo)
#### Dependency   
```xml
<dependency>
  <groupId>com.momomo</groupId>
  <artifactId>momomo.com.platform.Core</artifactId>
  <version>2.6.4</version>
</dependency>                                                      
```                         
#### Repository
```xml
<repository>
    <id>maven-central</id>
    <url>http://repo1.maven.org/maven2</url>
</repository>
```

### Important

#### [Exceptions](src/momomo/com/exceptions)

Our IO related operations, nor do most of our other API's, throw a bunch of different checked `exceptions` for various things. Instead we usually transform a checked `exception` into to a `runtime` `exception` equivalent.

######For instance  
For a thrown `IOException` we will `throw new $IOException(original)`.   
For a thrown `URISyntaxException` we will `throw new $URISyntaxException(original)`.   
For all other not specially tailored exception handling we will simply `throw new $RuntimeException(original)`.    
We usually do this automagically using our `Ex.runtime(exception)` method(s) to also avoid wrapping an `Exception` twice. 

#### [IO.java](src/momomo/com/IO.java) sample

```java     
// Some eachLine signatures (plenty more)                                                                  
IO.eachLine (CharSequence, Lambda.R2E<Boolean, String, Integer, E>) throws E         
IO.eachLine (File        , Lambda.R2E<Boolean, String, Integer, E>) throws E            
IO.eachLine (InputStream , Lambda.R2E<Boolean, String, Integer, E>) throws E  
IO.eachLine (URL         , Lambda.R2E<Boolean, String, Integer, E>) throws E

// Iterate each line in file and operate on eachLine 
IO.eachLine(file, (line, number) -> {
    int i      = line.indexOf("=");
    String key = line.substring(0, i), val = line.substring(i + 1); 

    System.out.println( "On line number " + number " we found " + val + "=" + key );
});
// ... and more ...
```

```java
IO.write (CharSequence , ...)          : void
IO.write (File         , ...)          : void
IO.write (URL          , ...)          : void                               
IO.write (URLConnection, ...)          : void
IO.write (OutputStream , ...)          : void      
IO.write (File         , ...)          : void                
IO.write (File         , ...)          : void

IO.getText (CharSequence)              : String
IO.getText (File)                      : String
IO.getText (InputStream)               : String
IO.getText (URI)                       : String
IO.getText (URL)                       : String
IO.getText (URLConnection)             : String
IO.getText (BufferedReader)            : String
```    

```java
// mkdir
IO.mkdir (CharSequence)                : boolean
IO.mkdir (File)                        : boolean

// mkdirs                        
IO.mkdirs(CharSequence)                : File
IO.mkdirs(File)                        : File

IO.copy (CharSequence, CharSequence)   : void                            
IO.copy (File, File)                   : void         
...                                      

IO.rename (CharSequence, CharSequence) : void                    
IO.rename (File, CharSequence)         : void
...                              

IO.move (File, File)                   : void      
IO.move (String, String)               : void      
... 

IO.clean (CharSequence)                : File 
IO.clean (File)                        : File

IO.remove (CharSequence)               : void
IO.remove (File)                       : void

// ... and more ...                                        
```

```java
// Create directory if not already exists, true for clean if exists
IO.mkdirs ("/path/to/some/dir", true);                     
IO.write  ("/path/to/some/file", "Writes this text to file. Becomes the content in the file.")
IO.append ("/path/to/some/file", " We add this to the file.")  

assert IO.getText("/path/to/some/file").equals("Writes this text to file. Becomes the content in the file. We add this to the file.")
```

```java
IO.Iterate.File.each, eachRecurse, find, findRecurse ...                
IO.Iterate.Zip .each, ...                
IO.Iterate.Jar .each, ... 
IO.Iterate.Url .each, ...                

StandardServiceRegistryBuilder registry = new StandardServiceRegistryBuilder().applySettings(properties);
MetadataSources         metadataSources = new MetadataSources(registry.build());

// Iterate a java package on the classpath, could be a file, a jar, a zip ... 
IO.Iterate.Url.each(getClass().getClassLoader().getResource(packege), entry -> {         
    String klassName = entry.getRelativeIterationPath();
    String klass     = (packege + "/" + klassName).replaceAll("/", ".").replaceAll(".class", "");

    Class<?> klass   = Class.forName(klass);
                                                                             
    // Add the entity to Hibernate JPA entities using MetaDataSources
    metadataSources.addAnnotatedClass(klass);
});               

IO.Iterate.File.eachRecurse(dir, file -> {
    if ( !file.isDirectory() && file.getName().endsWith(".less" ) ) {

        String text = IO.getText(file);
    }
});

IO.Iterate.File.eachRecurse(dir, file -> {
    if (!file.isDirectory()) {
        String name = file.getName();
        String text = IO.getText(file);
        
        StringBuilder sb = new StringBuilder();
        if (should.call(name)) {
            pattern.replace(sb, text, include, true);
        }
        
        if ( Is.Ok(sb)) {
            IO.write(file, sb.toString());
        }
    }
});
```

#### [Is.java](src/momomo/com/Is.java) samples

`Is` is class containing various boolean related **answers** for various questions.  

```java
Object              obj   = null;
Boolean             bool  = null;
String[]            array = {};
ArrayList<String>   list  = new ArrayList<>();
Map<String, String> map   = new HashMap<>();       
```

```java
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
```

```java
Is.Equal(...)
Is.Primitive(...)                          
Is.Double(...)
Is.Array(...)
Is.Symlink(...)
...
```    
```java
Is.NotNull(...)
Is.Null(...)
```
    
```java   
Is.True(...)
Is.False(...)
Is.Or(...)
Is.Empty(...)
Is.Between(...)
Is.In(...)
Is.In.Jar(...)
```
    
```java
Is.Linux(...)
Is.Window(...)
Is.Mac(...)
```
    
```java
Is.Development(...)
Is.Test(...)
Is.Production(...)
...
```                                           

#### [$Maps.java](src/momomo/com/$Maps.java) samples

```java                                                                            
$Maps.map()
$Maps.map(K, V, K, V, ...)
$Maps.merge(Map<K, V>, K, V, K, V, ...)
$Maps.put(...)
$Maps.copy(...)
$Maps.clone(...)                 
```

```java
import static momomo.com.$Maps.*;

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

////////////////////////////////////////////////
//////            We print              ///////
//////////////////////////////////////////////

System.out.println("Europe: " + europe.size());
System.out.println("Africa: " + africa.size());
System.out.println("World : " + world.size());

for (Map.Entry<String, String> entry : world.entrySet()) {
    System.out.println("Capital in country " + entry.getKey() + " is " + entry.getValue() );
}

Europe: 5
Africa: 2
World : 7
Capital in country Sweden is Stockholm
Capital in country Morocco is Rabat
Capital in country Norway is Oslo
Capital in country Egypt is Cairo
Capital in country Poland is Warsaw
Capital in country France is Paris
Capital in country Germany is Berlin
```                                                            

#### [Yarn.java](src/momomo/com/Yarn.java) samples

It is named `Yarn` to resemble and make the connectoin with the word `String`, hence it is for String related operations, a particular one purpose kind.
```java
// Can you figure out what this code does? 
                
String code = Yarn.$.create("""
  if ( map instanceof ${variable} ) { 
      return ((${variable}) map).clone();  
  } 
  """, "variable", map); 

// Yarn.$ is already defined of course! But we can also setup something else: 
SH = new Yarn("#{", "}");                         
String line = SH.create("Hello my name is #{name}!", "name", "Joseph");
```                                                               

#### [Strings.java](src/momomo/com/Strings.java) samples
```java
Strings.eachLine            (CharSequence, Lambda.V1E<String, E>)    : void
Strings.eachLine            (Scanner     , Lambda.V1E<String, E>)    : void
...                                
                                   
Strings.eachChar            (CharSequence, Lambda.V1E<Character, E>) : void         
...                                
                                   
Strings.join                (CharSequence, ...)                      : String       
...                                
                                   
Strings.quote               (CharSequence)                           : String       
...                                
                                   
Strings.toCharArray         (CharSequence)                           : char[]       
Strings.toBytes             (CharSequence)                           : byte[]
...                                
                                   
Strings.occurrences         (CharSequence, ...)                      : int
Strings.unchar              (CharSequence, ...)                      : String
Strings.unslash             (CharSequence, ...)                      : String       
                                   
Strings.multiply            (CharSequence, int)                      : String       
                                   
Strings.model               (Object[])                               : String       
Strings.clear               (StringBuilder)                          : StringBuilder
Strings.append              (StringBuilder, Scanner)                 : StringBuilder                      

Strings.Replace.first       (CharSequence, ...)                      : String
Strings.Replace.first       (CharSequence, ...)                      : String
Strings.Replace.all         (CharSequence, ...)                      : String
Strings.Replace.allNewLines (String, String)                         : String

Strings.Prepend.with        (CharSequence, CharSequence)             : String
Strings.Prepend.linenumber  (CharSequence, )                         : String
...         

```

#### [$Lists.java](src/momomo/com/$Lists.java) samples
```java
Coming soon. Please refer to the source code for now.
```

#### [$Arrays.java](src/momomo/com/$Arrays.java) samples
```java
Coming soon. Please refer to the source code for now. 
```                                                  

#### [$Collections.java](src/momomo/com/$Collections.java) samples
```java
Coming soon. Please refer to the source code for now. 
```

#### [$Environment.java](src/momomo/com/$Environment.java) samples
```java
Coming soon. Please refer to the source code for now. 
```                                                  
#### [$Array.java](src/momomo/com/collections/$Array.java) samples
```java
Coming soon. Please refer to the source code for now. 
```

#### [$ConcurrentHashSet.java](src/momomo/com/collections/$ConcurrentHashSet.java) samples
```java
Coming soon. Please refer to the source code for now. 
```

#### [$ConcurrentIndexedLinkedHashSet.java](src/momomo/com/collections/$ConcurrentIndexedLinkedHashSet.java) samples
```java
Coming soon. Please refer to the source code for now. 
```

#### [$ConcurrentList.java](src/momomo/com/collections/$ConcurrentList.java) samples
```java
Coming soon. Please refer to the source code for now. 
```

#### [$ConcurrentRotatingQueue.java](src/momomo/com/collections/$ConcurrentRotatingQueue.java) samples
```java
Coming soon. Please refer to the source code for now. 
```

#### [$List.java](src/momomo/com/collections/$List.java) samples
```java
Coming soon. Please refer to the source code for now. 
```

#### [$Map.java](src/momomo/com/collections/$List.java) samples
```java
Coming soon. Please refer to the source code for now. 
```

#### [$MapQuick.java](src/momomo/com/collections/$MapQuick.java) samples
```java
Coming soon. Please refer to the source code for now. 
```

#### [$Set.java](src/momomo/com/collections/$Set.java) samples
```java
Coming soon. Please refer to the source code for now. 
```

#### [Hashcodes.java](src/momomo/com/Hashcodes.java) samples
```java
Coming soon. Please refer to the source code for now. 
```

#### [Runtimes.java](src/momomo/com/Runtimes.java) samples
```java
Coming soon. Please refer to the source code for now. 
```

#### [Nano.java](src/momomo/com/Nano.java) samples
```java
Coming soon. Please refer to the source code for now. 
```

#### [Nanotime.java](src/momomo/com/sources/Nanotime.java) samples
```java
Coming soon. Please refer to the source code for now. 
```

#### [Zip.java](src/momomo/com/sources/Zip.java) samples
```java
Coming soon. Please refer to the source code for now. 
```

#### [Call.java](src/momomo/com/Call.java) samples
```java
Coming soon. Please refer to the source code for now. 
```

#### [MovingAverageConverging.java](src/momomo/com/sources/MovingAverageConverging.java) samples
```java
Coming soon. Please refer to the source code for now. 
```

#### [MovingAverageCumulative.java](src/momomo/com/sources/MovingAverageCumulative.java) samples
```java
Coming soon. Please refer to the source code for now. 
```

#### [Sysprop.java](src/momomo/com/sources/Sysprop.java) samples
```java
Coming soon. Please refer to the source code for now. 
```


#### [Gson.java](src/momomo/com/Gson.java) samples
```java
Coming soon. Please refer to the source code for now. 
```