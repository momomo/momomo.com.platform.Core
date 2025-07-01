<!---
-->

##### Makes out the core of several of our public releases, and is packed with useful everyday `Java` utility that at least serves our needs very well.

##### Dependencies 
* **[`momomo.com.platform.Lambda`](https://github.com/momomo/momomo.com.platform.Lambda)** 
* **[`momomo.com.platform.Obj`](https://github.com/momomo/momomo.com.platform.Obj)** 
* &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**[`org.projectlombok:lombok:1.18.10`](https://search.maven.org/artifact/org.projectlombok/lombok/1.18.10/jar)** 
* &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**[`com.google:com.google.code.gson:2.8.6`](https://search.maven.org/artifact/com.google.code.gson/gson/2.8.6/jar)** 
* &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**[`com.opencsv:opencsv:5.2`](https://mvnrepository.com/artifact/com.opencsv/opencsv/5.2)**

##### Maven dependencies available on maven central [search.maven.org](https://search.maven.org/search?q=com.momomo)
##### Dependency

```xml

<dependency>
    <groupId>com.momomo</groupId>
    <artifactId>m.platform.Core.100</artifactId>
    <version>5.0.2</version>
</dependency>                                                      
```                         
##### Repository
```xml
<repository>
    <id>maven-central</id>
    <url>http://repo1.maven.org/maven2</url>
</repository>
```

##### Our other, highlighted [repositories](https://github.com/momomo?tab=repositories)

* **[`momomo.com.platform.Lambda`](https://github.com/momomo/momomo.com.platform.Lambda)** Contains a bunch of `functional interfaces` similar to `Runnable`, `Supplier`, `Function`, `BiFunction`, `Consumer` `...` and so forth all packed in a easily accessed and understood intuitive pattern that are used plenty in our libraries. **`Lambda.V1E`**, **`Lambda.V2E`**, **`Lambda.R1E`**, **`Lambda.R2E`**, ...

* **[`momomo.com.platform.Obj`](https://github.com/momomo/momomo.com.platform.Obj)** Intuitive library that makes it easier for you to return multiple, fully defined objects on the fly from any method, any time rather than being limited to the default maximum of one. 

* **[`momomo.com.platform.Nanotime`](https://github.com/momomo/momomo.com.platform.Nanotime)** Allows for nanosecond time resolution when asking for time from Java Runtime in contrast with **`System.currentTimeMillis()`**.

* **[`momomo.com.platform.DB.transactional.Hibernate`](https://github.com/momomo/momomo.com.platform.DB.transactional.Hibernate)** A library to execute database commands in transactions without  having to use annotations based on Hibernate libraries. No Spring!

* **[`momomo.com.platform.DB.transactional.Spring`](https://github.com/momomo/momomo.com.platform.DB.transactional.Spring)** A library to execute database commands in transactions without  having to use annotations based on Spring libraries.

#### Background
Most of the code in this module is in because it is the least common denominator for other releases we've made or are about to make. 
So some classes, like **`Reflects.java`**, **`Time.java`**, **`Regexes.java`** currently *only contains* a subset of functionality otherwise found in our **other**, still private **`Base`** library. 
With time more and more methods and classes will be added to this module, as we make our code *releasable*.     
 
#### [Exception handling](src/momomo/com/exceptions)

Our IO related operations, and most of our other API's, normally would throw a bunch of different checked `exceptions` for various things, **`IOException`**, **`ClassNotFoundException`** and so forth.  
Instead we usually transform a checked `exception` into to a `runtime` `exception` equivalent.

###### For instance  
For a thrown **`IOException`** we will **`throw new $IOException(original)`**.   
For a thrown **`URISyntaxException`** we will **`throw new $URISyntaxException(original)`**.   
For all other not specially tailored exception handling we will simply **`throw new $RuntimeException(original)`**.    
We usually do this automagically using our **`Ex.runtime(exception)`** method(s) to also avoid wrapping an `Exception` twice.

#### [Is.java](src/momomo/com/Is.java) examples

**`Is`** is class containing various boolean related **answers** for various questions. Method names are consistently uppercase to allow us to 
retain intuitive names such as **`Is.True`** or **`Is.In()`** as **`Is.true()`** or **`Is.in`** would not be possible to use.   

```java
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
[config](..%2F..%2F..%2F..%2Fprojects%2Fbq.mmm.lib.Core%2F.git%2Fconfig)
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

#### [IO.java](src/momomo/com/IO.java) examples

```java     
// Some eachLine signatures (plenty more)                                                                  
IO.eachLine (CharSequence, Lambda.R2E<Boolean, String, Integer, E>) throws E         
IO.eachLine (File        , Lambda.R2E<Boolean, String, Integer, E>) throws E            
IO.eachLine (InputStream , Lambda.R2E<Boolean, String, Integer, E>) throws E  
IO.eachLine (URL         , Lambda.R2E<Boolean, String, Integer, E>) throws E
...  

// Iterate each line in file and operate on eachLine 
IO.eachLine(file, (line, number) -> {
    int i      = line.indexOf("=");
    String key = line.substring(0, i), val = line.substring(i + 1); 

    System.out.println( "On line number " + number " we found " + val + "=" + key );
});
```

```java
// write
IO.write (CharSequence , ...) : void
IO.write (File         , ...) : void
IO.write (URL          , ...) : void                               
IO.write (URLConnection, ...) : void
IO.write (OutputStream , ...) : void      
IO.write (File         , ...) : void                
IO.write (File         , ...) : void
```

```java                                                  
// read
IO.text (CharSequence)        : String
IO.text (File)                : String
IO.text (URI)                 : String
IO.text (URL)                 : String
IO.text (URLConnection)       : String
IO.text (BufferedReader)      : String
IO.text (InputStream)         : String
```    

```java
// read
IO.bytes (CharSequence)       : byte[]
IO.bytes (File)               : byte[]
IO.bytes (URL)                : byte[]
IO.bytes (URLConnection)      : byte[]
IO.bytes (InputStream)        : byte[]
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

assert IO.text("/path/to/some/file").equals("Writes this text to file. Becomes the content in the file. We add this to the file.")
```

```java                                                            
// Iterate nested directories, urls, zip and/or jar files 
IO.Iterate.File.each, eachRecurse, find, findRecurse ...                
IO.Iterate.Url .each, ...                
IO.Iterate.Zip .each, ...                
IO.Iterate.Jar .each, ... 
```

```java
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
```

```java           
IO.Iterate.File.eachRecurse(dir, file -> {
    if ( !file.isDirectory() && file.getName().endsWith(".less" ) ) {
        String text = IO.text(file);
    }
});    
```

```java
IO.Iterate.File.eachRecurse(dir, file -> {
    if ( !Is.Directory(file) ) {
        String name = file.getName();
        String text = IO.text(file);
        
        StringBuilder sb = new StringBuilder();
        ... 

        if ( Is.Ok(sb) ) {
            IO.write(file, sb);
        }
    }
});
```                                   

#### [$Maps.java](src/momomo/com/$Maps.java) examples

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

Map<String, String> europe = map(
    "Sweden" , "Stockholm",
    "France" , "Paris",
    "Germany", "Berlin"
);

merge(europe, "Poland", "Warsaw");
merge(europe, "Norway", "Oslo", "Switzerland", "Zurich");

Map<String, String> africa = map(
    "Morocco", "Rabat",
    "Egypt"  , "Cairo"
);

// Copy europe, then merge africa onto the copy
Map<String, String> world = merge(copy(europe), africa);

////////////////////////////////////////////////
//////            We print              ///////
//////////////////////////////////////////////

System.out.println("Countries in Europe: " + europe.size());
System.out.println("Countries in Africa: " + africa.size());
System.out.println("Countries in World : " + world.size());

for (Map.Entry<String, String> entry : world.entrySet()) {
    System.out.println("Capital of " + entry.getKey() + " is " + entry.getValue() );
}
```

```java
Countries in Europe: 6
Countries in Africa: 2
Countries in World : 8
Capital of Sweden is Stockholm
Capital of Morocco is Rabat
Capital of Norway is Oslo
Capital of Egypt is Cairo
Capital of Poland is Warsaw
Capital of Switzerland is Zurich
Capital of France is Paris
Capital of Germany is Berlin
```                                                            

#### [Yarn.java](src/momomo/com/Yarn.java) examples

It is named **`Yarn`** to remind us of the word **`String`**, since it is for **`String`** related operations, with a very specific purpose.
```java
String code = Yarn.$("""            
  Hi ${name}! 
  
  We send you this email today because we at ${site} miss you!   
  
  Please come back to see us again soon! 
""", "name", "Peter", "site", "momomo.com"); 
```

**`Yarn.$`** is already defined for us of course! But we can also setup something else, like for a hash **`#{}`**:

```java
SH = new Yarn("#{", "}"); 
String line = SH.create("Hello, my name is #{name}!", "name", "James");
```                                                               

#### [Strings.java](src/momomo/com/Strings.java) examples
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

#### [$Lists.java](src/momomo/com/$Lists.java) examples
```java
Documentation coming soon. Please refer to the source code for now.
```

#### [$Arrays.java](src/momomo/com/$Arrays.java) examples
```java
Documentation coming soon. Please refer to the source code for now. 
```                                                  

#### [$Collections.java](src/momomo/com/$Collections.java) examples
```java
Documentation coming soon. Please refer to the source code for now. 
```

#### [$Environment.java](src/momomo/com/$Environment.java) examples
```java
Documentation coming soon. Please refer to the source code for now. 
```                                                  
#### [$Array.java](src/momomo/com/collections/$Array.java) examples
```java
Documentation coming soon. Please refer to the source code for now. 
```

#### [$ConcurrentHashSet.java](src/momomo/com/collections/$ConcurrentHashSet.java) examples
```java
Documentation coming soon. Please refer to the source code for now. 
```

#### [$ConcurrentIndexedLinkedHashSet.java](src/momomo/com/collections/$ConcurrentIndexedLinkedHashSet.java) examples
```java
Documentation coming soon. Please refer to the source code for now. 
```

#### [$ConcurrentList.java](src/momomo/com/collections/$ConcurrentList.java) examples
```java
Documentation coming soon. Please refer to the source code for now. 
```

#### [$ConcurrentRotatingQueue.java](src/momomo/com/collections/$ConcurrentRotatingQueue.java) examples
```java
Documentation coming soon. Please refer to the source code for now. 
```

#### [$List.java](src/momomo/com/collections/$List.java) examples
```java
Documentation coming soon. Please refer to the source code for now. 
```

#### [$Map.java](src/momomo/com/collections/$List.java) examples
```java
Documentation coming soon. Please refer to the source code for now. 
```

#### [$MapQuick.java](src/momomo/com/collections/$MapQuick.java) examples
```java
Documentation coming soon. Please refer to the source code for now. 
```

#### [$Set.java](src/momomo/com/collections/$Set.java) examples
```java
Documentation coming soon. Please refer to the source code for now. 
```

#### [Hashcodes.java](src/momomo/com/Hashcodes.java) examples
```java
Documentation coming soon. Please refer to the source code for now. 
```

#### [Runtimes.java](src/momomo/com/Runtimes.java) examples
```java
Documentation coming soon. Please refer to the source code for now. 
```

#### [Nano.java](src/momomo/com/Nano.java) examples
```java
Documentation coming soon. Please refer to the source code for now. 
```

#### [Nanotime.java](src/momomo/com/sources/Nanotime.java) examples
```java
Documentation coming soon. Please refer to the source code for now. 
```

#### [Zip.java](src/momomo/com/sources/Zip.java) examples
```java
Documentation coming soon. Please refer to the source code for now. 
```

#### [Call.java](src/momomo/com/Call.java) examples
```java
Documentation coming soon. Please refer to the source code for now. 
```

#### [MovingAverageConverging.java](src/momomo/com/sources/MovingAverageConverging.java) examples
```java
Documentation coming soon. Please refer to the source code for now. 
```

#### [MovingAverageCumulative.java](src/momomo/com/sources/MovingAverageCumulative.java) examples
```java
Documentation coming soon. Please refer to the source code for now. 
```

#### [Sysprop.java](src/momomo/com/sources/Sysprop.java) examples
```java
Documentation coming soon. Please refer to the source code for now. 
```


#### [Gson.java](src/momomo/com/Gson.java) examples
```java
Documentation coming soon. Please refer to the source code for now. 
```           

### License
The full license can be found here [`MoL9`](https://raw.githubusercontent.com/momomo/momomo.com.yz.licenses/master/MoL9?raw=true?raw=true)

### Contribute
Send an email to `opensource{at}momomo.com` if you would like to contribute in any way, make changes or otherwise have thoughts and/or ideas on things to improve.
