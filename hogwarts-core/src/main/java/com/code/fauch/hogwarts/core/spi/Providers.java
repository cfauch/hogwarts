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
package com.code.fauch.hogwarts.core.spi;

import java.util.ServiceLoader;

import com.code.fauch.hogwarts.core.model.IContentType;
import com.code.fauch.hogwarts.core.model.IType;

/**
 * Utility class to make easier the research of types and content-types providers using SPI.
 * 
 * @author c.fauch
 *
 */
public final class Providers {

    /**
     * The service loader to use to research providers of types.
     */
    private static final ServiceLoader<ITypeProvider> TYPES = ServiceLoader.load(ITypeProvider.class);

    /**
     * The service loader to use to research providers of content-types.
     */
    private static final ServiceLoader<IContentTypeProvider> CONTENT_TYPES = ServiceLoader.load(IContentTypeProvider.class);

    /**
     * No constructor
     */
    private Providers() {
        // Nothing to do
    }
    
    /**
     * Research a type corresponding to a given name.
     * 
     * @param name the name of the type to research
     * @return the corresponding type or null if not found
     */
    public static IType<?> getTypeInstance(final String name) {
        for (ITypeProvider provider : TYPES) {
            final IType<?> type = provider.getInstance(name);
            if (type != null) {
                return type;
            }
        }
        return null;
    }
 
    /**
     * Research a content-type corresponding to a given name.
     * 
     * @param name the name of the content-type to research
     * @return the corresponding content-type or null if not found
     */
    public static IContentType<?> getContentTypeInstance(final String name) {
        for (IContentTypeProvider provider : CONTENT_TYPES) {
            final IContentType<?> type = provider.getInstance(name);
            if (type != null) {
                return type;
            }
        }
        return null;
    }
    
}
