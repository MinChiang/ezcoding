package com.ezcoding.common.web.convertor;

import com.ezcoding.common.foundation.core.enums.EnumMappableUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-09-08 13:56
 */
public class StandardEnumSerializer extends StdSerializer<Enum<?>> {

    public StandardEnumSerializer(Class<Enum<?>> t) {
        super(t);
    }

    @Override
    public void serialize(Enum<?> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeObject(EnumMappableUtils.map(value));
    }

}
