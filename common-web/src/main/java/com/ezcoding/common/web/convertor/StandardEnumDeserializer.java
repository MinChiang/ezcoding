package com.ezcoding.common.web.convertor;

import com.ezcoding.common.foundation.core.enums.EnumMappableUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-09-08 16:27
 */
public class StandardEnumDeserializer extends StdDeserializer<Enum<?>> {

    protected Class<?> sourceClass;

    public StandardEnumDeserializer(Class<?> sourceClass, Class<Enum<?>> targetClass) {
        super(targetClass);
        this.sourceClass = sourceClass;
    }

    @Override
    public Enum<?> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Object o = p.readValueAs(this.sourceClass);
        return EnumMappableUtils.map(o, (Class<Enum>) this._valueClass);
    }

}
