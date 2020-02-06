/*
 * Copyright 2019 Claire Fauch
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.code.fauch.hogwarts.core.loop;

import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

import com.code.fauch.hogwarts.core.loop.LoopOnFile.LoopFileIterator;

/**
 * @author c.fauch
 *
 */
public class LoopOnFileTest {

    @Test(expected = IllegalArgumentException.class)
    public void testLoopOnFile0Bytes() throws Exception {
        final Path file = Paths.get(getClass().getResource("/loop/test-file.data").toURI());
        final Path expected = Paths.get(getClass().getResource("/loop/test-file.data.X3").toURI());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try(LoopFileIterator iter = new LoopOnFile(file, 0, 3).iterator()) {
            while (iter.hasNext()) {
                out.write(iter.next().array()); 
            }
        }
        Assert.assertArrayEquals(Files.readAllBytes(expected), out.toByteArray());
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testLoopOnEmptyFile() throws Exception {
        final Path file = Paths.get(getClass().getResource("/loop/empty-file.data").toURI());
        final Path expected = Paths.get(getClass().getResource("/loop/test-file.data.X3").toURI());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try(LoopFileIterator iter = new LoopOnFile(file, 200, 3).iterator()) {
            while (iter.hasNext()) {
                out.write(iter.next().array()); 
            }
        }
        Assert.assertArrayEquals(Files.readAllBytes(expected), out.toByteArray());
    }
    
    @Test(expected=NullPointerException.class)
    public void testLoopOnNullFile() throws Exception {
        final Path expected = Paths.get(getClass().getResource("/loop/test-file.data.X3").toURI());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try(LoopFileIterator iter = new LoopOnFile(null, 200, 3).iterator()) {
            while (iter.hasNext()) {
                out.write(iter.next().array()); 
            }
        }
        Assert.assertArrayEquals(Files.readAllBytes(expected), out.toByteArray());
    }
    
    @Test
    public void testLoopOnFileX3() throws Exception {
        final Path file = Paths.get(getClass().getResource("/loop/test-file.data").toURI());
        final Path expected = Paths.get(getClass().getResource("/loop/test-file.data.X3").toURI());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try(LoopFileIterator iter = new LoopOnFile(file, 200, 3).iterator()) {
            while (iter.hasNext()) {
                out.write(iter.next().array()); 
            }
        }
        Assert.assertArrayEquals(Files.readAllBytes(expected), out.toByteArray());
    }

    @Test
    public void testLoopOnFileX0() throws Exception {
        final Path file = Paths.get(getClass().getResource("/loop/test-file.data").toURI());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try(LoopFileIterator iter = new LoopOnFile(file, 200, 0).iterator()) {
            while (iter.hasNext()) {
                out.write(iter.next().array()); 
            }
        }
        Assert.assertArrayEquals(new byte[0], out.toByteArray());
    }
    
    @Test
    public void testLoopOnFileXneg() throws Exception {
        final Path file = Paths.get(getClass().getResource("/loop/test-file.data").toURI());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try(LoopFileIterator iter = new LoopOnFile(file, 200, -2).iterator()) {
            while (iter.hasNext()) {
                out.write(iter.next().array()); 
            }
        }
        Assert.assertArrayEquals(new byte[0], out.toByteArray());
    }
    
    @Test
    public void testLoopOnFileInfinite() throws Exception {
        final Path file = Paths.get(getClass().getResource("/loop/test-file.data").toURI());
        final Path expected = Paths.get(getClass().getResource("/loop/test-file.data.X3").toURI());
        final long expectedSize = Files.size(expected);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try(LoopFileIterator iter = new LoopOnFile(file, 200, -1).iterator()) {
            while (iter.hasNext()) {
                out.write(iter.next().array());
                if (out.size() == (int)expectedSize) {
                    break;
                }
            }
        }
        Assert.assertArrayEquals(Files.readAllBytes(expected), out.toByteArray());
    }
    

}
