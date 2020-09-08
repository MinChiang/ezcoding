package com.ezcoding.common.foundation.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-09-01 16:53
 */
public class InterfaceEnumMapping implements EnumMappableStrategy {

    @Override
    public boolean canMap(Class<? extends Enum<?>> target) {
        return EnumMappable.class.isAssignableFrom(target);
    }

    @Override
    public ObjectEnumMappingInfo map(Class<? extends Enum<?>> target) {
        Enum<?>[] enumConstants = target.getEnumConstants();
        if (enumConstants == null || enumConstants.length == 0) {
            return null;
        }

        Map<Object, Enum<?>> mapping = new HashMap<>();
        Class<?> targetClass = null;
        for (Enum<?> enumConstant : enumConstants) {
            Object map = ((EnumMappable) enumConstant).map(enumConstant);
            mapping.put(map, enumConstant);
            if (targetClass == null && map != null) {
                targetClass = map.getClass();
            }
        }
        return new ObjectEnumMappingInfo(new MappingPair(targetClass, target), mapping);
    }

}
