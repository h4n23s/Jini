package eu.hgweb.jini;

import java.util.Collection;
import java.util.Map;

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
public class Section {

    private final String name;
    private final Map<String, String> valuePairs;

    protected Section(String name, Map<String, String> valuePairs) {

        this.name = name;
        this.valuePairs = valuePairs;

    }

    public String value(String key) {

        return valuePairs.get(key);
    }

    public Collection<String> values() {

        return valuePairs.values();
    }

    public String value(String key, String defaultValue) {

        return valuePairs.containsKey(key) ? valuePairs.get(key) : defaultValue;
    }

    public Collection<String> keys() {

        return valuePairs.keySet();
    }

    @Override
    public String toString() {

        return name;
    }

}