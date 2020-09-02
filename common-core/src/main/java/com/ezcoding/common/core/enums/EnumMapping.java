package com.ezcoding.common.core.enums;

import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-09-01 16:51
 */
public interface EnumMapping {

    /**
     * 判断是否可以映射
     *
     * @param target enum类
     * @return 是否可以进行映射
     */
    boolean canMap(Class<? extends Enum<?>> target);

    /**
     * 获取映射关系
     *
     * @param target enum类
     * @return 映射关系
     */
    Map<Object, Enum<?>> map(Class<? extends Enum<?>> target);

}
