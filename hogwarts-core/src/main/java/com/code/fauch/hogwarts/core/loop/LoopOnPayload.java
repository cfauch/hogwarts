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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.nio.ByteBuffer;
import java.util.NoSuchElementException;
import java.util.Objects;

import com.code.fauch.hogwarts.core.model.Payload;

/**
 * Loop on a predefined payload.
 * If on of a parameter value change, the encoded payload is changed accordingly.
 * 
 * @author c.fauch
 *
 */
public final class LoopOnPayload implements ILoop {
    
    /**
     * The payload.
     */
    private final Payload<?> payload; 
    
    /**
     * The total number of repeats.
     */
    private final int repeat;
    
    /**
     * Iterator implementation when payload encoded content should be recomputed
     * each time.
     * 
     * @author c.fauch
     *
     */
    public final class LoopComputedPayloadIterator implements ILoopIterator {

        /**
         * the current total number of loop on payload.
         */
        private int count;

        /**
         * Constructor.
         */
        private LoopComputedPayloadIterator() {
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
            if (this.count != -1) {
                this.count--;
            }
            return ByteBuffer.wrap(payload.getBytes());
        }

        @Override
        public void close() {
            // Nothing to do
        }
        
    }
    
    /**
     * Iterator implementation when payload encoded is recomputed
     * on property change event.
     * 
     * @author c.fauch
     *
     */
    public final class LoopPayloadIterator implements ILoopIterator, PropertyChangeListener {

        /**
         * the current total number of loop on file.
         */
        private int count;
        
        /**
         * The currently encoded data.
         */
        private byte[] data;

        /**
         * Constructor.
         */
        private LoopPayloadIterator() {
            this.count = repeat;
            payload.addPropertyChangeListener(this);
            updateData();
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
            if (this.count != -1) {
                this.count--;
            }
            return ByteBuffer.wrap(getData());
        }

        @Override
        public void close() {
            payload.removePropertyChangeListener(this);
        }
        
        /**
         * Return the current data to return.
         * @return the current data
         */
        private synchronized byte[] getData() {
            return this.data;
        }
        
        /**
         * updates the data to be return.
         */
        private synchronized void updateData() {
            this.data = payload.getBytes();
        }

        @Override
        public void propertyChange(final PropertyChangeEvent evt) {
            updateData();
        }
        
    }
    
    /**
     * Constructor
     * 
     * @param payload the payload (not null)
     * @param repeat the number of times to read file (or -1 for infinite loop)
     */
    public LoopOnPayload(final Payload<?> payload, final int repeat) {
        this.repeat = repeat;
        this.payload = Objects.requireNonNull(payload, "payload is mandatory");
    }

    @Override
    public ILoopIterator iterator() {
        if (this.payload.isComputed()) {
            return new LoopComputedPayloadIterator();
        } else {
            return new LoopPayloadIterator();
        }
    }
    
}
