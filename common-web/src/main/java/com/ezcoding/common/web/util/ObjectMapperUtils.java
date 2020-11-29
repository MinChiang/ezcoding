package com.ezcoding.common.web.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.util.Locale;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-11 11:10
 */
public class ObjectMapperUtils {

    /**
     * 生成基本的objectmapper
     *
     * @return 生成的objectmapper
     */
    public static ObjectMapper base() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
        objectMapper.setLocale(Locale.getDefault());
        return objectMapper;
    }

    /**
     * 制定message objectMapper特性
     *
     * @param objectMapper 需要指定特性的objectMapper
     */
    public static void messageCustomizeConfig(ObjectMapper objectMapper) {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        simpleModule.addSerializer(long.class, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
    }

    /**
     * 生成用于报文传输的objectmapper
     *
     * @return 生成的objectmapper
     */
    public static ObjectMapper message() {
        ObjectMapper objectMapper = base();
        messageCustomizeConfig(objectMapper);
        return objectMapper;
    }

    /**
     * 生成用于持久化的objectmapper
     *
     * @return 生成的objectmapper
     */
    public static ObjectMapper persist() {
        ObjectMapper objectMapper = base();

        //带上对应的@class标志
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        return objectMapper;
    }

}
