package com.ezcoding.common.foundation.core.message;

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

    @JsonProperty(value = CONSUMER_ID)
    protected String consumerId;
    @JsonProperty(value = CONSUMER_SEQUENCE_NO)
    protected String consumerSequenceNo;

    public RequestSystemHead() {
    }

    public RequestSystemHead(String consumerId, String consumerSequenceNo) {
        this.consumerId = consumerId;
        this.consumerSequenceNo = consumerSequenceNo;
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

    @Override
    public boolean valid() {
        return super.valid() && consumerId != null && consumerSequenceNo != null;
    }

}
