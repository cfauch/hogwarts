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

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

import org.junit.Assert;
import org.junit.Test;

import com.code.fauch.hogwarts.core.model.clock.FixedClock;
import com.code.fauch.hogwarts.core.model.clock.OffsetClock;

/**
 * @author c.fauch
 *
 */
public class StdTypeTest {

    @Test
    public void testDate() {
        final byte[] buff = StdType.DATE.encode(LocalDate.of(2020, 01, 24));
        final ByteBuffer bb = ByteBuffer.wrap(buff);
        Assert.assertEquals(20, bb.getInt());
        Assert.assertEquals(1, bb.getInt());
        Assert.assertEquals(24, bb.getInt());
    }

    @Test
    public void testDateWithNull() {
        final byte[] buff = StdType.DATE.encode(null);
        final ByteBuffer bb = ByteBuffer.wrap(buff);
        Assert.assertEquals(0, bb.getInt());
        Assert.assertEquals(0, bb.getInt());
        Assert.assertEquals(0, bb.getInt());
    }

    @Test
    public void testTime() {
        final byte[] buff = StdType.TIME.encode(LocalTime.of(9, 25, 33));
        final ByteBuffer bb = ByteBuffer.wrap(buff);
        Assert.assertEquals(9, bb.getInt());
        Assert.assertEquals(25, bb.getInt());
        Assert.assertEquals(33, bb.getInt());
    }
    
    @Test
    public void testTimeWithNull() {
        final byte[] buff = StdType.TIME.encode(null);
        final ByteBuffer bb = ByteBuffer.wrap(buff);
        Assert.assertEquals(0, bb.getInt());
        Assert.assertEquals(0, bb.getInt());
        Assert.assertEquals(0, bb.getInt());
    }
    
    @Test
    public void testOffsetClock() {
        final byte[] buff = StdType.OFFSET_CLOCK.encode(new OffsetClock(Duration.ofHours(-1)));
        final ByteBuffer bb = ByteBuffer.wrap(buff);
        final LocalDateTime currentSystemTime = LocalDateTime.now(ZoneOffset.UTC);
        Assert.assertEquals(currentSystemTime.getYear() - 2000, bb.getInt());
        Assert.assertEquals(currentSystemTime.getMonthValue(), bb.getInt());
        Assert.assertEquals(currentSystemTime.getDayOfMonth(), bb.getInt());
        Assert.assertEquals(currentSystemTime.getHour() - 1, bb.getInt());
        Assert.assertEquals(currentSystemTime.getMinute(), bb.getInt());
        Assert.assertEquals(currentSystemTime.getSecond(), bb.getInt());
    }
    
    @Test
    public void testOffsetClockWithNull() {
        final byte[] buff = StdType.OFFSET_CLOCK.encode(null);
        final ByteBuffer bb = ByteBuffer.wrap(buff);
        Assert.assertEquals(0, bb.getInt());
        Assert.assertEquals(0, bb.getInt());
        Assert.assertEquals(0, bb.getInt());
        Assert.assertEquals(0, bb.getInt());
        Assert.assertEquals(0, bb.getInt());
        Assert.assertEquals(0, bb.getInt());
    }
    
    @Test
    public void testFixedClock() {
        final byte[] buff = StdType.FIXED_CLOCK.encode(
                new FixedClock(
                        ZoneOffset.UTC, 
                        Instant.parse("2020-01-24T07:41:03.00Z")
                )
        );
        final ByteBuffer bb = ByteBuffer.wrap(buff);
        Assert.assertEquals(2020 - 2000, bb.getInt());
        Assert.assertEquals(1, bb.getInt());
        Assert.assertEquals(24, bb.getInt());
        Assert.assertEquals(7, bb.getInt());
        Assert.assertEquals(41, bb.getInt());
        Assert.assertEquals(3, bb.getInt());
    }
    
    @Test
    public void testFixedClockWithNull() {
        final byte[] buff = StdType.FIXED_CLOCK.encode(null);
        final ByteBuffer bb = ByteBuffer.wrap(buff);
        Assert.assertEquals(0, bb.getInt());
        Assert.assertEquals(0, bb.getInt());
        Assert.assertEquals(0, bb.getInt());
        Assert.assertEquals(0, bb.getInt());
        Assert.assertEquals(0, bb.getInt());
        Assert.assertEquals(0, bb.getInt());
    }
    
    @Test
    public void testString() throws UnsupportedEncodingException {
        final byte[] buff = StdType.STRING.encode("épilogue");
        Assert.assertEquals("épilogue", new String(buff, StandardCharsets.UTF_8));
    }
    
