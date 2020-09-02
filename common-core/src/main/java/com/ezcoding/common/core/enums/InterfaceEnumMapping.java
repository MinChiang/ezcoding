package com.ezcoding.common.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-09-01 16:53
 */
public class InterfaceEnumMapping implements EnumMapping {

    @Override
    public boolean canMap(Class<? extends Enum<?>> target) {
        return EnumMappable.class.isAssignableFrom(target);
    }

    @Override
    public Map<Object, Enum<?>> map(Class<? extends Enum<?>> target) {
//        Enum<?>[] enumConstants = target.getEnumConstants();
//        if (enumConstants == null || enumConstants.length == 0) {
//            return null;
//        }
//
//        Map<Object, Enum<?>> result = new HashMap<>();
//        for (Enum<?> enumConstant : enumConstants) {
//            result.put(((EnumMappable) enumConstant).map(), enumConstant);
//        }
//        return result;
        return null;
    }

}
