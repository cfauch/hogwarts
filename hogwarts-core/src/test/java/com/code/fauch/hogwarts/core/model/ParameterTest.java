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

import java.nio.ByteBuffer;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author c.fauch
 *
 */
public class ParameterTest {

    @Test
    public void testGetBytes() {
        final Parameter<Short> param = Parameter.newParameter(null, "label", StdType.SHORT, null);
        param.setValue((short)42);
        final byte[] buff = param.getBytes();
        final ByteBuffer bb = ByteBuffer.wrap(buff);
        Assert.assertEquals(42, bb.getShort());
    }

    @Test
    public void testValueChanges() {
        int[] values = new int[] {58};
        final Parameter<Integer> param = Parameter.newParameter(null, "label", StdType.INT, 58);
        param.addPropertyChangeListener(e -> values[0] = (int) e.getNewValue());
        Assert.assertEquals(58, param.getValue().intValue());
        Assert.assertEquals(58, values[0]);
        param.setValue(42);
        Assert.assertEquals(42, param.getValue().intValue());
        Assert.assertEquals(42, values[0]);
    }
    
    @Test
    public void testConstantValueChanges() {
        int[] values = new int[] {58};
        final Parameter<Integer> param = Parameter.newConstant(null, "label", StdType.INT, 58);
        param.addPropertyChangeListener(e -> values[0] = (int) e.getNewValue());
        Assert.assertEquals(58, param.getValue().intValue());
        Assert.assertEquals(58, values[0]);
        param.setValue(42);
        Assert.assertEquals(58, param.getValue().intValue());
        Assert.assertEquals(58, values[0]);
    }

}
