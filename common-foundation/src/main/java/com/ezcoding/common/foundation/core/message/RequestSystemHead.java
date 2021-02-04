package com.ezcoding.common.foundation.core.message;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public class RequestSystemHead extends AbstractSystemHead implements Serializable {

    private static final long serialVersionUID = 7333737769389481420L;

    protected String consumerId;
    protected String consumerSequenceNo;

    RequestSystemHead() {
    }

    RequestSystemHead(String consumerId, String consumerSequenceNo) {
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

//    @Override
//    public boolean valid() {
//        return super.valid() && consumerId != null && consumerSequenceNo != null;
//    }

    @Override
    public String toString() {
        return "RequestSystemHead{" +
                "consumerId='" + consumerId + '\'' +
                ", consumerSequenceNo='" + consumerSequenceNo + '\'' +
                ", transactionDate=" + transactionDate +
                ", version='" + version + '\'' +
                '}';
    }

}
