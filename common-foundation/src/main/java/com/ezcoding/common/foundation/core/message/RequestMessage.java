package com.ezcoding.common.foundation.core.message;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public class RequestMessage<T> extends AbstractMessage<T> implements Serializable {

    private static final long serialVersionUID = 2571149482460360333L;

    protected RequestSystemHead systemHead;
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

    @Override
    public boolean valid() {
        return (systemHead != null && systemHead.valid()) && (appHead != null && appHead.valid());
    }

    @Override
    public String toString() {
        return "RequestMessage{" +
                "systemHead=" + systemHead +
                ", appHead=" + appHead +
                ", body=" + body +
                '}';
    }

}
