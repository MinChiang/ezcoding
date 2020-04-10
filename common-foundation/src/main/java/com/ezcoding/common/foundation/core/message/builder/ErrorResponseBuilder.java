package com.ezcoding.common.foundation.core.message.builder;

import com.ezcoding.common.foundation.core.message.ResponseMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-01 14:44
 */
public class ErrorResponseBuilder<T> extends AbstractBodyBuilder<T> {

    private String errorCode;
    private List<String> errorMessage = new ArrayList<>(0);

    ErrorResponseBuilder(T body) {
        super(body);
    }

    public ErrorResponseBuilder() {
    }

    public ErrorResponseBuilder<T> errorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public ErrorResponseBuilder<T> errorMessage(String errorMessage) {
        this.errorMessage.add(errorMessage);
        return this;
    }

    @Override
    public ResponseMessage<T> build() {
        return MessageBuilder.getInstance().buildErrorResponseMessage(this.errorCode, this.errorMessage, this.body);
    }

}
