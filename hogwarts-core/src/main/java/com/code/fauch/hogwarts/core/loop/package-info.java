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
 * This package provides some {@link java.util.Iterator} to loop on bytes.
 * </p>
 * 
 * <h3> Loop on file </h3>
 * <p>
 * The goal of the {@link com.code.fauch.hogwarts.core.loop.LoopOnFile} is to provide an iterator 
 * to loop over a binary file by splitting it into small chunks.
 * </p>
 * <h4> Finite loop </h4>
 * <p>
 * To iterate over a file a fixed number of times, call 
 * <code>LoopOnFile(final Path file, final int size, final int repeat)</code> with number of 
 * repeats into <code>repeat</code> parameter and the size in bytes of chunks into 
 * <code>size</code> parameter.
 * </p>
 * Here is an example to loop over a file 2 times. Each call to <code>next()</code>
 * returns a chunk of 200 bytes.
 * <pre>
 *      ByteArrayOutputStream out = new ByteArrayOutputStream();
 *      try(LoopFileIterator iter = new LoopOnFile(file, 200, 2).iterator()) {
 *          while (iter.hasNext()) {
 *              out.write(iter.next().array()); 
 *          }
 *      }
 * </pre>
 * <p>
 * If the size of the file in bytes is not divisible by the given chunk size, the file is reopen a last time
 * to complete the last chunk with first bytes of the file. As consequences, the file may be open 
 * <code>repeat+1 </code> times.
 * </p>
 * <pre>
 *          (1st)                      (2nd)                     (3rd)
 * +------------------------+ +------------------------+ +------------------------+
 * |                        | |       chunk3           | |       chunk5           |
 * |        chunk1          | |                        | +------------------------+
 * |                        | +------------------------+ |                        |
 * +------------------------+ |                        | |                        |
 * |                        | |       chunk4           | |                        |
 * |        chunk2          | |                        | |                        |
 * |                        | +------------------------+ |                        |
 * +------------------------+ |                        | |                        |
 * |        chunk3          | |       chunk5           | |                        |
 * +------------------------+ +------------------------+
 * </pre>
 * <h4> Infinite loop </h4>
 * <p>
 * For infinite loop, call <code>LoopOnFile(final Path file, final int size, final int repeat)</code>
 * with <code>-1</code> in <code>repeat</code> parameter. It will iterate over and over unless 
 * the thread is interrupted.  
 * </p>
 * <pre>
 *      ByteArrayOutputStream out = new ByteArrayOutputStream();
 *      try(LoopFileIterator iter = new LoopOnFile(file, 200, -1).iterator()) {
 *          while (iter.hasNext()) {
 *              out.write(iter.next().array());
 *              if (out.size() == (int)expectedSize) {
 *                  break;
 *              }
 *          }
 *      }
 * </pre>
 * <h3> Loop on payload </h3>
 * <p>
 * The goal of the {@link com.code.fauch.hogwarts.core.loop.LoopOnPayload} is to provide an iterator 
 * to loop over a payload. Each call to <code>next()</code> will return the sequence of bytes corresponding
 * to the encoded payload content.
 * </p>
 * <h4> Finite loop </h4>
 * <p>
 * To iterate a fixed number of times over a payload, use the constructor 
 * <code>LoopOnPayload(final Payload<?> payload, final int repeat)</code> with the expected number of repeats
 * into <code>repeat</code> parameter.
 * </p>
 * <p>
 * Here is an example to loop 3 times over a payload made of one String parameter:
 * </p>
 * <pre>
 *      final Parameter<String> variable = Parameter.newParameter("", StdType.STRING, "hello !");
 *      final Payload<?> payload = Payload.newComputedPayload(
 *              StdContentType.PARAMETER, 
 *              variable
 *      );
 *      ByteArrayOutputStream out = new ByteArrayOutputStream();
 *      try(ILoopIterator iter = new LoopOnPayload(payload, 3).iterator()) {
 *          while (iter.hasNext()) {
 *              out.write(iter.next().array());
 *              variable.setValue("world !");
 *          }
 *      }
 *      Assert.assertEquals("hello !world !world !", new String(out.toByteArray()));
 * </pre>
 * <p>
 * Has you can see, the content of the payload may change and then the corresponding encoded
 * sequence of bytes is changed accordingly.
 * </p>
 * <h4> Infinite loop </h4>
 * <p>
 * For infinite loop, call <code>LoopOnPayload(final Payload<?> payload, final int repeat)</code>
 * with <code>-1</code> in <code>repeat</code> parameter. It will iterate over and over unless 
 * the thread is interrupted.  
 * </p>
 * <pre>
 *      final Parameter<String> variable = Parameter.newParameter("", StdType.STRING, "hello !");
 *      final Payload<?> payload = Payload.newPayload(
 *              StdContentType.PARAMETER, 
 *              variable
 *      );
 *      ByteArrayOutputStream out = new ByteArrayOutputStream();
 *      try(ILoopIterator iter = new LoopOnPayload(payload, -1).iterator()) {
 *          while (iter.hasNext()) {
 *              out.write(iter.next().array());
 *              variable.setValue("goodbye !");
 *              if (out.size() == 16) {
 *                  break;
 *              }
 *          }
 *      }
 *      Assert.assertEquals("hello !goodbye !", new String(out.toByteArray()));
 * </pre>
 * <p>
 * Has you can see, we use in this two last examples, two types of payload.
 * <ul>
 * <li>With <code>Payload.newPayload<code> the payload content is encoded only when the content changed</li>
 * <li>With <code>Payload.newComputedPayload<code> the payload content is encoded at the beginning of each iteration</li>
 * </ul>
 * </p>
 * 
 * @author c.fauch
 *
 */
package com.code.fauch.hogwarts.core.loop;