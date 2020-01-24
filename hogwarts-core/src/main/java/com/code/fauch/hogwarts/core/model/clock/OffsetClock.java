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
package com.code.fauch.hogwarts.core.model.clock;

import java.time.Clock;
import java.time.Duration;
import java.time.ZoneOffset;
import java.util.Objects;

/**
 * This class defines a clock based on the system clock with an offset (positive or negative).
 * 
 * @author c.fauch
 *
 */
public final class OffsetClock extends AbsClock<Duration> {

    /**
     * Constructor
     * 
     * @param offset the offset of the clock (positive or negative duration, not null)
     */
    public OffsetClock(final Duration offset) {
        super(
                Objects.requireNonNull(offset, "offset is missing"), 
                Clock.offset(Clock.system(ZoneOffset.UTC), offset)
        );
    }

    @Override
    public String toString() {
        return "Clock [offset="+this.getInformation()+"]";
    }
    
}
