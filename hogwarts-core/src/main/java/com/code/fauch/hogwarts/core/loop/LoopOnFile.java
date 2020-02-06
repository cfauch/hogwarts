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

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Loop on file and split it on chunks of fixed size.
 * It cannot implements Iterable because the iterator should be auto-closable.
 * 
 * @author c.fauch
 *
 */
public final class LoopOnFile implements ILoop {
    
    /**
     * Path of the file to read.
     */
    private final Path file;

    /**
     * The start position in the file.
     */
    private final int offset;
    
    /**
     * Packet size.
     */
    private final int size;
    
    /**
     * The total number of repeats.
     */
    private final int repeat;
    
    /**
     * Iterator implementation auto-closable to be able to close file stream.
     * 
     * @author c.fauch
     *
     */
    public final class LoopFileIterator implements ILoopIterator {
    
        /**
         * The current position in the file.
         */
        private int position;
        
        /**
         * The input stream
         */
        private InputStream in;
        
        /**
         * The chunk buffer.
         */
        private final byte[] buffer;
        
        /**
         * The current cursor on chunk buffer
         */
        private int cursor;
        
        /**
         * the current total number of loop on file.
         */
        private int count;
        
        /**
         * Constructor.
         */
        private LoopFileIterator() {
            this.position = offset;
            this.in = null;
            this.buffer = new byte[size];
            this.cursor = 0;
            this.count = repeat;
        }
        
        @Override
        public boolean hasNext() {
            return !Thread.currentThread().isInterrupted() && (this.count == -1 || this.count > 0);
        }
    
        @Override
        public ByteBuffer next() {
            if (!hasNext()) {
                throw new NoSuchElementException("loop stopped.");
            }
            try {
                while(!Thread.currentThread().isInterrupted()) {
                    if (this.in == null) {
                        this.in = Files.newInputStream(file);
                        this.in.skip(this.position);
                    }
                    final int remaining = size - this.cursor;
                    final int read = this.in.read(this.buffer, this.cursor, remaining);
                    if (read != -1) {
                        this.position += read;
                        if (read == remaining) {
                            this.cursor = 0;
                            return ByteBuffer.wrap(Arrays.copyOf(buffer, buffer.length));
                        } else {
                            this.cursor += read;
                        }
                    } else {
                        this.in.close();
                        this.in = null;
                        if (this.position == 0) {
                            throw new IllegalArgumentException("File was empty.");
                        }
                        this.position = 0;
                        if (this.count != -1) {
                            this.count--;
                        }
                    }
                }
                this.count = 0;
                throw new NoSuchElementException("loop stopped.");
            } catch (IOException e) {
                throw new NoSuchElementException(e.getMessage());
            }
        }
        
        @Override
        public void close() {
            if (this.in != null) {
                try {
                    this.in.close();
                } catch (IOException e1) {
                }
                this.in = null;
            }
        }
    
    }
    
    /**
     * Constructor.
     * 
     * @param file the path of the file (not null)
     * @param offset the start position on file.
     * @param size the size of each chunk (in bytes >0)
     * @param repeat the number of times to read file (or -1 for infinite loop)
     */
    public LoopOnFile(final Path file, final int offset, final int size, final int repeat) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be > 0");
        }
        this.file = Objects.requireNonNull(file, "file is missing");
        this.offset = offset;
        this.size = size;
        this.repeat = repeat;
    }

    /**
     * Constructor.
     * 
     * @param file the path of the file (not null)
     * @param size the size of each chunk (in bytes >0)
     * @param repeat the number of times to read file (or -1 for infinite loop)
     */
    public LoopOnFile(final Path file, final int size, final int repeat) {
        this(file, 0, size, repeat);
    }
    
    /**
     * Return an auto-closable iterator.
     * @return
     */
    @Override
    public LoopFileIterator iterator() {
        return new LoopFileIterator();
    }
    
}
