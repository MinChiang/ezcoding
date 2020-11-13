package com.ezcoding.common.foundation.core.message;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public class ResponseMessage<T> extends AbstractMessage<T> implements Serializable {

    private static final long serialVersionUID = -1585045910330405903L;

    protected ResponseSystemHead systemHead;
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

    @Override
    public boolean valid() {
        return (systemHead != null && systemHead.valid()) && (appHead != null && appHead.valid());
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "systemHead=" + systemHead +
                ", appHead=" + appHead +
                ", body=" + body +
                '}';
    }

}
