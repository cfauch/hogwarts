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

import static org.junit.Assert.*;

import java.nio.ByteBuffer;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author c.fauch
 *
 */
public class PayloadTest {

    @Test
    public void testGetBytes() {
        final Parameter<Integer> p1 = Parameter.newParameter(null, "l1", StdType.INT, 42);
        final Parameter<Integer> p2 = Parameter.newParameter(null, "l2", StdType.INT, 421); 
        final Sequence seq = new Sequence(null, Arrays.asList(p1, p2));
        final byte[] buff = Payload.newComputedPayload(StdContentType.SEQUENCE, seq).getBytes();
        final ByteBuffer bb = ByteBuffer.wrap(buff);
        Assert.assertEquals(42, bb.getInt());
        Assert.assertEquals(421, bb.getInt());
    }

    @Test(expected=NullPointerException.class)
    public void testGetBytesNullContent() {
        Payload.newComputedPayload(StdContentType.SEQUENCE, null).getBytes();
    }
    
    @Test
    public void testGetBytesEmptySequence() {
        final Sequence seq = new Sequence(null, Arrays.asList());
        final byte[] buff = Payload.newComputedPayload(StdContentType.SEQUENCE, seq).getBytes();
        Assert.assertEquals(0, buff.length);
    }

    @Test
    public void testGetBytesSequenceOfOneNullParameter() {
        final Sequence seq = new Sequence(null, Arrays.asList((Parameter<?>)null));
        final byte[] buff = Payload.newComputedPayload(StdContentType.SEQUENCE, seq).getBytes();
        Assert.assertEquals(0, buff.length);
    }

    @Test
    public void testContentChange() {
        final String[] labels = new String[]{""};
        final Parameter<Integer> p1 = Parameter.newParameter(null, "l1", StdType.INT, 42);
        final Parameter<Integer> p2 = Parameter.newParameter(null, "l2", StdType.INT, 421); 
        final Sequence seq = new Sequence(null, Arrays.asList(p1, p2));
        final Payload<Sequence> payload = Payload.newComputedPayload(StdContentType.SEQUENCE, seq);
        payload.addPropertyChangeListener(e -> labels[0] = ((Parameter<?>) e.getSource()).getLabel());
        p1.setValue(365);
        Assert.assertEquals("l1", labels[0]);
    }
}
