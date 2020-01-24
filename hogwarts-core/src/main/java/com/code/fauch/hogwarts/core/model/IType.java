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

/**
 * Describe a parameter type.
 * Is is used to encode the value of a parameter.
 * 
 * @author c.fauch
 *
 */
public interface IType<T> {

    /**
     * Returns the expected java class of the value (in java memory).
     *  
     * @return the value class
     */
    Class<T> getValueClass();
    
    /**
     * Encode a value into a sequence of bytes.
     * 
     * @param value the value to encode (may be null)
     * @return the encoded value or empty if the value is null
     */
    byte[] encode(T value);
    
    /**
     * Returns the name of this type like expected in text.
     * 
     * @return the name (not null)
     */
    String getName();
    
}
