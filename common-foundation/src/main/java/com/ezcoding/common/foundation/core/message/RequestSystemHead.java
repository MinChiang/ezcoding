package com.ezcoding.common.foundation.core.message;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public class RequestSystemHead extends AbstractSystemHead implements Serializable {

    private static final long serialVersionUID = 7333737769389481420L;

    protected String consumerSequenceNo;

    public RequestSystemHead() {
    }

    public RequestSystemHead(String consumerSequenceNo) {
        this.consumerSequenceNo = consumerSequenceNo;
    }

    public String getConsumerSequenceNo() {
        return consumerSequenceNo;
    }

    public void setConsumerSequenceNo(String consumerSequenceNo) {
        this.consumerSequenceNo = consumerSequenceNo;
    }

    @Override
    public String toString() {
        return "RequestSystemHead{" +
                "transactionDate=" + transactionDate +
                ", version='" + version + '\'' +
                ", consumerSequenceNo='" + consumerSequenceNo + '\'' +
                '}';
    }

}
