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

import java.io.ByteArrayOutputStream;

import org.junit.Assert;
import org.junit.Test;

import com.code.fauch.hogwarts.core.loop.ILoop.ILoopIterator;
import com.code.fauch.hogwarts.core.model.Parameter;
import com.code.fauch.hogwarts.core.model.Payload;
import com.code.fauch.hogwarts.core.model.StdContentType;
import com.code.fauch.hogwarts.core.model.StdType;

/**
 * @author c.fauch
 *
 */
public class LoopOnPayloadTest {

    @Test
    public void testLoopOnPayloadCstX3() throws Exception {
        final Payload<?> payload = Payload.newComputedPayload(
                StdContentType.PARAMETER, 
                Parameter.newConstant(null, "", StdType.STRING, "hello !")
        );
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try(ILoopIterator iter = new LoopOnPayload(payload, 3).iterator()) {
            while (iter.hasNext()) {
                out.write(iter.next().array()); 
            }
        }
        Assert.assertEquals("hello !hello !hello !", new String(out.toByteArray()));
    }

    @Test
    public void testLoopOnPayloadVariableX3() throws Exception {
        final Parameter<String> variable = Parameter.newParameter(null, "", StdType.STRING, "hello !");
        final Payload<?> payload = Payload.newPayload(
                StdContentType.PARAMETER, 
                variable
        );
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try(ILoopIterator iter = new LoopOnPayload(payload, 3).iterator()) {
            while (iter.hasNext()) {
                out.write(iter.next().array());
                variable.setValue("goodbye !");
            }
        }
        Assert.assertEquals("hello !goodbye !goodbye !", new String(out.toByteArray()));
    }

    @Test
    public void testLoopOnPayloadInfinite() throws Exception {
        final Parameter<String> variable = Parameter.newParameter(null, "", StdType.STRING, "hello !");
        final Payload<?> payload = Payload.newPayload(
                StdContentType.PARAMETER, 
                variable
        );
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try(ILoopIterator iter = new LoopOnPayload(payload, -1).iterator()) {
            while (iter.hasNext()) {
                out.write(iter.next().array());
                variable.setValue("goodbye !");
                if (out.size() == 16) {
                    break;
                }
            }
        }
        Assert.assertEquals("hello !goodbye !", new String(out.toByteArray()));
    }

    @Test(expected = NullPointerException.class)
    public void testLoopOnNullPayload() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try(ILoopIterator iter = new LoopOnPayload(null, 3).iterator()) {
            while (iter.hasNext()) {
                out.write(iter.next().array()); 
            }
        }
        Assert.assertEquals("hello !hello !hello !", new String(out.toByteArray()));
    }
    
    @Test
    public void testLoopOnPayloadCstX0() throws Exception {
        final Payload<?> payload = Payload.newComputedPayload(
                StdContentType.PARAMETER, 
                Parameter.newConstant(null, "", StdType.STRING, "hello !")
        );
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try(ILoopIterator iter = new LoopOnPayload(payload, 0).iterator()) {
            while (iter.hasNext()) {
                out.write(iter.next().array()); 
            }
        }
        Assert.assertEquals("", new String(out.toByteArray()));
    }
    
    @Test
    public void testLoopOnPayloadCstXneg() throws Exception {
        final Payload<?> payload = Payload.newComputedPayload(
                StdContentType.PARAMETER, 
                Parameter.newConstant(null, "", StdType.STRING, "hello !")
        );
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try(ILoopIterator iter = new LoopOnPayload(payload, -2).iterator()) {
            while (iter.hasNext()) {
                out.write(iter.next().array()); 
            }
        }
        Assert.assertEquals("", new String(out.toByteArray()));
    }
    
}
