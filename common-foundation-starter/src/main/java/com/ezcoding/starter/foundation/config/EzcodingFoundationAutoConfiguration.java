package com.ezcoding.starter.foundation.config;

import com.ezcoding.common.foundation.core.message.builder.IMessageBuilder;
import com.ezcoding.common.foundation.core.message.builder.MessageBuilder;
import com.ezcoding.common.foundation.core.message.handler.JsonMessageBuilderHandler;
import com.ezcoding.common.foundation.core.message.type.MessageTypeEnum;
import com.ezcoding.common.foundation.core.tools.jwt.AuthSettings;
import com.ezcoding.common.foundation.core.tools.uuid.IUUIDProducer;
import com.ezcoding.common.foundation.core.tools.uuid.OriginalUUIDProducer;
import com.ezcoding.common.foundation.core.tools.uuid.SnowflakeUUIDProducer;
import com.ezcoding.common.foundation.core.validation.PrependMessageInterpolator;
import com.ezcoding.common.foundation.util.ConvertUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomUtils;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.validation.beanvalidation.LocaleContextMessageInterpolator;
import org.springframework.validation.beanvalidation.MessageSourceResourceBundleLocator;

import javax.validation.MessageInterpolator;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.nio.charset.Charset;
import java.util.Locale;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-11 10:47
 */
@Configuration
@EnableConfigurationProperties(EzcodingFoundationConfigBean.class)
public class EzcodingFoundationAutoConfiguration implements InitializingBean {

//    private static final String ENCODING = "UTF-8";
//    private static final String SUFFIX = ".properties";
//    private static final String DIR = "validation";

    @Autowired
    private EzcodingFoundationConfigBean ezcodingFoundationConfigBean;

    /**
     * 注册默认分页类型
     */
    @Override
    public void afterPropertiesSet() {
        ConvertUtils.init();
//        MetadataConfigBean metadata = ezcodingFoundationConfigBean.getMetadata();
//        if (metadata == null) {
//            throw new Error("配置元数据不能为空");
//        }
//        this.doInitPageUtils();
    }

    @Primary
    @ConditionalOnMissingBean(SnowflakeUUIDProducer.class)
    @Bean("snowflakeUUIDProducer")
    public IUUIDProducer snowflakeUUIDProducer() {
//        //高六位为微服务的服务对应序号，低4位为此类微服务对应的机器号
//        int mechineId = (ApplicationUtils.getApplicationMetadata().getCategoryCode() << (SnowflakeUUIDProducer.MACHINE_BIT - GlobalConstants.Application.APPLICATION_CODE_BIT_LENGTH)) | ApplicationUtils.getApplicationMetadata().getCategoryNo();
        long datacenterId = RandomUtils.nextLong(0, SnowflakeUUIDProducer.MAX_DATACENTER_NUM);
        long machineId = RandomUtils.nextLong(0, SnowflakeUUIDProducer.MAX_MACHINE_NUM);
        if (ezcodingFoundationConfigBean.getMetadata() != null) {
            datacenterId = ezcodingFoundationConfigBean.getMetadata().getDataCenterNo();
            machineId = ezcodingFoundationConfigBean.getMetadata().getCategoryNo();
        }
        return new SnowflakeUUIDProducer(datacenterId, machineId);
    }

    @ConditionalOnMissingBean(OriginalUUIDProducer.class)
    @Bean("originalUUIDProducer")
    public IUUIDProducer originalUUIDProducer() {
//        //高六位为微服务的服务对应序号，低4位为此类微服务对应的机器号
//        int mechineId = (ApplicationUtils.getApplicationMetadata().getCategoryCode() << (SnowflakeUUIDProducer.MACHINE_BIT - GlobalConstants.Application.APPLICATION_CODE_BIT_LENGTH)) | ApplicationUtils.getApplicationMetadata().getCategoryNo();
        return OriginalUUIDProducer.getInstance();
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
    @ConditionalOnMissingBean(name = "serializableObjectMapper")
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

    @ConditionalOnMissingBean(IMessageBuilder.class)
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
        return ezcodingFoundationConfigBean.getAuth();
    }

//    /**
//     * 加载默认的语言资源
//     *
//     * @return 语言资源
//     */
//    @Bean
//    public MessageSource messageSource() {
//        List<String> strings = Lists.newArrayList();
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        try {
//            Resource[] resources = resolver.getResources("classpath*:message/*.properties");
//            for (Resource resource : resources) {
//                String path = resource.getURL().getPath();
//                String name = StringUtils.substringAfterLast(path, DIR);
//                name = StringUtils.substringBefore(name, SUFFIX);
//                strings.add(DIR + name);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        ResourceBundleMessageSource rbms = new ResourceBundleMessageSource();
//        rbms.setDefaultEncoding(ENCODING);
//        rbms.setBasenames(strings.toArray(new String[strings.size()]));
//        return rbms;
//    }

    /**
     * 1.默认读取ValidationMessages.properties中的内容，注意此文件需要使用UTF-8进行编码
     * 2.开启快速校验模式，当遇到第一个校验失败时马上返回
     * 3.使用PrependMessageInterpolator默认消息插值模板，若想使用默认的消息插值方式，则在payload里面添加对应的Default.class
     *
     * @param messageSource 对应的messageSource
     * @return 校验器
     */
    @Bean
    public Validator validator(MessageSource messageSource) {
        ResourceBundleLocator resourceBundleLocator = new MessageSourceResourceBundleLocator(messageSource);
        MessageInterpolator messageInterpolator = new PrependMessageInterpolator(resourceBundleLocator);
        MessageInterpolator localeContextMessageInterpolator = new LocaleContextMessageInterpolator(messageInterpolator);

        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                //开启快速校验，只抛出第一个检测到的异常
                .failFast(true)
                .messageInterpolator(localeContextMessageInterpolator)
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }

//    @Deprecated
//    private void doRegisterConverUtils() {
//        MessageConfigBean message = ezcodingFoundationConfigBean.getMessage();
//
//        //关于标准报文头的分页类型转换插件
//        ConvertUtils.register(new PageInfoConverter(new PageInfo(message.getCurrentPage(), message.getPageSize())), PageInfo.class);
//        //关于mybatis-plus分页类型转换插件
//        Page page = new Page(message.getCurrentPage(), message.getPageSize());
//        page.setSearchCount(false);
//        ConvertUtils.register(new PageConvertor(page), Page.class);
//    }

//    private void doInitPageUtils() {
//        MessageConfigBean message = ezcodingFoundationConfigBean.getMessage();
//
//        PageUtils.setDefaultCurrentPage(message.getCurrentPage());
//        PageUtils.setDefaultPageSize(message.getPageSize());
//    }

}
