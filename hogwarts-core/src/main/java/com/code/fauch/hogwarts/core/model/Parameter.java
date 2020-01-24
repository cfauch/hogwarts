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
import java.beans.PropertyChangeSupport;
import java.util.Objects;
import java.util.UUID;

/**
 * Definition of a parameter.
 * The value of a parameter may change along its life.
 * It is possible for client code to subscribe to parameter changes
 * 
 * @author c.fauch
 *
 */
public final class Parameter <T> implements IContent {

    /**
     * Name of the event raised when value change.
     */
    public static final String VALUE = "parameter-value";
    
    /**
     * Unique identifier of this parameter.
     */
    private final UUID id;

    /**
     * The label of this parameter.
     */
    private final String label;
    
    /**
     * The type of this parameter.
     */
    private final IType<T> type;
    
    /**
     * The current value.
     */
    private T value;
    
    /**
     * Indicates whether the value can change or not
     */
    private final boolean isReadOnly;
    
    /**
     * To manage listeners.
     */
    private final PropertyChangeSupport changeSupport;
    
    /**
     * Constructor.
     * 
     * @param id the unique identifier of the parameter (may be null)
     * @param label the label of the parameter (not null)
     * @param type the type of the parameter (not null)
     * @param value the value of the parameter
     * @param isReadOnly true for constant parameter
     */
    protected Parameter(final UUID id, final String label, final IType<T> type, T value, final boolean isReadOnly) {
        this.id = id;
        this.label = Objects.requireNonNull(label, "label is missing");
        this.type = Objects.requireNonNull(type, "type is missing");
        this.value = value;
        this.isReadOnly = isReadOnly;
        this.changeSupport = new PropertyChangeSupport(this);
    }
    
    /**
     * Creates a new parameter.
     * 
     * @param <U>
     * @param id the unique identifier of the parameter (may be null)
     * @param label the label of the parameter (not null)
     * @param type the type of the parameter (not null)
     * @param value the value of the parameter
     * @return the new parameter
     */
    public static <U> Parameter<U> newParameter(final UUID id, final String label, final IType<U> type, 
            final U value) {
        return new Parameter<U>(id, label, type, value, false);
    }
    
    /**
     * Creates a new constant.
     * 
     * @param <U>
     * @param id the unique identifier of the parameter (may be null)
     * @param label the label of the constant parameter (not null)
     * @param type the type of the constant parameter (not null)
     * @param value the value of the constant parameter
     * @return the new constant parameter
     */
    public static <U> Parameter<U> newConstant(final UUID id, final String label, final IType<U> type, 
            final U value) {
        return new Parameter<U>(id, label, type, value, true);
    }
    
    /**
     * There is no fixed size for parameter available.
     * The expected size depend on the type of the parameter
     */
    @Override
    public Integer getSize() {
        return null;
    }

    /**
     * The unique identifier of this parameter.
     * 
     * @return the id (may be null)
     */
    public UUID getId() {
        return id;
    }

    /**
     * The label of this parameter
     * 
     * @return the label
     */
    public final String getLabel() {
        return label;
    }

    /**
     * The type of thus parameter.
     * 
     * @return the type
     */
    public final IType<T> getType() {
        return type;
    }

    /**
     * Return true only if it's a constant so that the value cannot change.
     * 
     * @return the isReadOnly
     */
    public final boolean isReadOnly() {
        return isReadOnly;
    }
    
    /**
     * Returns the current value of this parameter
     * 
     * @return the value
     */
    public final synchronized T getValue() {
        return this.value;
    }

    /**
     * Set a new value to this parameter.
     * 
     * @param value the new value
     */
    public final void setValue(final T value) {
        if (!this.isReadOnly) {
            synchronized (this) {
                this.value = value;
            }
            fireEvent(VALUE, value);
        }
    }
    
    /**
     * Encode this parameter into a sequence of bytes.
     * 
     * @return the encoded parameter
     */
    public byte[] getBytes() {
        return this.type.encode(this.value);
    }
    
    /**
     * Register a listener to listen changes on this parameter
     */
    @Override
    public final void addPropertyChangeListener(PropertyChangeListener listener) {
        this.changeSupport.addPropertyChangeListener(listener);
        
    }

    /**
     * Unregister a listener.
     */
    @Override
    public final void removePropertyChangeListener(PropertyChangeListener listener) {
        this.changeSupport.removePropertyChangeListener(listener);
    }
    
    /**
     * Reports parameter change to listeners.
     * 
     * @param event the event name
     * @param newValue the new value
     */
    protected final void fireEvent(final String event, final Object newValue) {
        this.changeSupport.firePropertyChange(event, null, newValue);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((label == null) ? 0 : label.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Parameter<?> other = (Parameter<?>) obj;
        if (label == null) {
            if (other.label != null)
                return false;
        } else if (!label.equals(other.label))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Parameter [id=" + id + ", label=" + label + ", type=" + type + ", value=" + value + "]";
    }
    
}
