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

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;


/**
 * @author c.fauch
 *
 */
public class SequenceTest {

    @Test
    public void testNewParameterCopy() {
        final ArrayList<Parameter<?>> params = new ArrayList<Parameter<?>>();
        params.add(Parameter.newParameter(null, "l1", StdType.INT, 42));
        final Sequence seq = new Sequence(4, params);
        params.add(Parameter.newParameter(null, "l2", StdType.INT, 421));
        final ArrayList<Parameter<?>> seqParams = new ArrayList<Parameter<?>>();
        for (Parameter<?> p : seq) {
            seqParams.add(p);
        }
        Assert.assertEquals(params.subList(0, 1), seqParams);
    }

    @Test
    public void testParameterChanges() {
        final String[] labels = new String[]{""};
        final Parameter<Integer> p1 = Parameter.newParameter(null, "l1", StdType.INT, 42);
        final Parameter<Integer> p2 = Parameter.newParameter(null, "l2", StdType.INT, 421); 
        final ArrayList<Parameter<?>> params = new ArrayList<Parameter<?>>();
        params.add(p1);
        params.add(p2);
        final Sequence seq = new Sequence(4, params);
        seq.addPropertyChangeListener(e -> labels[0] = ((Parameter<?>) e.getSource()).getLabel());
        Assert.assertEquals("", labels[0]);
        p2.setValue(365);
        Assert.assertEquals("l2", labels[0]);
    }

}
