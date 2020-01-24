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

/**
 * Describes the content of a payload.
 * 
 * @author c.fauch
 *
 */
public interface IContent {

    /**
     * The expected size of the encoded content.
     * 
     * @return the expected size in bytes
     */
    Integer getSize();
    
    /**
     * Register a listener to listen all changes on this content.
     * 
     * @param listener the listener to register (not null)
     */
    void addPropertyChangeListener(PropertyChangeListener listener);
    
    /**
     * Unregister a listener.
     * 
     * @param listener the listener to unregister
     */
    void removePropertyChangeListener(PropertyChangeListener listener);
    
}
