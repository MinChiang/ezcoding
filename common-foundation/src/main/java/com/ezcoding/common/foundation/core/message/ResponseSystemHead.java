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

    protected String providerId;
    protected String providerSequenceNo;

    public ResponseSystemHead() {
    }

    public ResponseSystemHead(String providerId, String providerSequenceNo) {
        this.providerId = providerId;
        this.providerSequenceNo = providerSequenceNo;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderSequenceNo() {
        return providerSequenceNo;
    }

    public void setProviderSequenceNo(String providerSequenceNo) {
        this.providerSequenceNo = providerSequenceNo;
    }

//    @Override
//    public boolean valid() {
//        return super.valid() && providerId != null && providerSequenceNo != null;
//    }

    @Override
    public String toString() {
        return "ResponseSystemHead{" +
                "providerId='" + providerId + '\'' +
                ", providerSequenceNo='" + providerSequenceNo + '\'' +
                ", transactionDate=" + transactionDate +
                ", version='" + version + '\'' +
                '}';
    }

}
