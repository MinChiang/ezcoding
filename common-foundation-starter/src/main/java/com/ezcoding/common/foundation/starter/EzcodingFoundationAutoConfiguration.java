package com.ezcoding.common.foundation.starter;

import com.ezcoding.common.foundation.core.application.ApplicationLayerModule;
import com.ezcoding.common.foundation.core.application.FunctionLayerModule;
import com.ezcoding.common.foundation.core.application.ModuleLayerModule;
import com.ezcoding.common.foundation.core.enums.EnumMappableUtils;
import com.ezcoding.common.foundation.core.exception.BaseModuleExceptionBuilderFactory;
import com.ezcoding.common.foundation.core.exception.processor.*;
import com.ezcoding.common.foundation.core.message.builder.MessageBuildable;
import com.ezcoding.common.foundation.core.message.builder.MessageBuilder;
import com.ezcoding.common.foundation.core.message.handler.JsonMessageBuilderHandler;
import com.ezcoding.common.foundation.core.message.type.MessageTypeEnum;
import com.ezcoding.common.foundation.core.tools.uuid.IdProduceable;
import com.ezcoding.common.foundation.core.tools.uuid.OriginalUuidProducer;
import com.ezcoding.common.foundation.core.tools.uuid.SnowflakeIdProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.nio.charset.Charset;
import java.util.List;
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
@ConditionalOnProperty(prefix = "ezcoding.foundation", name = "enabled", havingValue = "true", matchIfMissing = true)
public class EzcodingFoundationAutoConfiguration implements InitializingBean {

    @Autowired
    private EzcodingFoundationConfigBean ezcodingFoundationConfigBean;

    /**
     * 注册默认分页类型
     */
    @Override
    public void afterPropertiesSet() {
        initApplicationMetadata();
        initEnumMapping();
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

    private void initEnumMapping() {

    }

    @Bean
    @ConditionalOnMissingBean(BaseModuleExceptionBuilderFactory.class)
    public BaseModuleExceptionBuilderFactory baseModuleExceptionBuilderFactory() {
        return new BaseModuleExceptionBuilderFactory();
    }

    @Primary
    @ConditionalOnMissingBean(SnowflakeIdProducer.class)
    @Bean("snowflakeUUIDProducer")
    public IdProduceable snowflakeUuidProducer() {
        Optional<MetadataConfigBean> metadata = Optional.ofNullable(ezcodingFoundationConfigBean.getMetadata());
        Long datacenterId = metadata
                .map(MetadataConfigBean::getDataCenterNo)
                .orElse(0L);
        Long machineId = metadata
                .map(MetadataConfigBean::getCategoryNo)
                .orElse(0L);
        return new SnowflakeIdProducer(datacenterId, machineId);
    }

    @ConditionalOnMissingBean(OriginalUuidProducer.class)
    @Bean("originalUUIDProducer")
    public IdProduceable originalUuidProducer() {
        return OriginalUuidProducer.getInstance();
    }

    @ConditionalOnMissingBean(MessageBuildable.class)
    @Bean
    public MessageBuilder messageBuilder(ObjectMapper objectMapper, IdProduceable producer) {
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

        Long categoryNo = ezcodingFoundationConfigBean.getMetadata().getCategoryNo();
        Long dataCenterNo = ezcodingFoundationConfigBean.getMetadata().getDataCenterNo();
        instance.setDefaultId(String.valueOf(dataCenterNo) + String.valueOf(categoryNo));

        instance.setDefaultReadMessageType(MessageTypeEnum.valueOf(message.getReadMessageType()));
        instance.setDefaultWriteMessageType(MessageTypeEnum.valueOf(message.getWriteMessageType()));

        return instance;
    }

    @ConditionalOnMissingBean(AbstractApplicationExceptionManager.class)
    @Bean("defaultExceptionManager")
    public ModuleApplicationExceptionManager moduleApplicationExceptionManager(@Autowired(required = false) @Qualifier(value = "defaultLayerModuleProcessor") AbstractLayerModuleProcessor defaultProcessor,
                                                                               @Autowired(required = false) List<ApplicationExceptionProcessorConfigurer> registers) {
        ModuleApplicationExceptionManager moduleApplicationExceptionManager = new ModuleApplicationExceptionManager(defaultProcessor);
        registerDefaultProcessor(moduleApplicationExceptionManager, defaultProcessor);
        if (registers != null && !registers.isEmpty()) {
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
                                        List<ApplicationExceptionProcessorConfigurer> registers,
                                        AbstractLayerModuleProcessor defaultProcessor) {
        if (registers != null && !registers.isEmpty()) {
            registers
                    .forEach(register -> {
                        register.registerApplicationProcessor(moduleApplicationExceptionManager, defaultProcessor);
                        register.registerModuleProcessor(moduleApplicationExceptionManager, defaultProcessor);
                        register.registerFunctionProcessor(moduleApplicationExceptionManager, defaultProcessor);
                    });
        }
    }

}
