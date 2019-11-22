package com.ezcoding.starter.foundation.config;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezcoding.base.web.extend.core.PageConvertor;
import com.ezcoding.base.web.extend.core.PageInfoConverter;
import com.ezcoding.base.web.extend.spring.aspect.ServiceLoggerAspect;
import com.ezcoding.base.web.extend.spring.resolver.JsonRequestMessageResolver;
import com.ezcoding.base.web.util.PageUtils;
import com.ezcoding.common.foundation.core.message.builder.MessageBuilder;
import com.ezcoding.common.foundation.core.message.handler.JsonMessageBuilderHandler;
import com.ezcoding.common.foundation.core.message.head.PageInfo;
import com.ezcoding.common.foundation.core.message.type.MessageTypeEnum;
import com.ezcoding.common.foundation.core.tools.jwt.AuthSettings;
import com.ezcoding.common.foundation.core.tools.uuid.IUUIDProducer;
import com.ezcoding.common.foundation.core.tools.uuid.SnowflakeUUIDProducer;
import com.ezcoding.common.foundation.util.ConvertUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
 * @date 2019-11-11 10:47
 */
@Configuration
@EnableConfigurationProperties(EzcodingFoundationConfigBean.class)
public class EzcodingAutoConfiguration implements InitializingBean {

    private static final String ENCODING = "UTF-8";
    private static final String SUFFIX = ".properties";
    private static final String DIR = "validation";

    @Autowired
    private EzcodingFoundationConfigBean ezcodingFoundationConfigBean;

    @Bean
    public IUUIDProducer snowflakeUUIDProducer() {
//        //高六位为微服务的服务对应序号，低4位为此类微服务对应的机器号
//        int mechineId = (ApplicationUtils.getApplicationMetadata().getCategoryCode() << (SnowflakeUUIDProducer.MACHINE_BIT - GlobalConstants.Application.APPLICATION_CODE_BIT_LENGTH)) | ApplicationUtils.getApplicationMetadata().getCategoryNo();
        return new SnowflakeUUIDProducer(ezcodingFoundationConfigBean.getMetadata().getDataCenterNo(), ezcodingFoundationConfigBean.getMetadata().getCategoryNo());
    }

    @Primary
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
        objectMapper.setLocale(Locale.CHINESE);
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

        MessageConfigBean message = ezcodingFoundationConfigBean.getMessage();

        MessageBuilder instance = MessageBuilder.getInstance();
        instance.setDefaultErrorResponseCode(message.getErrorResponseCode());
        instance.setDefaultSuccessResponseCode(message.getSuccessResponseCode());
        instance.setDefaultErrorResponseMessage(message.getErrorResponseMessage());
        instance.setDefaultSuccessResponseMessage(message.getSuccessResponseMessage());
        instance.setDefaultMessageBuilder(jsonMessageBuilderHandler);
        instance.setDefaultReadCharset(Charset.forName(message.getReadCharset()));
        instance.setDefaultWriteCharset(Charset.forName(message.getWriteCharset()));
//        instance.setDefaultConsumerId(this.defaultConsumerId);

        instance.setDefaultReadMessageType(MessageTypeEnum.valueOf(message.getReadMessageType()));
        instance.setDefaultWriteMessageType(MessageTypeEnum.valueOf(message.getWriteMessageType()));

        return instance;
    }

    @Bean
    public AuthSettings authSettings() {
        AuthConfigBean auth = ezcodingFoundationConfigBean.getAuth();

        AuthSettings authSettings = new AuthSettings();
        authSettings.setExpiration(auth.getExpiration());
        authSettings.setHeader(auth.getHeader());
        return authSettings;
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
        MetadataConfigBean metadata = ezcodingFoundationConfigBean.getMetadata();
        if (metadata == null) {
            throw new Error("配置元数据不能为空");
        }
        this.doInitPageUtils();
    }

    @Deprecated
    private void doRegisterConverUtils() {
        MessageConfigBean message = ezcodingFoundationConfigBean.getMessage();

        //关于标准报文头的分页类型转换插件
        ConvertUtils.register(new PageInfoConverter(new PageInfo(message.getCurrentPage(), message.getPageSize())), PageInfo.class);
        //关于mybatis-plus分页类型转换插件
        Page page = new Page(message.getCurrentPage(), message.getPageSize());
        page.setSearchCount(false);
        ConvertUtils.register(new PageConvertor(page), Page.class);
    }

    private void doInitPageUtils() {
        MessageConfigBean message = ezcodingFoundationConfigBean.getMessage();

        PageUtils.setDefaultCurrentPage(message.getCurrentPage());
        PageUtils.setDefaultPageSize(message.getPageSize());
    }

}
