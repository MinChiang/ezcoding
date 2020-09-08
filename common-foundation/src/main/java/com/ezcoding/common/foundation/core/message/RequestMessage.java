package com.ezcoding.common.foundation.core.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public class RequestMessage<T> extends AbstractMessage<T> implements Serializable {

    private static final long serialVersionUID = 2571149482460360333L;
    public static final String SYS_HEAD = "sysHead";
    public static final String APP_HEAD = "appHead";

    @JsonProperty(value = SYS_HEAD)
    protected RequestSystemHead systemHead;
    @JsonProperty(value = APP_HEAD)
    protected RequestAppHead appHead;

    public RequestMessage() {
    }

    public RequestMessage(RequestSystemHead systemHead, RequestAppHead appHead, T body) {
        super(body);
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
