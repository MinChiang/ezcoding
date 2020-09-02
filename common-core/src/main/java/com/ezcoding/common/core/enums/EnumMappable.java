package com.ezcoding.common.core.enums;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-09-01 14:29
 */
public interface EnumMappable<S> {

    /**
     * 转化为可标识的id
     *
     * @return 可标识的id
     */
    S to();

}
