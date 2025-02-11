/* 
 * The MIT License
 *
 * Copyright 2015 Fanaen <contact@fanaen.fr>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package fr.fanaen.wordlist.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import fr.fanaen.wordlist.model.ReferenceStorage;

/**
 *
 * @author Fanaen <contact@fanaen.fr>
 */
public abstract class Parser {
        
    // -- Attributes --
    protected ReferenceStorage storage;
    
    // -- Constructors --
    public Parser() {
        
    }
    
    // -- Methods --
    
    public void processFile(String filePath) throws IOException {
        
        // Check if storage is ready --
        if(storage == null || !storage.isReady()) return;
        
        // Get some info converted --
        File file = new File(filePath);
        Charset charset = Charset.forName("UTF-8");
        
        // Read the file --
        // TODO : ensure we can read a resource here
        try(BufferedReader br = Files.newBufferedReader(file.toPath(), charset)) {
            int i = 0;

            for(String line; (line = br.readLine()) != null; ) {
                try {
                    processLine(line, i);
                }
                catch(Exception ex) {
                    System.err.println("Line "+ (i + 1) +": " + line);
                    throw ex;
                }

                i++;
            }
        }
        catch(Exception ex) {
            System.err.println("Error processing " + filePath);
            throw ex;
        }
    }

    protected abstract void processLine(String line, int lineNumber);
    
    // -- Getters & Setters --

    public ReferenceStorage getStorage() {
        return storage;
    }

    public void setStorage(ReferenceStorage storage) {
        this.storage = storage;
    }    
}
