package com.ezcoding.web.convertor;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * spring mvc string->enum类型转换器
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-17 11:49
 */
public class StringToEnumConverterFactory implements ConverterFactory<String, Enum> {

    private static final Map<Class, Converter> CONVERTER_MAP = new ConcurrentHashMap<>();

    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
        Converter result = CONVERTER_MAP.get(targetType);
        if (result == null) {
            result = new StringToEnum(targetType);
            CONVERTER_MAP.put(targetType, result);
        }
        return result;
    }

    private static class StringToEnum<T extends IEnumConvert> implements Converter<String, T> {
        private Map<String, T> enumMap = new HashMap<>();

        public StringToEnum(Class<T> enumType) {
            T[] enums = enumType.getEnumConstants();
            for (T e : enums) {
                this.enumMap.put(e.getConvertValue().toString(), e);
            }
        }

        @Override
        public T convert(String source) {
            return this.enumMap.get(source);
        }
    }
}
