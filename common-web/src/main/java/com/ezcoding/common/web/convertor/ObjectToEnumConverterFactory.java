package com.ezcoding.common.web.convertor;

import com.ezcoding.common.foundation.util.EnumMappableUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * spring mvc string->enum类型转换器
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-17 11:49
 */
public class ObjectToEnumConverterFactory implements ConverterFactory<Object, Enum> {

    @Override
    public <T extends Enum> Converter<Object, T> getConverter(Class<T> targetType) {
        return new ObjectToEnumConverter<>(targetType);
    }

    private static class ObjectToEnumConverter<T extends Enum> implements Converter<Object, T> {

        private Class<T> targetClass;

        ObjectToEnumConverter(Class<T> targetClass) {
            this.targetClass = targetClass;
        }

        @Override
        public T convert(Object source) {
            return EnumMappableUtils.mapIgnoreType(source, this.targetClass);
        }

    }

}
