package com.ezcoding.common.foundation.core.message.builder;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-01 14:35
 */
public class ResponseMessageBuilder {

    public static <T> SuccessResponseBuilder<T> success(T body) {
        return new SuccessResponseBuilder<>(body);
    }

    public static <T> ErrorResponseBuilder<T> error(T body) {
        return new ErrorResponseBuilder<>(body);
    }

    public static SuccessResponseBuilder<?> success() {
        return success(null);
    }

    public static ErrorResponseBuilder<?> error() {
        return error(null);
    }

}
