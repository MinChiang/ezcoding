package com.ezcoding.common.foundation.util;

import com.fasterxml.jackson.annotation.JsonValue;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-16 15:40
 */
public class EnumMappableUtils {

    private static Map<Class<?>, Map<Object, ? extends Enum<?>>> CLASS_OBJECT_ENUM_MAPPING = new ConcurrentHashMap<>();
    private static Map<Class<?>, Map<String, ? extends Enum<?>>> CLASS_OBJECT_ENUM_MAPPING_IGNORE_TYPE = new ConcurrentHashMap<>();

    /**
     * 获取枚举映射的枚举类型
     *
     * @param object    需要映射的参数
     * @param enumClass 待被映射的enum
     * @param <T>       被映射后的枚举实例
     * @return 被映射后的枚举实例
     */
    public static <T extends Enum<T>> T map(Object object, Class<T> enumClass) {
        Map<Object, ? extends Enum<?>> serializableEnumMap = CLASS_OBJECT_ENUM_MAPPING.get(enumClass);
        if (serializableEnumMap == null) {
            createMapping(enumClass);
            serializableEnumMap = CLASS_OBJECT_ENUM_MAPPING.get(enumClass);
        }
        return (T) serializableEnumMap.get(object);
    }

    /**
     * 获取枚举映射的枚举类型（无校验类型）
     *
     * @param object    需要映射的参数（无需校验参数类型）
     * @param enumClass 待被映射的enum
     * @param <T>       被映射后的枚举实例
     * @return 被映射后的枚举实例
     */
    public static <T extends Enum<?>> T mapIgnoreType(Object object, Class<T> enumClass) {
        Map<String, ? extends Enum<?>> serializableEnumMap = CLASS_OBJECT_ENUM_MAPPING_IGNORE_TYPE.get(enumClass);
        if (serializableEnumMap == null) {
            createMapping(enumClass);
            serializableEnumMap = CLASS_OBJECT_ENUM_MAPPING_IGNORE_TYPE.get(enumClass);
        }
        return (T) serializableEnumMap.get(object.toString());
    }

    /**
     * 构建映射关系
     *
     * @param enumClass 被映射的枚举类型
     * @param <T>       被映射的枚举类型
     */
    private static <T extends Enum<?>> void createMapping(Class<T> enumClass) {
        Field field = null;
        Method method = null;
        Map<Object, Enum<?>> mapping = new HashMap<>();
        Map<String, Enum<?>> mappingIgnoreType = new HashMap<>();

        Field[] declaredFields = enumClass.getDeclaredFields();
        Method[] methods = enumClass.getMethods();

        if (declaredFields.length > 0) {
            for (Field declaredField : declaredFields) {
                if (declaredField.getDeclaredAnnotation(JsonValue.class) != null) {
                    field = declaredField;
                    break;
                }
            }
        }

        if (field == null && methods.length > 0) {
            for (Method declareMethod : methods) {
                if (declareMethod.getDeclaredAnnotation(JsonValue.class) != null) {
                    method = declareMethod;
                    break;
                }
            }
        }

        for (T t : enumClass.getEnumConstants()) {
            try {

                Object key;

                if (field != null) {
                    field.setAccessible(true);
                    key = field.get(t);
                } else if (method != null) {
                    key = method.invoke(t, (Object[]) null);
                } else {
                    key = t.ordinal();
                }

                mapping.put(key, t);
                mappingIgnoreType.put(key.toString(), t);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        CLASS_OBJECT_ENUM_MAPPING.put(enumClass, mapping);
        CLASS_OBJECT_ENUM_MAPPING_IGNORE_TYPE.put(enumClass, mappingIgnoreType);
    }

}
