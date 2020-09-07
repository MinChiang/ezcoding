package com.ezcoding.common.foundation.core.message;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-26 23:37
 */
public interface Message<T> {

    /**
     * 获取信息载体
     *
     * @return 获取的信息载体
     */
    T getPayload();

}
