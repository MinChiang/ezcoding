package com.ezcoding.common.foundation.core.message.head;

import com.ezcoding.common.foundation.core.tools.uuid.IdProduceable;
import com.ezcoding.common.foundation.core.tools.uuid.OriginalUuidProducer;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public class RequestSystemHead extends AbstractSystemHead implements Serializable {

    private static final long serialVersionUID = 7333737769389481420L;
    public static final String CONSUMER_ID = "consumerId";
    public static final String CONSUMER_SEQUENCE_NO = "consumerSequenceNo";

    private static String defaultConsumerId;
    private static IdProduceable sequenceNoProducer = OriginalUuidProducer.getInstance();

    @JsonProperty(value = CONSUMER_ID)
    protected String consumerId;
    @JsonProperty(value = CONSUMER_SEQUENCE_NO)
    protected String consumerSequenceNo;

    public RequestSystemHead() {
        this.consumerId = defaultConsumerId;
        this.consumerSequenceNo = sequenceNoProducer.produce();
    }

    public static IdProduceable getSequenceNoProducer() {
        return sequenceNoProducer;
    }

    public static void setSequenceNoProducer(IdProduceable sequenceNoProducer) {
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
