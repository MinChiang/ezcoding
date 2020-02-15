package com.ezcoding.common.foundation.starter;

import com.ezcoding.common.foundation.core.application.ApplicationLayerModule;
import com.ezcoding.common.foundation.core.application.FunctionLayerModule;
import com.ezcoding.common.foundation.core.application.ModuleLayerModule;
import com.ezcoding.common.foundation.core.exception.BaseModuleExceptionBuilderFactory;
import com.ezcoding.common.foundation.core.exception.processor.*;
import com.ezcoding.common.foundation.core.message.builder.IMessageBuilder;
import com.ezcoding.common.foundation.core.message.builder.MessageBuilder;
import com.ezcoding.common.foundation.core.message.handler.JsonMessageBuilderHandler;
import com.ezcoding.common.foundation.core.message.type.MessageTypeEnum;
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
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static com.ezcoding.common.foundation.core.exception.ModuleConstants.DEFAULT_APPLICATION_LAYER_MODULE;
import static com.ezcoding.common.foundation.core.exception.ModuleConstants.DEFAULT_MODULE_LAYER_MODULE;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-11 10:47
 */
@Configuration
@EnableConfigurationProperties(EzcodingFoundationConfigBean.class)
public class EzcodingFoundationAutoConfiguration implements InitializingBean {

    @Autowired
    private EzcodingFoundationConfigBean ezcodingFoundationConfigBean;
    @Autowired
    private MessageSource messageSource;

    /**
     * 注册默认分页类型
     */
    @Override
    public void afterPropertiesSet() {
        ConvertUtils.init();
        initApplicationMetadata();
    }

    private void initApplicationMetadata() {
        MetadataConfigBean metadata = ezcodingFoundationConfigBean.getMetadata();
        ApplicationLayerModule.setApplicationCodeLength(metadata.getApplicationCodeLength());
        ApplicationLayerModule.setApplicationFillChar(metadata.getApplicationFillChar());
        ModuleLayerModule.setModuleCodeLength(metadata.getModuleCodeLength());
        ModuleLayerModule.setModuleFillChar(metadata.getModuleFillChar());
        FunctionLayerModule.setFunctionCodeLength(metadata.getFunctionCodeLength());
        FunctionLayerModule.setFunctionFillChar(metadata.getFunctionFillChar());
    }

    @Bean
    @ConditionalOnMissingBean(BaseModuleExceptionBuilderFactory.class)
    public BaseModuleExceptionBuilderFactory baseModuleExceptionBuilderFactory() {
        return new BaseModuleExceptionBuilderFactory();
    }

    @Primary
    @ConditionalOnMissingBean(SnowflakeUUIDProducer.class)
    @Bean("snowflakeUUIDProducer")
    public IUUIDProducer snowflakeUuidProducer() {
//        //高六位为微服务的服务对应序号，低4位为此类微服务对应的机器号
//        int mechineId = (ApplicationUtils.getApplicationMetadata().getCategoryCode() << (SnowflakeUUIDProducer.MACHINE_BIT - GlobalConstants.Application.APPLICATION_CODE_BIT_LENGTH)) | ApplicationUtils.getApplicationMetadata().getCategoryNo();

        Optional<MetadataConfigBean> metadata = Optional.ofNullable(ezcodingFoundationConfigBean.getMetadata());
        Long datacenterId = metadata
                .map(MetadataConfigBean::getDataCenterNo)
                .orElseGet(() -> RandomUtils.nextLong(0, SnowflakeUUIDProducer.MAX_DATACENTER_NUM));
        Long machineId = metadata
                .map(MetadataConfigBean::getCategoryNo)
                .orElseGet(() -> RandomUtils.nextLong(0, SnowflakeUUIDProducer.MAX_MACHINE_NUM));
        return new SnowflakeUUIDProducer(datacenterId, machineId);
    }

    @ConditionalOnMissingBean(OriginalUUIDProducer.class)
    @Bean("originalUUIDProducer")
    public IUUIDProducer originalUuidProducer() {
        return OriginalUUIDProducer.getInstance();
    }

    private void customObjectMapperConfig(ObjectMapper objectMapper) {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        simpleModule.addSerializer(long.class, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
    }

    @Primary
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
        objectMapper.setLocale(Locale.SIMPLIFIED_CHINESE);

        this.customObjectMapperConfig(objectMapper);
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
        this.customObjectMapperConfig(objectMapper);
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

    /**
     * 1.默认读取ValidationMessages.properties中的内容，注意此文件需要使用UTF-8进行编码
     * 2.开启快速校验模式，当遇到第一个校验失败时马上返回
     * 3.使用PrependMessageInterpolator默认消息插值模板，若想使用默认的消息插值方式，则在payload里面添加对应的Default.class
     *
     * @return 校验器
     */
    @Bean
    public Validator validator() {
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

    @ConditionalOnMissingBean(AbstractApplicationExceptionManager.class)
    @Bean("defaultExceptionManager")
    public ModuleApplicationExceptionManager moduleApplicationExceptionManager(@Autowired(required = false) @Qualifier(value = "defaultLayerModuleProcessor") AbstractLayerModuleProcessor defaultProcessor,
                                                                               @Autowired(required = false) List<IApplicationExceptionProcessorConfigurer> registers) {
        ModuleApplicationExceptionManager moduleApplicationExceptionManager = new ModuleApplicationExceptionManager(defaultProcessor);
        registerDefaultProcessor(moduleApplicationExceptionManager, defaultProcessor);
        if (CollectionUtils.isNotEmpty(registers)) {
            registerExtraProcessor(moduleApplicationExceptionManager, registers, defaultProcessor);
        }
        return moduleApplicationExceptionManager;
    }

    private void registerDefaultProcessor(ModuleApplicationExceptionManager moduleApplicationExceptionManager,
                                          AbstractLayerModuleProcessor defaultProcessor) {
        moduleApplicationExceptionManager.registerApplicationProcessor(DEFAULT_APPLICATION_LAYER_MODULE, new ApplicationLayerModuleProcessor(defaultProcessor));
        moduleApplicationExceptionManager.registerModuleProcessor(DEFAULT_MODULE_LAYER_MODULE, new ModuleLayerModuleProcessor(defaultProcessor));
    }

    private void registerExtraProcessor(ModuleApplicationExceptionManager moduleApplicationExceptionManager,
                                        List<IApplicationExceptionProcessorConfigurer> registers,
                                        AbstractLayerModuleProcessor defaultProcessor) {
        if (CollectionUtils.isNotEmpty(registers)) {
            registers
                    .forEach(register -> {
                        register.registerApplicationProcessor(moduleApplicationExceptionManager, defaultProcessor);
                        register.registerModuleProcessor(moduleApplicationExceptionManager, defaultProcessor);
                        register.registerFunctionProcessor(moduleApplicationExceptionManager, defaultProcessor);
                    });
        }
    }

}
