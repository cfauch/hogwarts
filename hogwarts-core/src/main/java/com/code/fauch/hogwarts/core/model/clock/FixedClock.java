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
import java.time.Instant;
import java.time.ZoneId;
import java.util.Objects;

/**
 * This class defines a clock that always returns the same instant.
 * 
 * @author c.fauch
 *
 */
public final class FixedClock extends AbsClock<Instant> {

    /**
     * Constructor.
     * 
     * @param instant the instant (not null)
     * @param zone the time-zone to use to convert the instant to date-time (not null)
     */
    public FixedClock(final ZoneId zone, final Instant instant) {
        super(
                Objects.requireNonNull(instant, "instant is missing"), 
                Clock.fixed(instant, Objects.requireNonNull(zone, "zone is missing"))
        );
    }

    @Override
    public String toString() {
        return "Clock [instant="+this.getInformation()+"]";
    }
    
}
