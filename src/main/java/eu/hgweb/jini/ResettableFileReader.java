package eu.hgweb.jini;

import java.io.*;
import java.nio.charset.Charset;

/*
 * Copyright 2021 Hannes Gehrold
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
public class ResettableFileReader extends Reader {

    private final RandomAccessFile randomAccessFile;
    private final Charset charset;

    public ResettableFileReader(File file, Charset charset) throws FileNotFoundException {
        this.randomAccessFile = new RandomAccessFile(file, "r");
        this.charset = charset;
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        byte[] bytes = new byte[len];

        int bytesRead = randomAccessFile.read(bytes, 0, len);
        if(bytesRead < 0) {
            return bytesRead;
        }

        char[] chars = new String(bytes, 0, bytesRead, charset).toCharArray();
        System.arraycopy(chars, 0, cbuf, off, chars.length);

        return chars.length;
    }

    @Override
    public void reset() throws IOException {
        randomAccessFile.seek(0);
    }

    @Override
    public void close() throws IOException {
        randomAccessFile.close();
    }
}
