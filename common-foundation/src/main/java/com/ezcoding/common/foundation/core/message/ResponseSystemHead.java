package com.ezcoding.common.foundation.core.message;

import com.ezcoding.common.foundation.core.tools.uuid.IdProduceable;
import com.ezcoding.common.foundation.core.tools.uuid.OriginalUuidProducer;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public class ResponseSystemHead extends AbstractSystemHead implements Serializable {

    private static final long serialVersionUID = -7372043515415310976L;
    private static IdProduceable sequenceNoProducer = OriginalUuidProducer.getInstance();
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

    public static IdProduceable getSequenceNoProducer() {
        return sequenceNoProducer;
    }

    public static void setSequenceNoProducer(IdProduceable sequenceNoProducer) {
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

    static void setDefaultProviderId(String defaultProviderId) {
        ResponseSystemHead.defaultProviderId = defaultProviderId;
    }

}
