## Jini

![Minimum language level](https://img.shields.io/badge/java-7%2B-informational)
![Total code size](https://img.shields.io/github/languages/code-size/h4n23s/Jini)
![Latest release](https://img.shields.io/github/v/release/h4n23s/Jini)
![License](https://img.shields.io/github/license/h4n23s/Jini)

**Jini** (short for _``Java ini``_) is a simple, flexible and lightweight configuration file parser (``.ini``).

#### Usage

Add our dependency to your project:

<table>
  <tr>
    <th>Maven</th>
    <th>Gradle</th>
  </tr>
  <tr>
  <td>
    
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.h4n23s</groupId>
        <artifactId>Jini</artifactId>
        <version>1.0.3</version>
    </dependency>
</dependencies>
```
   
   </td>
   <td>
   
```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.h4n23s:Jini:1.0.3'
}
```
   
   </td>
  </tr>
</table>

##### Examples
```java
public static void main(String[] args) throws IOException {

    Ini ini = new Ini(new File("test.ini"), true);

    // This can be used to verify parsed data, but also to get a
    // formatted version of the original .ini file, as you can see down below.
    System.out.println(ini);

    for(Section section : ini.sections()) {

        System.out.println("Section name: " + section.name());

        for(String key : section.keys()) {
            System.out.println(key + " = " + section.value(key));
        }
    }

    if(ini.sectionExists("section1")) {

        Section section1 = ini.section("section1");

        if(section1.keyExists("key1")) {
            String value1 = section1.value("key1");
        }

        // You can also define a default value in case the specified key does not exist
        String value2 = section1.value("key2", "Default value");
        
    }
    
    // If there are no sections in a configuration file, you can still retrieve the keys
    Collection<String> ungroupedKeys = ini.section("").keys();

}
```

##### Formatting

Input:
```ini
[section1]
key1    = value1
key2    = value2

; This is a comment

[section2] ; This is another comment
key1=value1
key2=value2

[section3]
key1    = "value1"
key2="value2"
key3=   'value3'
# key4=value4
  # key5=value5
```

Formatted output using ``ini.toString()`` and when ``handleQuotes`` is true:
```ini
[section1]
key1=value1
key2=value2

[section2]
key1=value1
key2=value2

[section3]
key1=value1
key2=value2
key3=value3
```