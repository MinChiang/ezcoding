package com.ezcoding.common.foundation.core.message.builder;

import com.ezcoding.common.foundation.core.message.AbstractMessage;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-01 14:38
 */
public interface IMessageBuildable<T> {

    /**
     * 构建响应实例
     *
     * @return 响应实例
     */
    AbstractMessage<T> build();

}
