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
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import com.code.fauch.hogwarts.core.model.clock.FixedClock;
import com.code.fauch.hogwarts.core.model.clock.OffsetClock;
import com.code.fauch.hogwarts.core.spi.ITypeProvider;

/**
 * Standard types provider.
 * 
 * @author c.fauch
 *
 */
public final class StdType implements ITypeProvider {
    
    private static final String DATE_NAME = "DATE";
    private static final String TIME_NAME = "TIME";
    private static final String OFFSET_CLOCK_NAME = "OFFSET_CLOCK";
    private static final String FIXED_CLOCK_NAME = "FIXED_CLOCK";
    private static final String SHORT_NAME = "SHORT";
    private static final String STRING_NAME = "STRING";
    private static final String INT_NAME = "INT";
    private static final String LONG_NAME = "LONG";
    private static final String FLOAT_NAME = "FLOAT";
    private static final String DOUBLE_NAME = "DOUBLE";
    private static final String UNSIGNED_INT_NAME = "UNSIGNED_INT";
    private static final String UNSIGNED_LONG_NAME = "UNSIGNED_LONG";

    /**
     * A date in ISO 8601 format encode with 3 x 4 bytes in big endian:
     * <ul>
     * <li>[1-4]: year </li>
     * <li>[5-8]: month </li>
     * <li>[9-12]: day </li>
     * </ul>
     * If the time is null then all field are set to 0
     */
    public static final IType<LocalDate> DATE = new IType<LocalDate>() {

        @Override
        public Class<LocalDate> getValueClass() {
            return LocalDate.class;
        }

        @Override
        public byte[] encode(final LocalDate value) {
            byte[] frame = new byte[12];
            ByteBuffer buff = ByteBuffer.wrap(frame);
            buff.order(ByteOrder.BIG_ENDIAN);
            if (value != null) {
                buff.putInt(value.getYear() - 2000);
                buff.putInt(value.getMonthValue());
                buff.putInt(value.getDayOfMonth());
            }
            return frame;
        }

        @Override
        public String getName() {
            return DATE_NAME;
        }
        
    };

    /**
     * A time in ISO 8601 format encode with 3 x 4 bytes in big endian:
     * <ul>
     * <li>[1-4]: hours </li>
     * <li>[5-8]: minutes </li>
     * <li>[9-12]: seconds </li>
     * </ul>
     * If the time is null then all field are set to 0
     */
    public static final IType<LocalTime> TIME = new IType<LocalTime>() {

        @Override
        public Class<LocalTime> getValueClass() {
            return LocalTime.class;
        }

        @Override
        public byte[] encode(final LocalTime value) {
            byte[] frame = new byte[12];
            ByteBuffer buff = ByteBuffer.wrap(frame);
            buff.order(ByteOrder.BIG_ENDIAN);
            if (value != null) {
                buff.putInt(value.getHour());
                buff.putInt(value.getMinute());
                buff.putInt(value.getSecond());
            }
            return frame;
        }

        @Override
        public String getName() {
            return TIME_NAME;
        }
        
    };
    
    /**
     * A clock based on the system clock with an offset (positive or negative) encode with 6 x 4 bytes in big endian:
     * <ul>
     * <li>[1-4]: year </li>
     * <li>[5-8]: month </li>
     * <li>[9-12]: day </li>
     * <li>[13-16]: hour </li>
     * <li>[17-20]: minutes </li>
     * <li>[21-24]: seconds </li>
     * </ul>
     * If the clock is null then all field are set to 0
     * Clock instant is encoded in UTC.
     */
    public static final IType<OffsetClock> OFFSET_CLOCK = new IType<OffsetClock>() {
        
        @Override
        public Class<OffsetClock> getValueClass() {
            return OffsetClock.class;
        }

        @Override
        public byte[] encode(final OffsetClock value) {
            byte[] frame = new byte[24];
            ByteBuffer buff = ByteBuffer.wrap(frame);
            buff.order(ByteOrder.BIG_ENDIAN);
            if (value != null) {
                final ZonedDateTime dateTime = value.instant().atZone(ZoneOffset.UTC);
                buff.putInt(dateTime.getYear() - 2000);
                buff.putInt(dateTime.getMonthValue());
                buff.putInt(dateTime.getDayOfMonth());
                buff.putInt(dateTime.getHour());
                buff.putInt(dateTime.getMinute());
                buff.putInt(dateTime.getSecond());
            }
            return frame;
        }
        
        @Override
        public String getName() {
            return OFFSET_CLOCK_NAME;
        }
        
    };
    
