package com.ezcoding.common.foundation.core.enums;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-16 15:40
 */
public class EnumMappableUtils {

    private static final Map<TypeMappingPair, Map<?, ? extends Enum<?>>> CLASS_OBJECT_ENUM_MAPPING = new ConcurrentHashMap<>();

    /**
     * 获取枚举映射的枚举类型
     *
     * @param source    需要映射的参数
     * @param enumClass 待被映射的enum
     * @param <S>       enum类型
     * @param <T>       标识对象
     * @return 被映射后的枚举实例
     */
    public static <S extends Enum<S>, T> S map(T source, Class<S> enumClass) {
        Map<?, ? extends Enum<?>> map = CLASS_OBJECT_ENUM_MAPPING.get(new TypeMappingPair(enumClass, source.getClass()));
        return map == null ? null : (S) map.get(source);
    }

    /**
     * 注册解析接口
     *
     * @param enumMappable 待注册的解析接口
     */
    public static <S extends Enum<S>, T> void register(EnumMappable<S, T> enumMappable) {
        if (enumMappable == null) {
            return;
        }
        Type[] genericInterfaces = enumMappable.getClass().getGenericInterfaces();
        ParameterizedType genericInterface = (ParameterizedType) genericInterfaces[0];
        Type[] actualTypeArguments = genericInterface.getActualTypeArguments();
        Class<S> enumClass = (Class<S>) actualTypeArguments[0];
        TypeMappingPair typeMappingPair = new TypeMappingPair(enumClass, (Class<T>) actualTypeArguments[1]);
        Map<T, S> mapping = new HashMap<>();
        for (S enumConstant : enumClass.getEnumConstants()) {
            T map = enumMappable.map(enumConstant);
            if (map == null) {
                continue;
            }
            mapping.put(map, enumConstant);
        }
        CLASS_OBJECT_ENUM_MAPPING.put(typeMappingPair, mapping);
    }

    /**
     * 注册解析接口
     *
     * @param genericEnumMappable 待注册的解析接口
     */
    public static void register(GenericEnumMappable genericEnumMappable) {
        TypeMappingPair typeMappingPair = genericEnumMappable.mapPair();
        if (typeMappingPair == null || typeMappingPair.getSourceClass() == null || typeMappingPair.getTargetClass() == null) {
            return;
        }
        Map<Object, Enum<?>> mapping = new HashMap<>();
        for (Enum<?> enumConstant : typeMappingPair.getSourceClass().getEnumConstants()) {
            Object map = genericEnumMappable.map(enumConstant);
            if (map == null) {
                continue;
            }
            mapping.put(map, enumConstant);
        }
        CLASS_OBJECT_ENUM_MAPPING.put(typeMappingPair, mapping);
    }

    /**
     * 注册解析接口
     *
     * @param typeMappingInfo 映射关系
     */
    public static void register(TypeMappingInfo typeMappingInfo) {
        if (typeMappingInfo == null) {
            return;
        }
        CLASS_OBJECT_ENUM_MAPPING.put(typeMappingInfo.getTypeMappingPair(), typeMappingInfo.getMapping());
    }

    /**
     * 获取所有注册的enum到目标映射类型
     *
     * @return 目标映射类型
     */
    public static Set<TypeMappingPair> acquireAllTypeMapping() {
        return CLASS_OBJECT_ENUM_MAPPING.keySet();
    }

}
