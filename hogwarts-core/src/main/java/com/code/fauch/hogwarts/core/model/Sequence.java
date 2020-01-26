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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Definition of a sequence of parameters.
 * It is possible for client code to subscribe on changes of all parameters.
 * 
 * @author c.fauch
 *
 */
public final class Sequence implements Iterable<Parameter<?>>, IContent {
    
    /**
     * The expected size of the encoded sequence (in bytes).
     * Null means auto-adjust
     */
    private final Integer size;
    
    /**
     * The list of parameters of the sequence.
     */
    private final ArrayList<Parameter<?>> parameters;
    
    /**
     * Constructor.
     * 
     * @param size the expected size in bytes of the encoded content (may be null).
     */
    public Sequence(final Integer size) {
        this.size = size;
        this.parameters = new ArrayList<Parameter<?>>();
    }
    
    /**
     * Constructor.
     * 
     * @param size the expected size in bytes of the encoded content (may be null).
     * @param parameters the ordered list of parameters (to copy, not null may be empty)
     */
    public Sequence(final Integer size, final List<Parameter<?>> parameters) {
        this(size);
        this.parameters.addAll(Objects.requireNonNull(parameters, "parameters is mandatory"));
    }
    
    /**
     * The expected size.
     * Null means auto-adjust
     */
    @Override
    public Integer getSize() {
        return this.size;
    }

    /**
     * Register the listener to listen all parameters of the sequence
     */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        for (Parameter<?> p : this.parameters) {
            p.addPropertyChangeListener(listener);
        }
    }

    /**
     * Unregister the listener from all parameters of the sequence
     */
    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        for (Parameter<?> p : this.parameters) {
            p.removePropertyChangeListener(listener);
        }
    }

    @Override
    public Iterator<Parameter<?>> iterator() {
        return this.parameters.iterator();
    }
    
}
