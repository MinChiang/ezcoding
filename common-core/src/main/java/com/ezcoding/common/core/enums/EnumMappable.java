package com.ezcoding.common.core.enums;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-09-01 14:29
 */
public interface EnumMappable<S extends Enum<S>, T> {

    /**
     * 转化为可标识的id
     *
     * @param s 待映射的enum
     * @return 可标识的id
     */
    T map(S s);

}
