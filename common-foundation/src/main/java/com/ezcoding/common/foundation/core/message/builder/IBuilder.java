package com.ezcoding.common.foundation.core.message.builder;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-12-07 9:30
 */
public interface IBuilder<C> {

    /**
     * 构建对象
     *
     * @return 构建后的对象
     */
    C build();

}
