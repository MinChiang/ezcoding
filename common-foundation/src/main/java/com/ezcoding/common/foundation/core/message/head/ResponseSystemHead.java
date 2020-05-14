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
    private static String defaultProviderId;

    public static final String PROVIDER_SEQUENCE_NO_KEY = "providerSequenceNo";
    public static final String PROVIDER_ID = "providerId";

    @JsonProperty(value = PROVIDER_ID)
    protected String providerId;
    @JsonProperty(value = PROVIDER_SEQUENCE_NO_KEY)
    protected String providerSequenceNo;

    public ResponseSystemHead() {
        this.providerSequenceNo = sequenceNoProducer.produce();
        this.providerId = defaultProviderId;
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

    public static String getDefaultProviderId() {
        return defaultProviderId;
    }

    public static void setDefaultProviderId(String defaultProviderId) {
        ResponseSystemHead.defaultProviderId = defaultProviderId;
    }

}
