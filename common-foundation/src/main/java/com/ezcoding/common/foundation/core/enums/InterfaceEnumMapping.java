package com.ezcoding.common.foundation.core.enums;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-09-01 16:53
 */
public class InterfaceEnumMapping implements GenericEnumMappable {

    @Override
    public TypeMappingPair mapPair() {
        return null;
    }

    @Override
    public Object map(Enum<?> source) {
        Class sourceClass = mapPair().getSourceClass();
        return EnumMappableUtils.map(source, sourceClass);
    }

//    @Override
//    public boolean canMap(Class<? extends Enum<?>> target) {
//        return EnumMappable.class.isAssignableFrom(target);
//    }
//
//    @Override
//    public Map<Object, Enum<?>> map(Class<? extends Enum<?>> target) {
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
//        return null;
//    }

}
