package com.ezcoding.common.foundation.core.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public abstract class AbstractMessage<T> implements Message<T>, Serializable {

    private static final long serialVersionUID = -5655558365282029885L;
    public static final String BODY = "body";

    @JsonProperty(value = BODY)
    protected T body;

    public AbstractMessage() {
    }

    public AbstractMessage(T body) {
        this.body = body;
    }

    @Override
    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

}
