package com.ezcoding.base.web.config;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezcoding.base.web.extend.core.PageConvertor;
import com.ezcoding.base.web.extend.core.PageInfoConverter;
import com.ezcoding.base.web.extend.spring.aspect.ServiceLoggerAspect;
import com.ezcoding.base.web.extend.spring.resolver.JsonRequestMessageResolver;
import com.ezcoding.base.web.util.PageUtils;
import com.ezcoding.common.foundation.core.constant.GlobalConstants;
import com.ezcoding.common.foundation.core.message.builder.MessageBuilder;
import com.ezcoding.common.foundation.core.message.handler.JsonMessageBuilderHandler;
import com.ezcoding.common.foundation.core.message.head.PageInfo;
import com.ezcoding.common.foundation.core.message.type.MessageTypeEnum;
import com.ezcoding.common.foundation.core.tools.jwt.AuthSettings;
import com.ezcoding.common.foundation.core.tools.uuid.IUUIDProducer;
import com.ezcoding.common.foundation.core.tools.uuid.SnowflakeUUIDProducer;
import com.ezcoding.common.foundation.util.ApplicationUtils;
import com.ezcoding.common.foundation.util.ConvertUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-16 21:52
 */
@Configuration
@PropertySource(value = "classpath:toolConfig.properties", encoding = "UTF-8")
public class ToolConfig implements InitializingBean {

    private static final String ENCODING = "UTF-8";
    private static final String SUFFIX = ".properties";
    private static final String DIR = "validation";

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

    @Value("${PAGE_SIZE}")
    private int defaultPageSize;
    @Value("${CURRENT_PAGE_NO}")
    private int defaultCurrentPage;

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
        objectMapper.setLocale(Locale.CHINESE);

        //将long转化为string，解决序列化long精度丢失的问题
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        simpleModule.addSerializer(long.class, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);

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
        objectMapper.setLocale(Locale.CHINESE);
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
        instance.setDefaultErrorResponseCode(this.defaultErrorResponseCode);
        instance.setDefaultSuccessResponseCode(this.defaultSuccessResponseCode);
        instance.setDefaultErrorResponseMessage(this.defaultErrorResponseMessage);
        instance.setDefaultSuccessResponseMessage(this.defaultSuccessResponseMessage);
        instance.setDefaultMessageBuilder(jsonMessageBuilderHandler);
        instance.setDefaultReadCharset(Charset.forName(this.defaultReadCharset));
        instance.setDefaultWriteCharset(Charset.forName(this.defaultWriteCharset));
        instance.setDefaultConsumerId(this.defaultConsumerId);

        instance.setDefaultReadMessageType(MessageTypeEnum.valueOf(this.defaultReadMessageType));
        instance.setDefaultWriteMessageType(MessageTypeEnum.valueOf(this.defaultWriteMessageType));

        return instance;
    }

    @Bean
    public ServiceLoggerAspect serviceLoggerAspect() {
        return new ServiceLoggerAspect();
    }

    @Bean
    public AuthSettings authSettings() {
        AuthSettings authSettings = new AuthSettings();
        authSettings.setExpiration(this.defaultTokenExpiration);
        authSettings.setHeader(this.defaultTokenHeader);
        return authSettings;
    }

    @Bean
    public JsonRequestMessageResolver requestMessageResolver(MessageBuilder messageBuilder) {
        return new JsonRequestMessageResolver(messageBuilder);
    }

    /**
     * 加载默认的语言资源
     *
     * @return 语言资源
     */
    @Bean
    public MessageSource messageSource() {
        List<String> strings = Lists.newArrayList();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resolver.getResources("classpath*:validation/*.properties");
            for (Resource resource : resources) {
                String path = resource.getURL().getPath();
                String name = StringUtils.substringAfterLast(path, DIR);
                name = StringUtils.substringBefore(name, SUFFIX);
                strings.add(DIR + name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ResourceBundleMessageSource rbms = new ResourceBundleMessageSource();
        rbms.setDefaultEncoding(ENCODING);
        rbms.setBasenames(strings.toArray(new String[strings.size()]));
        return rbms;
    }

    /**
     * 注册默认分页类型
     */
    @Override
    public void afterPropertiesSet() {
        this.doInitPageUtils();
    }

    @Deprecated
    private void doRegisterConverUtils() {
        //关于标准报文头的分页类型转换插件
        ConvertUtils.register(new PageInfoConverter(new PageInfo(this.defaultCurrentPage, this.defaultPageSize)), PageInfo.class);
        //关于mybatis-plus分页类型转换插件
        Page page = new Page(this.defaultCurrentPage, this.defaultPageSize);
        page.setSearchCount(false);
        ConvertUtils.register(new PageConvertor(page), Page.class);
    }

    private void doInitPageUtils() {
        PageUtils.setDefaultCurrentPage(this.defaultCurrentPage);
        PageUtils.setDefaultPageSize(this.defaultPageSize);
    }

}
