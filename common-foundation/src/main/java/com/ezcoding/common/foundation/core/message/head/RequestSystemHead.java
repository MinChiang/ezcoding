package com.ezcoding.common.foundation.core.message.head;

import com.ezcoding.common.foundation.core.tools.uuid.IUUIDProducer;
import com.ezcoding.common.foundation.core.tools.uuid.OriginalUUIDProducer;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public class RequestSystemHead extends AbstractSystemHead implements Serializable {

    private static IUUIDProducer sequenceNoProducer = OriginalUUIDProducer.getInstance();

    @JsonProperty(value = "consumerId")
    protected String consumerId;
    @JsonProperty(value = "consumerSequenceNo")
    protected String consumerSequenceNo;

    public RequestSystemHead() {
        this(null, sequenceNoProducer.produce());
    }

    public RequestSystemHead(String consumerId, String consumerSequenceNo) {
        this.consumerId = consumerId;
        this.consumerSequenceNo = consumerSequenceNo;
    }

    public static IUUIDProducer getSequenceNoProducer() {
        return sequenceNoProducer;
    }

    public static void setSequenceNoProducer(IUUIDProducer sequenceNoProducer) {
        RequestSystemHead.sequenceNoProducer = sequenceNoProducer;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public String getConsumerSequenceNo() {
        return consumerSequenceNo;
    }

    public void setConsumerSequenceNo(String consumerSequenceNo) {
        this.consumerSequenceNo = consumerSequenceNo;
    }
}
