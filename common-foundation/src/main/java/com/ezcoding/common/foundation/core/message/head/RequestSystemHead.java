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

    private static String defaultConsumerId;

    public static final String CONSUMER_ID = "consumerId";
    public static final String CONSUMER_SEQUENCE_NO = "consumerSequenceNo";
    
    private static IUUIDProducer sequenceNoProducer = OriginalUUIDProducer.getInstance();

    @JsonProperty(value = CONSUMER_ID)
    protected String consumerId;
    @JsonProperty(value = CONSUMER_SEQUENCE_NO)
    protected String consumerSequenceNo;

    public RequestSystemHead() {
        this.consumerId = defaultConsumerId;
        this.consumerSequenceNo = sequenceNoProducer.produce();
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

    public static String getDefaultConsumerId() {
        return defaultConsumerId;
    }

    public static void setDefaultConsumerId(String defaultConsumerId) {
        RequestSystemHead.defaultConsumerId = defaultConsumerId;
    }

}
