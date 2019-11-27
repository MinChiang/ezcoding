package com.ezcoding.gateway.config;

import com.ezcoding.common.foundation.core.constant.GlobalConstants;
import com.ezcoding.common.foundation.core.message.builder.MessageBuilder;
import com.ezcoding.common.foundation.core.message.handler.JsonMessageBuilderHandler;
import com.ezcoding.common.foundation.core.message.type.MessageTypeEnum;
import com.ezcoding.common.foundation.core.tools.jwt.AuthSettings;
import com.ezcoding.common.foundation.core.tools.jwt.TokenTools;
import com.ezcoding.common.foundation.core.tools.uuid.IUUIDProducer;
import com.ezcoding.common.foundation.core.tools.uuid.SnowflakeUUIDProducer;
import com.ezcoding.common.foundation.util.ApplicationUtils;
import com.ezcoding.common.foundation.util.KeyUtils;
import com.ezcoding.gateway.core.error.ApplicationErrorController;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.PublicKey;
import java.util.List;
import java.util.Locale;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-10-14 20:13
 */
@Configuration
@PropertySource(value = "classpath:toolConfig.properties", encoding = "UTF-8")
public class GatewayToolConfig {

    @Value("${CHARSET_READ_MESSAGE}")
    private String defaultReadCharset;
    @Value("${CHARSET_WRITE_MESSAGE}")
    private String defaultWriteCharset;
    @Value("${REQ_TYPE}")
    private String defaultReadMessageType;
    @Value("${RSP_TYPE}")
    private String defaultWriteMessageType;

    @Value("${ERROR_MESSAGE_RESPONSE_CODE}")
    private String defaultErrorResponseCode;
    @Value("${SUCCESS_MESSAGE_RESPONSE_CODE}")
    private String defaultSuccessResponseCode;
    @Value("${ERROR_MESSAGE_RESPONSE_MESSAGE}")
    private String defaultErrorResponseMessage;
    @Value("${SUCCESS_MESSAGE_RESPONSE_MESSAGE}")
    private String defaultSuccessResponseMessage;

    @Value("${token.header}")
    private String defaultTokenHeader;
    @Value("${token.expiration}")
    private long defaultTokenExpiration;

    @Value("${spring.application.name}")
    private String defaultConsumerId;

    @Bean
    public IUUIDProducer snowflakeUUIDProducer() {
        //高六位为微服务的服务对应序号，低4位为此类微服务对应的机器号
        int mechineId = (ApplicationUtils.getApplicationMetadata().getCategoryCode() << (SnowflakeUUIDProducer.MACHINE_BIT - GlobalConstants.Application.APPLICATION_CODE_BIT_LENGTH)) | ApplicationUtils.getApplicationMetadata().getCategoryNo();
        return new SnowflakeUUIDProducer(ApplicationUtils.getApplicationMetadata().getDataCenterNo(), mechineId);
    }

    @Primary
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
        objectMapper.setLocale(Locale.SIMPLIFIED_CHINESE);
        return objectMapper;
    }

    /**
     * 用于序列化使用
     *
     * @return json序列化器
     */
    @Bean(value = "serializableObjectMapper")
    public ObjectMapper serializableObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
        objectMapper.setLocale(Locale.SIMPLIFIED_CHINESE);
        //带上对应的@class标志
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        return objectMapper;
    }

    @Bean
    public MessageBuilder messageBuilder(ObjectMapper objectMapper, IUUIDProducer producer) {
        JsonMessageBuilderHandler jsonMessageBuilderHandler = new JsonMessageBuilderHandler();
        jsonMessageBuilderHandler.setObjectMapper(objectMapper);
        MessageBuilder.configHandler(MessageTypeEnum.JSON, jsonMessageBuilderHandler);
        MessageBuilder.setIdProducer(producer);

        MessageBuilder instance = MessageBuilder.getInstance();
        instance.setDefaultErrorResponseCode(defaultErrorResponseCode);
        instance.setDefaultSuccessResponseCode(defaultSuccessResponseCode);
        instance.setDefaultErrorResponseMessage(defaultErrorResponseMessage);
        instance.setDefaultSuccessResponseMessage(defaultSuccessResponseMessage);
        instance.setDefaultMessageBuilder(jsonMessageBuilderHandler);
        instance.setDefaultReadCharset(Charset.forName(defaultReadCharset));
        instance.setDefaultWriteCharset(Charset.forName(defaultWriteCharset));
        instance.setDefaultConsumerId(defaultConsumerId);

        instance.setDefaultReadMessageType(MessageTypeEnum.valueOf(defaultReadMessageType));
        instance.setDefaultWriteMessageType(MessageTypeEnum.valueOf(defaultWriteMessageType));

        return instance;
    }

    @Bean
    public AuthSettings authSettings() {
        AuthSettings authSettings = new AuthSettings();
        authSettings.setExpiration(defaultTokenExpiration);
        authSettings.setHeader(defaultTokenHeader);
        return authSettings;
    }

    @Bean
    public TokenTools tokenTools() throws IOException {
        PublicKey publicKey = KeyUtils.loadPublickey("key/rsa_public_key.pem");
        return new TokenTools(null, publicKey);
    }

    @Bean
    public ApplicationErrorController basicErrorController(ErrorAttributes errorAttributes, ServerProperties serverProperties, List<ErrorViewResolver> errorViewResolvers) {
        return new ApplicationErrorController(errorAttributes, serverProperties.getError(), errorViewResolvers);
    }

}
