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
package com.code.fauch.hogwarts.core.model;

import java.beans.PropertyChangeListener;
import java.util.Objects;

/**
 * Definition of a payload.
 * It is possible for client code to subscribe on changes of the content of the payload.
 * 
 * @author c.fauch
 *
 */
public final class Payload <T extends IContent> {
    
    /**
     * The content type (not null).
     * Used to encode the content of this payload.
     */
    private final IContentType<T> contentType;
    
    /**
     * The content (may be null)
     */
    private T content;
    
    /**
     * Determines whether the content of the payload should be recomputed
     * each time it is encoded, or only on change events
     */
    private final boolean isComputed;
    
    /**
     * Constructor.
     * 
     * @param contentType the content type (not null)
     * @param content the content (not null)
     * @param isComputed true means the content is recomputed each time it is encoded
     */
    private Payload(final IContentType<T> contentType, final T content, final boolean isComputed) {
        this.contentType = Objects.requireNonNull(contentType, "contentType is missing");
        this.content = Objects.requireNonNull(content, "content is missing");
        this.isComputed = isComputed;
    }
    
    /**
     * Creates a new payload.
     * The content of the payload is recomputed only on change events.
     * 
     * @param <U>
     * @param contentType the content type (not null)
     * @param content the content (not null)
     * @return the new payload
     */
    public static <U extends IContent> Payload<U> newPayload(final IContentType<U> contentType, final U content) {
        return new Payload<>(contentType, content, false);
    }
    
    /**
     * Creates a new computed payload.
     * The content of the payload is recompted each time it is encoded. 
     * 
     * @param <U>
     * @param contentType the content type (not null)
     * @param content the content
     * @return the new payload
     */
    public static <U extends IContent> Payload<U> newComputedPayload(final IContentType<U> contentType, final U content) {
        return new Payload<>(contentType, content, true);
    }
    
    /**
     * Returns whether this payload is recomputed each times or not.
     * 
     * @return is computed
     */
    public boolean isComputed() {
        return this.isComputed;
    }
    
    /**
     * Encode the content of this payload into a sequence of bytes.
     * 
     * @return the encoded content.
     */
    public byte[] getBytes() {
        return this.contentType.encode(this.content);
    }
    
    /**
     * Register a listener to listen changes on payload content.
     * 
     * @param listener the listener to register (not null)
     */
    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        this.content.addPropertyChangeListener(Objects.requireNonNull(listener, "listener is mandatory"));
    }
    
    /**
     * Unregister a listener on the payload content.
     * 
     * @param listener the listener to remove
     */
    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        this.content.removePropertyChangeListener(listener);
    }
    
}
