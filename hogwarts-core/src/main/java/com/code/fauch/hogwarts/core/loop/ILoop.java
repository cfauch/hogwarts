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

import java.nio.ByteBuffer;
import java.util.Iterator;

/**
 * Interface describing the expected behavior of a loop on bytes.
 * A loop provides an iterator that returns ByteBuffer at each calls.
 * This iterator should be auto-closable
 * 
 * @author c.fauch
 *
 */
public interface ILoop {

    /**
     * Returns an iterator auto-closable that returns byte buffer.
     * 
     * @return an iterator that returns byte buffer
     */
    ILoopIterator iterator();
    
    /**
     * The iterator to return extends AutoClosable.
     * 
     * @author c.fauch
     *
     */
    public interface ILoopIterator extends Iterator<ByteBuffer>, AutoCloseable {}
    
}
