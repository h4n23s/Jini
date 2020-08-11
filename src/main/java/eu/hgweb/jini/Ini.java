package eu.hgweb.jini;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
 */
public class Ini {

    private final Map<String, Section> configurations;

    /**
     * @see Ini (java.io.File, boolean)
     */
    public Ini(String pathname) throws IOException {

        this(new File(pathname));
    }

    /**
     * @see Ini (java.io.File, boolean)
     */
    public Ini(File file) throws IOException {

        this(file, false);
    }

    /**
     * Reads all sections and key-value pairs from a given file and stores them in a map.
     *
     * @param file The configuration file
     * @param handleQuotes Determines whether enclosing single and double quotation marks should be part of the parsed data.
     *                     If {@code true}, all enclosing quotation marks will be removed. However, if there are no quotation marks,
     *                     the data will still be processed correctly. If {@code false}, processing time will drastically decrease.
     * @throws IOException If an error occurs while reading the configuration file
     */
    public Ini(File file, boolean handleQuotes) throws IOException {

        configurations = new HashMap<>();

        String[] lines = read(file).split("(\\r\\n|\\r|\\n)");

        List<String> sections = new ArrayList<>();
        StringBuilder sectionContent = new StringBuilder();

        // Parse sections
        for(int linesRead = 0; linesRead < lines.length; linesRead++) {

            String line = lines[linesRead];

            if(sectionContent.length() != 0 && line.matches("^\\s*\\[.+?].*?$")) {

                sections.add(sectionContent.toString());
                sectionContent = new StringBuilder(line).append("\n");
                continue;

            }

            if(!line.matches("^\\s*#.*")) {

                sectionContent.append(line).append("\n");
            }

            if(linesRead == (lines.length - 1)) {

                sections.add(sectionContent.toString());
            }
        }

        String keyRegex = handleQuotes ? "^\\s*(.+?)\\s*=\\s*[\"|']?(.*?)[\"|']?\\s*$" : "^\\s*(.+?)\\s*=\\s*(.*?)\\s*$";

        // Parse keys and values
        for(String section : sections) {

            Map<String, String> keyValues = new HashMap<>();
            Matcher matcher = Pattern.compile(keyRegex, Pattern.MULTILINE).matcher(section);

            while (matcher.find()) {

                keyValues.put(matcher.group(1), matcher.group(2));
            }

            int startSection = section.indexOf("[");
            int endSection = section.indexOf("]");

            String sectionName = (startSection != -1) ? section.substring(startSection + 1, endSection) : "";
            configurations.put(sectionName, new Section(sectionName, keyValues));

        }

    }

    /**
     * Returns the key-value pairs of a given section.
     *
     * @param name The name of the section
     * @return A selection of key-value pairs
     */
    public Section section(String name) {

        return configurations.get(name);
    }

    public Collection<Section> sections() {

        return configurations.values();
    }

    private String read(File file) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        FileInputStream fileInputStream = new FileInputStream(file);

        byte[] buffer = new byte[1024];
        int read;

        while ((read = fileInputStream.read(buffer)) >= 0) {

            byteArrayOutputStream.write(buffer, 0, read);
        }

        return new String(byteArrayOutputStream.toByteArray());

    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        for(Section section : this.sections()) {

            stringBuilder.append("[").append(section).append("]\n");

            for(String key : section.keys()) {

                stringBuilder.append(key).append("=").append(section.value(key)).append("\n");
            }

            stringBuilder.append("\n");
        }

        return stringBuilder.toString();

    }

}