package com.ezcoding.common.foundation.core.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-09-02 10:42
 */
public class JacksonEnumMapping implements EnumMappableStrategy {

    @Override
    public boolean canMap(Class<? extends Enum<?>> target) {
        return acquireJsonValueAnnotationMethod(target) != null || acquireJsonValueAnnotationField(target) != null;
    }

    @Override
    public ObjectEnumMappingInfo map(Class<? extends Enum<?>> target) {
        Enum<?>[] enumConstants = target.getEnumConstants();
        if (enumConstants == null || enumConstants.length == 0) {
            return null;
        }

        Method method = acquireJsonValueAnnotationMethod(target);
        boolean methodAccessible = false;
        if (method != null) {
            methodAccessible = method.isAccessible();
            method.setAccessible(true);
        }
        Field field = acquireJsonValueAnnotationField(target);
        boolean fieldAccessible = false;
        if (field != null) {
            fieldAccessible = field.isAccessible();
            field.setAccessible(true);
        }

        Map<Object, Enum<?>> mapping = new HashMap<>();
        Class<?> targetClass = null;
        try {
            for (Enum<?> enumConstant : enumConstants) {
                Object key = null;
                if (method != null) {
                    key = method.invoke(enumConstant);
                } else if (field != null) {
                    key = field.get(enumConstant);
                }
                if (targetClass == null && key != null) {
                    targetClass = key.getClass();
                }
                mapping.put(key, enumConstant);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        } finally {
            if (method != null) {
                method.setAccessible(methodAccessible);
            }
            if (field != null && fieldAccessible) {
                field.setAccessible(fieldAccessible);
            }
        }
        return new ObjectEnumMappingInfo(new MappingPair(targetClass, target), mapping);
    }

    private Method acquireJsonValueAnnotationMethod(Class<? extends Enum<?>> target) {
        return Arrays
                .stream(target.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(JsonValue.class))
                .findFirst()
                .orElse(null);
    }

    private Field acquireJsonValueAnnotationField(Class<? extends Enum<?>> target) {
        return Arrays
                .stream(target.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(JsonValue.class))
                .findFirst()
                .orElse(null);
    }

}
