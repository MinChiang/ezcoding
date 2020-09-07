package com.ezcoding.common.web.convertor;

import com.ezcoding.common.foundation.core.enums.EnumMappableUtils;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.util.Collections;
import java.util.Set;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-09-03 9:21
 */
public class ObjectToEnumConverter implements GenericConverter {

    private final ConvertiblePair convertiblePair;

    public ObjectToEnumConverter(Class<?> sourceClass, Class<?> targetClass) {
        this.convertiblePair = new ConvertiblePair(sourceClass, targetClass);
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(convertiblePair);
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        return EnumMappableUtils.map(source, (Class<? extends Enum>) this.convertiblePair.getTargetType());
    }

}
