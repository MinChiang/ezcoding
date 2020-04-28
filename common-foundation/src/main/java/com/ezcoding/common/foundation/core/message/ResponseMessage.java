package com.ezcoding.common.foundation.core.message;

import com.ezcoding.common.foundation.core.message.head.ResponseAppHead;
import com.ezcoding.common.foundation.core.message.head.ResponseSystemHead;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public class ResponseMessage<T> extends AbstractMessage<T> implements Serializable {

    public static final String SYS_HEAD = "sysHead";
    public static final String APP_HEAD = "appHead";

    @JsonProperty(value = SYS_HEAD)
    protected ResponseSystemHead systemHead;
    @JsonProperty(value = APP_HEAD)
    protected ResponseAppHead appHead;

    public ResponseMessage() {
    }

    public ResponseMessage(T payload) {
        this.payload = payload;
    }

    public boolean success() {
        return Optional.ofNullable(this.appHead).map(ResponseAppHead::success).orElse(false);
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
