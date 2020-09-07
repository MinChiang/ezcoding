package com.ezcoding.common.foundation.core.enums;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-09-01 18:13
 */
public class SimpleEnumMapping extends AbstractEnumMappableStrategy {

    @Override
    public boolean canMap(Class<? extends Enum<?>> target) {
        return acquireNotEnumFields(target).size() == 1;
    }

    @Override
    public TypeMappingInfo doMap(Class<? extends Enum<?>> target) {
        Enum<?>[] enumConstants = target.getEnumConstants();
        if (enumConstants == null || enumConstants.length == 0) {
            return null;
        }

        Field field = acquireNotEnumFields(target).get(0);
        boolean fieldAccessible = field.isAccessible();
        field.setAccessible(true);
        Map<Object, Enum<?>> mapping = new HashMap<>();
        Class<?> targetClass = null;
        try {
            for (Enum<?> enumConstant : enumConstants) {
                Object o = field.get(enumConstant);
                mapping.put(o, enumConstant);
                if (targetClass == null && o != null) {
                    targetClass = o.getClass();
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            field.setAccessible(fieldAccessible);
        }
        return new TypeMappingInfo(new TypeMappingPair(target, targetClass), mapping);
    }

    /**
     * 获取非enum成员变量列表
     *
     * @param target enum类
     * @return 非enum成员变量列表
     */
    private List<Field> acquireNotEnumFields(Class<? extends Enum<?>> target) {
        return Arrays
                .stream(target.getDeclaredFields())
                .filter(field -> field.getType() != target && !field.getType().isArray())
                .collect(Collectors.toList());
    }

}
