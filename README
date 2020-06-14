## Jini

**Jini** (short for _``Java ini``_) is a simple and lightweight configuration file parser (``.ini``) compatible with Java 7 and higher.

[![UML diagram](assets/uml-medium.png)](assets/uml-full.png)

#### Examples

```
public static void main(String[] args) throws IOException {

    Ini ini = new Ini(new File("test.ini"), true);

    System.out.println(ini); // In case you want to verify the parsed data.

    for(Section section : ini.sections()) {

        System.out.println("Section: " + section);

        for(String key : section.keys()) {

            System.out.println(key + " = " + section.value(key));
        }
    }

    Section section1 = ini.section("section1");
    String value1 = section1.value("key1");

}
```