package com.ezcoding.common.core.enums;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-16 15:40
 */
public class EnumMappableUtils {

    private static final Map<SourceEnumClassPair, Map<?, ? extends Enum<?>>> CLASS_OBJECT_ENUM_MAPPING = new ConcurrentHashMap<>();

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
        Map<?, ? extends Enum<?>> map = CLASS_OBJECT_ENUM_MAPPING.get(new SourceEnumClassPair(enumClass, source.getClass()));
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
        SourceEnumClassPair sourceEnumClassPair = new SourceEnumClassPair(enumClass, (Class<T>) actualTypeArguments[1]);
        Map<T, S> mapping = new HashMap<>();
        for (S enumConstant : enumClass.getEnumConstants()) {
            mapping.put(enumMappable.map(enumConstant), enumConstant);
        }
        CLASS_OBJECT_ENUM_MAPPING.put(sourceEnumClassPair, mapping);
    }

    private static class SourceEnumClassPair {

        Class<?> sourceClass;
        Class<?> targetClass;

        public SourceEnumClassPair(Class<?> sourceClass, Class<?> targetClass) {
            this.sourceClass = sourceClass;
            this.targetClass = targetClass;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            SourceEnumClassPair that = (SourceEnumClassPair) o;
            return sourceClass.equals(that.sourceClass) &&
                    targetClass.equals(that.targetClass);
        }

        @Override
        public int hashCode() {
            return Objects.hash(sourceClass, targetClass);
        }

    }

}
