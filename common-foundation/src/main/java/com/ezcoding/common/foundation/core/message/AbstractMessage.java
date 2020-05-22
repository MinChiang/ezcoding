package com.ezcoding.common.foundation.core.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public abstract class AbstractMessage<T> implements Message, Serializable {

    public static final String BODY = "body";

    @JsonProperty(value = BODY)
    protected T payload;

    public AbstractMessage() {
    }

    public AbstractMessage(T payload) {
        this.payload = payload;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

}
