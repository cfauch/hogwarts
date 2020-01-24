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

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Objects;

import com.code.fauch.hogwarts.core.spi.IContentTypeProvider;

/**
 * Standard content types provider.
 * 
 * @author c.fauch
 *
 */
public final class StdContentType implements IContentTypeProvider {

    /**
     * A single parameter.
     */
    public static final IContentType<Parameter<?>> PARAMETER = new IContentType<Parameter<?>>() {

        @Override
        public byte[] encode(Parameter<?> content) {
            return Objects.requireNonNull(content, "content is mandatory").getBytes();
        }

        @Override
        public String getName() {
            return "PARAMETER";
        }
        
    };
    
    /**
     * A Sequence of parameters.
     */
    public static final IContentType<Sequence> SEQUENCE = new IContentType<Sequence>() {

        @Override
        public byte[] encode(Sequence content) {
            final ByteArrayOutputStream output = new ByteArrayOutputStream();
            for (Parameter<?> p : Objects.requireNonNull(content, "content is mandatory")) {
                output.writeBytes(p.getBytes());
            }
            return content.getSize() == null ? output.toByteArray() : Arrays.copyOf(output.toByteArray(), content.getSize());
        }

        @Override
        public String getName() {
            return "SEQUENCE";
        }
        
    };
    
    @Override
    public IContentType<? extends IContent> getInstance(String name) {
        if (name == null) {
            return null;
        }
        if (name.equals(PARAMETER.getName())) {
            return PARAMETER;
        }
        if (name.equals(SEQUENCE.getName())) {
            return SEQUENCE;
        }
        return null;
    }
    
}
