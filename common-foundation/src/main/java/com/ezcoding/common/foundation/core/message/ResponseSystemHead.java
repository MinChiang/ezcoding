package com.ezcoding.common.foundation.core.message;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public class ResponseSystemHead extends AbstractSystemHead implements Serializable {

    private static final long serialVersionUID = -7372043515415310976L;

    public static final String PROVIDER_SEQUENCE_NO_KEY = "providerSequenceNo";
    public static final String PROVIDER_ID = "providerId";

    protected String providerSequenceNo;

    public ResponseSystemHead() {
    }

    public ResponseSystemHead(String providerSequenceNo) {
        this.providerSequenceNo = providerSequenceNo;
    }

    public String getProviderSequenceNo() {
        return providerSequenceNo;
    }

    public void setProviderSequenceNo(String providerSequenceNo) {
        this.providerSequenceNo = providerSequenceNo;
    }

    @Override
    public String toString() {
        return "ResponseSystemHead{" +
                "transactionDate=" + transactionDate +
                ", version='" + version + '\'' +
                ", providerSequenceNo='" + providerSequenceNo + '\'' +
                '}';
    }

}
