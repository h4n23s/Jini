import eu.hgweb.jini.Ini;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * Copyright 2020 Hannes Gehrold
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
public class IniTests {

    @Test
    public void testSections() throws IOException {

        for (int i = 0; i < 2; i++) {

            boolean handleQuotes = i > 0;

            final Map<String, Map<String, String>> sections = getDefaultTestMap();

            Ini ini = new Ini(generateIniStream(sections, handleQuotes), handleQuotes);
            ini.sections().forEach(section -> assertTrue(sections.containsKey(section.name())));

        }
    }

    @Test
    public void testSectionExists() throws IOException {

        for (int i = 0; i < 2; i++) {

            boolean handleQuotes = i > 0;

            Map<String, Map<String, String>> sections = getDefaultTestMap();

            Ini ini = new Ini(generateIniStream(sections, handleQuotes), handleQuotes);
            assertTrue(ini.sectionExists(sections.keySet().toArray(new String[0])[0]));

        }
    }

    @Test
    public void testSection() throws IOException {

        for (int i = 0; i < 2; i++) {

            boolean handleQuotes = i > 0;

            Map<String, Map<String, String>> sections = getDefaultTestMap();

            Ini ini = new Ini(generateIniStream(sections, handleQuotes), handleQuotes);

            for(String section : sections.keySet()) {

                for(Map<String, String> valuePairs : sections.values()) {

                    assertEquals(ini.section(section).name(), ini.section(section).toString());

                    for(String key : valuePairs.keySet()) {

                        assertTrue(ini.section(section).keys().contains(key));
                        assertTrue(ini.section(section).values().contains(valuePairs.get(key)));
                        assertTrue(ini.section(section).keyExists(key));
                        assertTrue(ini.section(section).valueExists(valuePairs.get(key)));

                    }

                    for(String key : valuePairs.keySet()) {

                        assertEquals(valuePairs.get(key), ini.section(section).value(key));
                    }
                }

                assertEquals(ini.section(section).value("nonExistentKey", "defaultValue"), "defaultValue");

            }
        }
    }

    private Map<String, Map<String, String>> getDefaultTestMap() {

        Map<String, Map<String, String>> sections = new HashMap<>();

        HashMap<String, String> section = new HashMap<>();
        section.put("test1", "test");
        section.put("test2", "qwertyuiopasdfghjklzxcvbnm0123456789,;.-?\\#+*~'<>|!$§%&/()=^°");
        section.put("test3", "qwertyuiopasdfghjklzxcvbnm0123456789,;.-?\\#+*~'<>|!$§%&/()=^°".repeat(100));

        sections.put("section1", section);
        sections.put("section2", section);
        sections.put("section3", section);

        return sections;

    }

    private InputStream generateIniStream(Map<String, Map<String, String>> sections, boolean addQuotes) {

        StringBuilder ini = new StringBuilder();

        for(String section : sections.keySet()) {

            Map<String, String> valuePairs = sections.get(section);

            ini.append("[").append(section).append("]\n");

            for(String key : valuePairs.keySet()) {

                String randomSpace = " ".repeat((int) (Math.random() * 50));

                if(addQuotes) {

                    ini.append(randomSpace).append(key).append("=").append(randomSpace)
                            .append("\"").append(valuePairs.get(key))
                            .append("\"").append(randomSpace);
                } else {

                    ini.append(randomSpace).append(key).append(randomSpace).append("=").append(randomSpace)
                            .append(valuePairs.get(key))
                            .append(randomSpace);
                }

                ini.append("\n");

            }

            ini.append("\n");

        }

        return new ByteArrayInputStream(ini.toString().getBytes());

    }

}
