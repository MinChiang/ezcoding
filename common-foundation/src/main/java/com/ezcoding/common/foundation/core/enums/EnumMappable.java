package com.ezcoding.common.foundation.core.enums;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-09-24 10:23
 */
public interface EnumMappable<T> {

    /**
     * 转化为可标识的id
     *
     * @return 可标识的id
     */
    T map();

}
