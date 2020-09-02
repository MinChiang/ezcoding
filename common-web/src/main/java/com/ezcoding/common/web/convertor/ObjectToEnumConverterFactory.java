package com.ezcoding.common.web.convertor;

import com.ezcoding.common.core.enums.EnumMappableUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * spring mvc string->enum类型转换器
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-17 11:49
 */
public class ObjectToEnumConverterFactory implements ConverterFactory<Object, Enum<?>> {

    private final Map<Class<?>, Converter<Object, ? extends Enum<?>>> MAP = new ConcurrentHashMap<>();

    @Override
    public <T extends Enum<?>> Converter<Object, T> getConverter(Class<T> targetType) {
        Converter<Object, ? extends Enum<?>> result = MAP.get(targetType);
        if (result == null) {
            result = new ObjectToEnumConverter<>(targetType);
            MAP.put(targetType, result);
        }
        return (Converter<Object, T>) result;
    }

    private static class ObjectToEnumConverter<T extends Enum<?>> implements Converter<Object, T> {

        private final Class<T> targetClass;

        ObjectToEnumConverter(Class<T> targetClass) {
            this.targetClass = targetClass;
        }

        @Override
        public T convert(Object source) {
            return EnumMappableUtils.mapIgnoreType(source, this.targetClass);
        }

    }

}
