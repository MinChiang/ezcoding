package com.ezcoding.common.foundation.core.enums;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-09-05 21:13
 */
public interface EnumMappableStrategy {

    /**
     * 判断enum是否能够进行映射
     *
     * @param target 待判断的enum
     * @return 是否能够映射
     */
    boolean canMap(Class<? extends Enum<?>> target);

    /**
     * enum映射
     *
     * @param target 待映射的enum
     * @return 映射结果
     */
    ObjectEnumMappingInfo map(Class<? extends Enum<?>> target);

}
