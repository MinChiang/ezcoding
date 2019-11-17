package com.ezcoding.common.foundation.core.message.builder;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-01 14:35
 */
public class RequestMessageBuilder {

    public static <T> DefaultRequestMessageBuilder<T> create(T body) {
        return new DefaultRequestMessageBuilder<>(body);
    }

    public static <T> DefaultRequestMessageBuilder<T> create() {
        return new DefaultRequestMessageBuilder<>();
    }

}
