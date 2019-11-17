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
public class ResponseSystemHead extends AbstractSystemHead implements Serializable {

    private static IUUIDProducer sequenceNoProducer = OriginalUUIDProducer.getInstance();

    @JsonProperty(value = "providerId")
    protected String providerId;
    @JsonProperty(value = "providerSequenceNo")
    protected String providerSequenceNo;

    public ResponseSystemHead() {
        providerSequenceNo = sequenceNoProducer.produce();
    }

    @Deprecated
    public ResponseSystemHead(String providerId, String providerSequenceNo) {
        this.providerId = providerId;
        this.providerSequenceNo = providerSequenceNo;
    }

    public static IUUIDProducer getSequenceNoProducer() {
        return sequenceNoProducer;
    }

    public static void setSequenceNoProducer(IUUIDProducer sequenceNoProducer) {
        ResponseSystemHead.sequenceNoProducer = sequenceNoProducer;
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
}
