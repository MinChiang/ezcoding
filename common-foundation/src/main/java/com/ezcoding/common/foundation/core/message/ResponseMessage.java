package com.ezcoding.common.foundation.core.message;

import com.ezcoding.common.foundation.core.message.head.ResponseAppHead;
import com.ezcoding.common.foundation.core.message.head.ResponseSystemHead;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public class ResponseMessage<T> extends AbstractMessage<T> implements Serializable {

    @JsonProperty(value = "sysHead")
    protected ResponseSystemHead systemHead;
    @JsonProperty(value = "appHead")
    protected ResponseAppHead appHead;

    public ResponseMessage() {
    }

    public ResponseMessage(T payload) {
        this.payload = payload;
    }

    public ResponseMessage(ResponseAppHead appHead, T payload) {
        this(new ResponseSystemHead(), appHead, payload);
    }

    public ResponseMessage(ResponseSystemHead systemHead, ResponseAppHead appHead, T payload) {
        super(payload);
        this.systemHead = systemHead;
        this.appHead = appHead;
    }

    public ResponseSystemHead getSystemHead() {
        return systemHead;
    }

    public void setSystemHead(ResponseSystemHead systemHead) {
        this.systemHead = systemHead;
    }

    public ResponseAppHead getAppHead() {
        return appHead;
    }

    public void setAppHead(ResponseAppHead appHead) {
        this.appHead = appHead;
    }

}
