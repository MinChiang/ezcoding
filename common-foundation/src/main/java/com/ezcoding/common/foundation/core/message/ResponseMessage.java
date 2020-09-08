package com.ezcoding.common.foundation.core.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public class ResponseMessage<T> extends AbstractMessage<T> implements Serializable {

    private static final long serialVersionUID = -1585045910330405903L;
    public static final String SYS_HEAD = "sysHead";
    public static final String APP_HEAD = "appHead";

    @JsonProperty(value = SYS_HEAD)
    protected ResponseSystemHead systemHead;
    @JsonProperty(value = APP_HEAD)
    protected ResponseAppHead appHead;

    public ResponseMessage() {
    }

    public ResponseMessage(T body) {
        this.body = body;
    }

    public ResponseMessage(ResponseAppHead appHead, T body) {
        this(new ResponseSystemHead(), appHead, body);
    }

    public ResponseMessage(ResponseSystemHead systemHead, ResponseAppHead appHead, T body) {
        super(body);
        this.systemHead = systemHead;
        this.appHead = appHead;
    }

    public boolean success() {
        return Optional.ofNullable(this.appHead).map(ResponseAppHead::success).orElse(false);
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
