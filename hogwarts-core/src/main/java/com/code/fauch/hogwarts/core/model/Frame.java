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
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A frame contains information to package and send payload through UDP packets.
 * 
 * @author c.fauch
 *
 */
public final class Frame<T extends IContent> {

    /**
     * Unique identifier of this frame.
     */
    private final UUID id;

    /**
     * Frame label.
     */
    private final String label;
    
    /**
     * Payload content type.
     */
    private final IContentType<T> contentType;
    
    /**
     * Payload parameters.
     */
    private final List<String> parameters;

    /**
     * (Optional)The expected content size of the payload in bytes (may be null)
     */
    private final Integer size;
    
    /**
     * The sending rate in ms.
     */
    private final long rate;

    /**
     * (optional)The source binding port.
     */
    private final Integer srcPort;
    
    /**
     * The destination port
     */
    private final Integer dstPort;
    
    /**
     * The destination IP.
     */
    private final String dstIp;
    
    /**
     * List of simulation to except
     */
    private final List<String> exceptSimulation;
    /**
     * Constructor.
     * 
     * @param id the unique identifier (may be null)
     * @param label the label of the frame (not null)
     * @param type the content type of the payload (not null)
     * @param parameters the list of parameters of the payload (not null may be empty)
     * @param size the expected size of the payload (if null size is auto-adjust)
     * @param rate the sending rate in ms
     * @param srcPort the source binding port
     * @param dstIp the destination IP (not null)
     * @param dstPort the destination port
     * @param except the list of name of simulation to except
     */
    public Frame(final UUID id, final String label, final IContentType<T> type, final List<String> parameters, 
            final Integer size, final long rate, final Integer srcPort, final String dstIp, final Integer dstPort,
            final List<String> except) {
        this.id = id;
        this.label = Objects.requireNonNull(label, "label is missing");;
        this.contentType =  Objects.requireNonNull(type, "type is missing");
        this.parameters = Collections.unmodifiableList(
                new ArrayList<>(Objects.requireNonNull(parameters, "parameters is missing")));
        this.size = size;
        this.rate = rate;
        this.srcPort = srcPort;
        this.dstPort = dstPort;
        this.dstIp = Objects.requireNonNull(dstIp, "dstIp is missing");
        this.exceptSimulation = except == null ? Collections.emptyList() : 
            Collections.unmodifiableList(new ArrayList<>(except));

    }
    /**
     * @return the id
     */
    public UUID getId() {
        return id;
    }
    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }
    /**
     * @return the contentType
     */
    public IContentType<T> getContentType() {
        return contentType;
    }
    /**
     * @return the parameters
     */
    public List<String> getParameters() {
        return parameters;
    }
    /**
     * @return the size
     */
    public Integer getSize() {
        return size;
    }
    /**
     * @return the rate
     */
    public long getRate() {
        return rate;
    }
    /**
     * @return the srcPort
     */
    public Integer getSrcPort() {
        return srcPort;
    }
    /**
     * @return the dstPort
     */
    public Integer getDstPort() {
        return dstPort;
    }
    /**
     * @return the dstIp
     */
    public String getDstIp() {
        return dstIp;
    }
    /**
     * @return the exceptSimulation
     */
    public List<String> getExceptSimulation() {
        return exceptSimulation;
    }
    
}
