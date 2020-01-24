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

/**
 * Customize java clock.
 * 
 * @author c.fauch
 *
 */
public abstract class AbsClock<T> {
    
    /**
     * The java clock.
     */
    private final Clock clock;
    
    /**
     * The clock information
     */
    private final T information;
    
    /**
     * Constructor.
     * 
     * @param information information about the clock customization
     * @param clock the clock
     */
    AbsClock(final T information, Clock clock) {
        this.information = information;
        this.clock = clock;
    }
    
    /**
     * Returns the current instant of the clock
     * @return the current instant
     */
    public final Instant instant() {
        return this.clock.instant();
    }
    
    /**
     * Returns information about the clock customization.
     * (may be offset, instant according to the type of the clock)
     * @return the clock customization
     */
    public final T getInformation() {
        return this.information;
    }
    
}
