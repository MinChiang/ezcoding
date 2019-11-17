package com.ezcoding.common.foundation.core.message;

import com.ezcoding.common.foundation.core.message.head.RequestAppHead;
import com.ezcoding.common.foundation.core.message.head.RequestSystemHead;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public class RequestMessage<T> extends AbstractMessage<T> implements Serializable {

    @JsonProperty(value = "sysHead")
    protected RequestSystemHead systemHead;
    @JsonProperty(value = "appHead")
    protected RequestAppHead appHead;

    public RequestMessage() {
    }

    public RequestMessage(RequestSystemHead systemHead, RequestAppHead appHead, T payload) {
        super(payload);
        this.systemHead = systemHead;
        this.appHead = appHead;
    }

    public RequestSystemHead getSystemHead() {
        return systemHead;
    }

    public void setSystemHead(RequestSystemHead systemHead) {
        this.systemHead = systemHead;
    }

    public RequestAppHead getAppHead() {
        return appHead;
    }

    public void setAppHead(RequestAppHead appHead) {
        this.appHead = appHead;
    }
}
