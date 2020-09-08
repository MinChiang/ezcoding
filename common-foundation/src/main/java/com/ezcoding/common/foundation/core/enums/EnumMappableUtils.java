package com.ezcoding.common.foundation.core.enums;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-16 15:40
 */
public class EnumMappableUtils {

    private static final Map<MappingPair, Map<?, ? extends Enum<?>>> OBJECT_TO_ENUM_MAPPING = new ConcurrentHashMap<>();
    private static final Map<Class<? extends Enum<?>>, EnumObjectMappingInfo> ENUM_TO_OBJECT_MAPPING = new ConcurrentHashMap<>();

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
        Map<?, ? extends Enum<?>> map = OBJECT_TO_ENUM_MAPPING.get(new MappingPair(source.getClass(), enumClass));
        return map == null ? null : (S) map.get(source);
    }

    /**
     * 获取枚举映射的枚举类型7
     *
     * @param e   待被映射的enum
     * @param <S> enum类型
     * @param <T> 标识对象
     * @return 被映射后的枚举实例
     */
    public static <S extends Enum<S>, T> T map(Enum<S> e) {
        if (e == null) {
            return null;
        }
        EnumObjectMappingInfo enumObjectMappingInfo = ENUM_TO_OBJECT_MAPPING.get(e.getClass());
        if (enumObjectMappingInfo == null) {
            return null;
        }
        return (T) enumObjectMappingInfo.getMapping().get(e);
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
        MappingPair mappingPair = new MappingPair(enumClass, (Class<T>) actualTypeArguments[1]);
        Map<T, S> objectEnumMap = new HashMap<>();
        for (S enumConstant : enumClass.getEnumConstants()) {
            T map = enumMappable.map(enumConstant);
            if (map == null) {
                continue;
            }
            objectEnumMap.put(map, enumConstant);
        }
        OBJECT_TO_ENUM_MAPPING.put(mappingPair, objectEnumMap);
    }

    /**
     * 注册解析接口
     *
     * @param genericEnumMappable 待注册的解析接口
     */
    public static void register(GenericEnumMappable genericEnumMappable) {
        MappingPair mappingPair = genericEnumMappable.mapPair();
        if (mappingPair == null || mappingPair.getSourceClass() == null || mappingPair.getTargetClass() == null) {
            return;
        }
        Map<Object, Enum<?>> objectEnumMap = new HashMap<>();

        Class<Enum<?>> sourceClass = (Class<Enum<?>>) mappingPair.getSourceClass();
        for (Enum<?> enumConstant : sourceClass.getEnumConstants()) {
            Object map = genericEnumMappable.map(enumConstant);
            if (map == null) {
                continue;
            }
            objectEnumMap.put(map, enumConstant);
        }
        OBJECT_TO_ENUM_MAPPING.put(mappingPair, objectEnumMap);
    }

    /**
     * 注册解析接口
     *
     * @param objectEnumMappingInfo 映射关系
     */
    public static void register(ObjectEnumMappingInfo objectEnumMappingInfo) {
        if (objectEnumMappingInfo == null) {
            return;
        }
        OBJECT_TO_ENUM_MAPPING.put(objectEnumMappingInfo.getMappingPair(), objectEnumMappingInfo.getMapping());
    }

    /**
     * 注册解析接口
     *
     * @param enumObjectMappingInfo 映射关系
     */
    public static void register(EnumObjectMappingInfo enumObjectMappingInfo) {
        if (enumObjectMappingInfo == null) {
            return;
        }
        ENUM_TO_OBJECT_MAPPING.put((Class<? extends Enum<?>>) enumObjectMappingInfo.getMappingPair().getSourceClass(), enumObjectMappingInfo);
    }

    /**
     * 获取所有注册的enum到目标映射类型
     *
     * @return 目标映射类型
     */
    public static Set<MappingPair> acquireObjectToEnumMapping() {
        return OBJECT_TO_ENUM_MAPPING.keySet();
    }

    /**
     * 获取所有注册的enum到目标映射类型
     *
     * @return 目标映射类型
     */
    public static Set<MappingPair> acquireEnumToObjectMapping() {
        Set<MappingPair> set = new HashSet<>();
        for (EnumObjectMappingInfo value : ENUM_TO_OBJECT_MAPPING.values()) {
            set.add(value.getMappingPair());
        }
        return set;
    }

}