    /**
     * A clock that always returns the same instant encode with 6 x 4 bytes in big endian:
     * <ul>
     * <li>[1-4]: year </li>
     * <li>[5-8]: month </li>
     * <li>[9-12]: day </li>
     * <li>[13-16]: hours </li>
     * <li>[17-20]: minutes </li>
     * <li>[21-24]: seconds </li>
     * </ul>
     * If the clock is null then all field are set to 0
     * Clock instant is encoded in UTC.
     */
    public static final IType<FixedClock> FIXED_CLOCK = new IType<FixedClock>() {

        @Override
        public Class<FixedClock> getValueClass() {
            return FixedClock.class;
        }

        @Override
        public byte[] encode(final FixedClock value) {
            byte[] frame = new byte[24];
            ByteBuffer buff = ByteBuffer.wrap(frame);
            buff.order(ByteOrder.BIG_ENDIAN);
            if (value != null) {
                final ZonedDateTime dateTime = value.instant().atZone(ZoneOffset.UTC);
                buff.putInt(dateTime.getYear() - 2000);
                buff.putInt(dateTime.getMonthValue());
                buff.putInt(dateTime.getDayOfMonth());
                buff.putInt(dateTime.getHour());
                buff.putInt(dateTime.getMinute());
                buff.putInt(dateTime.getSecond());
            }
            return frame;
        }

        @Override
        public String getName() {
            return FIXED_CLOCK_NAME;
        }
        
    };
    
    /**
     * A double encoded as a big-endian 64-bit real number.
     * If double is null then 0 is encoded
     */
    public static final IType<Double> DOUBLE = new IType<Double>() {

        @Override
        public Class<Double> getValueClass() {
            return Double.class;
        }

        @Override
        public byte[] encode(final Double value) {
            byte[] frame = new byte[8];
            ByteBuffer buff = ByteBuffer.wrap(frame);
            buff.order(ByteOrder.BIG_ENDIAN);
            if (value != null) {
                buff.putDouble(value);
            }
            return frame;
        }

        @Override
        public String getName() {
            return DOUBLE_NAME;
        }
        
    };
    
    /**
     * A float encoded as a big-endian 32-bit real number.
     * If float is null then 0 is encoded
     */
    public static final IType<Float> FLOAT = new IType<Float>() {

        @Override
        public Class<Float> getValueClass() {
            return Float.class;
        }

        @Override
        public byte[] encode(final Float value) {
            byte[] frame = new byte[4];
            ByteBuffer buff = ByteBuffer.wrap(frame);
            buff.order(ByteOrder.BIG_ENDIAN);
            if (value != null) {
                buff.putFloat(value);
            }
            return frame;
        }

        @Override
        public String getName() {
            return FLOAT_NAME;
        }
        
    };
    
    /**
     * An String encoded as a big-endian 64-bit unsigned integer.
     */
    public static final IType<String> UNSIGNED_LONG = new IType<String>() {
        
        @Override
        public Class<String> getValueClass() {
            return String.class;
        }
        
        @Override
        public byte[] encode(final String value) {
            byte[] frame = new byte[8];
            ByteBuffer buff = ByteBuffer.wrap(frame);
            buff.order(ByteOrder.BIG_ENDIAN);
            if (value != null) {
                buff.putLong(Long.parseUnsignedLong(value));
            }
            return frame;
        }
        
        @Override
        public String getName() {
            return UNSIGNED_LONG_NAME;
        }
        
    };
    
