package com.ezcoding.common.foundation.core.enums;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-09-01 16:51
 */
public interface GenericEnumMappable {

    /**
     * 获取映射键值对
     *
     * @return 映射键值对
     */
    TypeMappingPair mapPair();

    /**
     * 获取映射关系
     *
     * @param source 原始对象
     * @return 映射后的对象
     */
    Object map(Enum<?> source);

}