    @Test
    public void testStringWithNull() throws UnsupportedEncodingException {
        final byte[] buff = StdType.STRING.encode(null);
        Assert.assertEquals("", new String(buff, StandardCharsets.UTF_8));
    }
    
    @Test
    public void testShort() throws UnsupportedEncodingException {
        final byte[] buff = StdType.SHORT.encode((short) 15150);
        final ByteBuffer bb = ByteBuffer.wrap(buff);
        Assert.assertEquals(15150, bb.getShort());
    }

    @Test
    public void testShortWithNull() throws UnsupportedEncodingException {
        final byte[] buff = StdType.SHORT.encode(null);
        final ByteBuffer bb = ByteBuffer.wrap(buff);
        Assert.assertEquals(0, bb.getShort());
    }

    @Test
    public void testInt() throws UnsupportedEncodingException {
        final byte[] buff = StdType.INT.encode(2147483646);
        final ByteBuffer bb = ByteBuffer.wrap(buff);
        Assert.assertEquals(2147483646, bb.getInt());
    }
    
    @Test
    public void testIntWithNull() throws UnsupportedEncodingException {
        final byte[] buff = StdType.INT.encode(null);
        final ByteBuffer bb = ByteBuffer.wrap(buff);
        Assert.assertEquals(0, bb.getInt());
    }
    
    @Test
    public void testLong() throws UnsupportedEncodingException {
        final byte[] buff = StdType.LONG.encode(9223372036854775806L);
        final ByteBuffer bb = ByteBuffer.wrap(buff);
        Assert.assertEquals(9223372036854775806L, bb.getLong());
    }

    @Test
    public void testLongWithNull() throws UnsupportedEncodingException {
        final byte[] buff = StdType.LONG.encode(null);
        final ByteBuffer bb = ByteBuffer.wrap(buff);
        Assert.assertEquals(0L, bb.getLong());
    }

    @Test
    public void testFloat() throws UnsupportedEncodingException {
        final byte[] buff = StdType.FLOAT.encode(3.14159274f);
        final ByteBuffer bb = ByteBuffer.wrap(buff);
        Assert.assertEquals(3.14159274f, bb.getFloat(),0);
    }

    @Test
    public void testFloatWithNull() throws UnsupportedEncodingException {
        final byte[] buff = StdType.FLOAT.encode(null);
        final ByteBuffer bb = ByteBuffer.wrap(buff);
        Assert.assertEquals(0f, bb.getFloat(),0);
    }

    @Test
    public void testDouble() throws UnsupportedEncodingException {
        final byte[] buff = StdType.DOUBLE.encode(387850941.35821074);
        final ByteBuffer bb = ByteBuffer.wrap(buff);
        Assert.assertEquals(387850941.35821074, bb.getDouble(),0);
    }
    
    @Test
    public void testDoubleWithNull() throws UnsupportedEncodingException {
        final byte[] buff = StdType.DOUBLE.encode(null);
        final ByteBuffer bb = ByteBuffer.wrap(buff);
        Assert.assertEquals(0, bb.getDouble(),0);
    }
    
    @Test
    public void testUnsignedInt() throws UnsupportedEncodingException {
        final byte[] buff = StdType.UNSIGNED_INT.encode(4294967294L);
        final ByteBuffer bb = ByteBuffer.wrap(buff);
        final long l = Integer.toUnsignedLong(bb.getInt());
        Assert.assertEquals(4294967294L, l);
    }

    @Test
    public void testUnsignedIntWithNull() throws UnsupportedEncodingException {
        final byte[] buff = StdType.UNSIGNED_INT.encode(null);
        final ByteBuffer bb = ByteBuffer.wrap(buff);
        final long l = Integer.toUnsignedLong(bb.getInt());
        Assert.assertEquals(0L, l);
    }

    @Test
    public void testUnsignedLong() throws UnsupportedEncodingException {
        final byte[] buff = StdType.UNSIGNED_LONG.encode("18446744073709551615");
        final ByteBuffer bb = ByteBuffer.wrap(buff);
        final String s = Long.toUnsignedString(bb.getLong());
        Assert.assertEquals("18446744073709551615", s);
    }

    @Test
    public void testUnsignedLongWithNull() throws UnsupportedEncodingException {
        final byte[] buff = StdType.UNSIGNED_LONG.encode(null);
        final ByteBuffer bb = ByteBuffer.wrap(buff);
        final String s = Long.toUnsignedString(bb.getLong());
        Assert.assertEquals("0", s);
    }

}
