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
/**
 * <p>
 * The main API for payload and parameters.
 * </p>
 * <p>
 * The goal is to provide a customizable java bean ({@link com.code.fauch.hogwarts.core.model.Payload}) 
 * that can be encoded into a sequence of bytes.
 * </p>
 * <h4>example: payload of one string parameter</h4>
 * <pre>
 * public void testEncodePayloadOfStringParameter() {
 *      final Parameter<String> param = Parameter.newParameter("param", StdType.STRING, "totoro");
 *      Payload<Parameter<?>> payload = Payload.newPayload(StdContentType.PARAMETER, param);
 *      Assert.assertEquals("totoro", new String(payload.getBytes()));
 *      param.setValue("hello world !");
 *      Assert.assertEquals("hello world !", new String(payload.getBytes()));
 * }
 * </pre>
 * <h4>example: payload of one sequence of two parameters</h4>
 * <pre>
 * public void testEncodePayloadOfSequence() {
 *      final Parameter<String> param1 = Parameter.newParameter("param1", StdType.STRING, "hello ");
 *      final Parameter<String> param2 = Parameter.newParameter("param2", StdType.STRING, "totoro !");
 *      Payload<Sequence> payload = Payload.newPayload(
 *              StdContentType.SEQUENCE, 
 *              new Sequence(null, Arrays.asList(param1, param2))
 *      );
 *      Assert.assertEquals("hello totoro !", new String(payload.getBytes()));
 *      param2.setValue("titi !");
 *      Assert.assertEquals("hello titi !", new String(payload.getBytes()));
 * }
 * </pre>
 * <h3>Payload</h3>
 * <p>
 * A Payload is made of:
 * <ul>
 * <li>A content-type ({@link com.code.fauch.hogwarts.core.model.IContentType})</li>
 * <li>A content ({@link com.code.fauch.hogwarts.core.model.IContent})</li>
 * </ul>
 * </p>
 * <h3>Content and content-type</h3>
 * <p>
 * Only two standard content-types are implemented in its module. But it can be extended with new ones easily. 
 * All standard content-types are defined in {@link com.code.fauch.hogwarts.core.model.StdContentType}:
 * <ul>
 * <li>PARAMETER: Allows {@link com.code.fauch.hogwarts.core.model.Parameter} as content.</li>
 * <li>SEQUENCE: Allows {@link com.code.fauch.hogwarts.core.model.Sequence} as content.</li>
 * </ul>
 * </p>
 * <h3>Parameter</h3>
 * <p>
 * A parameter is made of:
 * <ul>
 * <li>A label: the name of the label</li>
 * <li>A type ({@link com.code.fauch.hogwarts.core.model.IType})
 * <li>A value: the current value of the parameter</li>
 * </ul>
 * </p>
 * <p>
 * Only standard types are implemented in this module. But it can be extended with new ones easily.
 * All standard types are defined in {@link com.code.fauch.hogwarts.core.model.StdType}:
 * <ul>
 * <li>STRING: Allows {@link java.lang.String} value encoded with the UTF-8 charset.</li>
 * </ul>
 * </p>
 * <p>
 * To build a new parameter call <code>Parameter.newParameter</code> with the expected type, 
 * label, and value of the parameter.
 * </p>
 * <pre>
 * final Parameter<String> param = Parameter.newParameter("", StdType.STRING, "Hello World !");
 * </pre>
 * 
 * @author c.fauch
 *
 */
package com.code.fauch.hogwarts.core.model;