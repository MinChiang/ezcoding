package com.ezcoding.common.foundation.core.message.builder;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-01 14:38
 */
public abstract class AbstractBodyBuilder<T> implements IMessageBuildable<T> {

    protected T body;

    AbstractBodyBuilder(T body) {
        this.body = body;
    }

    AbstractBodyBuilder() {
    }

}
