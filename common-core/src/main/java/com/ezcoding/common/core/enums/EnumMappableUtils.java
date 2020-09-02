package com.ezcoding.common.core.enums;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-16 15:40
 */
public class EnumMappableUtils {

    private static final Map<Class<?>, Map<Object, ? extends Enum<?>>> CLASS_OBJECT_ENUM_MAPPING = new ConcurrentHashMap<>();
    private static final Map<Class<?>, Map<String, ? extends Enum<?>>> CLASS_OBJECT_ENUM_MAPPING_IGNORE_TYPE = new ConcurrentHashMap<>();
    private static final List<EnumMapping> MAPPINGS = new CopyOnWriteArrayList<>();

    static {
        MAPPINGS.add(new InterfaceEnumMapping());
        MAPPINGS.add(new JacksonEnumMapping());
        MAPPINGS.add(new SimpleEnumMapping());
    }

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
        return serializableEnumMap == null ? null : (T) serializableEnumMap.get(object);
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
        return serializableEnumMap == null ? null : (T) serializableEnumMap.get(object.toString());
    }

    /**
     * 构建映射关系
     *
     * @param enumClass 被映射的枚举类型
     * @param <T>       被映射的枚举类型
     */
    private static <T extends Enum<?>> void createMapping(Class<T> enumClass) {
        for (EnumMapping mapping : MAPPINGS) {
            if (!mapping.canMap(enumClass)) {
                continue;
            }

            Map<Object, Enum<?>> map = mapping.map(enumClass);
            if (map != null && !map.isEmpty()) {
                CLASS_OBJECT_ENUM_MAPPING.put(enumClass, map);
                Map<String, Enum<?>> mappingIgnoreType = new HashMap<>();
                for (Map.Entry<Object, Enum<?>> entry : map.entrySet()) {
                    mappingIgnoreType.put(entry.getKey().toString(), entry.getValue());
                }
                CLASS_OBJECT_ENUM_MAPPING_IGNORE_TYPE.put(enumClass, mappingIgnoreType);
            }
        }
    }

    /**
     * 注册解析接口
     *
     * @param enumMapping 待注册的解析接口
     */
    public static void register(EnumMapping enumMapping) {
        if (enumMapping == null) {
            return;
        }
        MAPPINGS.add(enumMapping);
    }

}
