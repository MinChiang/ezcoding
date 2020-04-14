package com.ezcoding.common.foundation.core.message.builder;

import com.ezcoding.common.foundation.core.exception.ApplicationException;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-01 14:35
 */
public class ResponseMessageBuilder {

    public static <T> SuccessResponseBuilder<T> success(T body) {
        return new SuccessResponseBuilder<>(body);
    }

    public static SuccessResponseBuilder<?> success() {
        return success(null);
    }

    public static <T> ErrorResponseBuilder<T> error(T body) {
        return new ErrorResponseBuilder<>(body);
    }

    public static <T> ErrorResponseBuilder<?> error(ApplicationException exception) {
        return new ErrorResponseBuilder<>().errorCode(exception.getIdentification()).errorMessage(exception.getSummary());
    }

    public static ErrorResponseBuilder<?> error() {
        return new ErrorResponseBuilder<>();
    }

}
