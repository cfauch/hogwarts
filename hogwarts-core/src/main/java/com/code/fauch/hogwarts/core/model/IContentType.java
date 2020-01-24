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
 * Describes a content-type.
 * Is is used to encode the content of a payload.
 * 
 * @author c.fauch
 *
 */
public interface IContentType<T> {

    /**
     * Encode the given content into sequence of bytes
     * 
     * @param content the content to encode (may be null)
     * @return the encoded content or null if content is null
     */
    byte[] encode(T content);
    
    /**
     * Returns the name of this content type like expected in text.
     * 
     * @return the name (not null)
     */
    String getName();
    
}