    /**
     * A Long encoded as a big-endian 64-bit integer.
     * If long is null then 0 is encoded
     */
    public static final IType<Long> LONG = new IType<Long>() {

        @Override
        public Class<Long> getValueClass() {
            return Long.class;
        }

        @Override
        public byte[] encode(final Long value) {
            byte[] frame = new byte[8];
            ByteBuffer buff = ByteBuffer.wrap(frame);
            buff.order(ByteOrder.BIG_ENDIAN);
            if (value != null) {
                buff.putLong(value);
            }
            return frame;
        }

        @Override
        public String getName() {
            return LONG_NAME;
        }
        
    };
    
    /**
     * An Long encoded as a big-endian 32-bit unsigned integer.
     * If long is null then 0 is encoded
     */
    public static final IType<Long> UNSIGNED_INT = new IType<Long>() {

        @Override
        public Class<Long> getValueClass() {
            return Long.class;
        }

        @Override
        public byte[] encode(final Long value) {
            byte[] frame = new byte[4];
            ByteBuffer buff = ByteBuffer.wrap(frame);
            buff.order(ByteOrder.BIG_ENDIAN);
            if (value != null) {
                buff.putInt(value.intValue());
            }
            return frame;
        }

        @Override
        public String getName() {
            return UNSIGNED_INT_NAME;
        }
        
    };
    
    /**
     * An Integer encoded as a big-endian 32-bit integer.
     * If integer is null then 0 is encoded
     */
    public static final IType<Integer> INT = new IType<Integer>() {
        
        @Override
        public Class<Integer> getValueClass() {
            return Integer.class;
        }
        
        @Override
        public byte[] encode(final Integer value) {
            byte[] frame = new byte[4];
            ByteBuffer buff = ByteBuffer.wrap(frame);
            buff.order(ByteOrder.BIG_ENDIAN);
            if (value != null) {
                buff.putInt(value);
            }
            return frame;
        }
        
        @Override
        public String getName() {
            return INT_NAME;
        }
        
    };
    
    /**
     * A Short encoded as big-endian 16-bit integer.
     * If short is null then 0 is encoded
     */
    public static final IType<Short> SHORT = new IType<Short>() {

        @Override
        public Class<Short> getValueClass() {
            return Short.class;
        }

        @Override
        public byte[] encode(final Short value) {
            byte[] frame = new byte[2];
            ByteBuffer buff = ByteBuffer.wrap(frame);
            buff.order(ByteOrder.BIG_ENDIAN);
            if (value != null) {
                buff.putShort(value);
            }
            return frame;
        }

        @Override
        public String getName() {
            return SHORT_NAME;
        }
        
    };
    
    /**
     * A String encoded with UTF_8 charset.
     * Empty string is encoded if null.
     */
    public static final IType<String> STRING = new IType<String>() {

        @Override
        public Class<String> getValueClass() {
            return String.class;
        }

        @Override
        public byte[] encode(String value) {
            return value == null ? new byte[0] : value.getBytes(StandardCharsets.UTF_8);
        }

        @Override
        public String getName() {
            return STRING_NAME;
        }
        
    };
    
    @Override
    public IType<?> getInstance(String name) {
        if (name == null) {
            return null;
        }
        if (name.equals(STRING.getName())) {
            return STRING;
        }
        if (name.equals(OFFSET_CLOCK.getName())) {
            return OFFSET_CLOCK;
        }
        if (name.equals(FIXED_CLOCK.getName())) {
            return FIXED_CLOCK;
        }
        if (name.equals(DATE.getName())) {
            return DATE;
        }
        if (name.equals(TIME.getName())) {
            return TIME;
        }
        if (name.equals(SHORT.getName())) {
            return SHORT;
        }
        if (name.equals(INT.getName())) {
            return INT;
        }
        if (name.equals(LONG.getName())) {
            return LONG;
        }
        if (name.equals(FLOAT.getName())) {
            return FLOAT;
        }
        if (name.equals(DOUBLE.getName())) {
            return DOUBLE;
        }
        if (name.equals(UNSIGNED_INT.getName())) {
            return UNSIGNED_INT;
        }
        if (name.equals(UNSIGNED_LONG.getName())) {
            return UNSIGNED_LONG;
        }
        return null;
    }
    
}
