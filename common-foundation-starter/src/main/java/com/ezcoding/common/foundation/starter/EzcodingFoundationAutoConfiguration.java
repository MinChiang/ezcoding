package com.ezcoding.common.foundation.starter;

import com.ezcoding.common.foundation.core.application.ApplicationLayerModule;
import com.ezcoding.common.foundation.core.application.FunctionLayerModule;
import com.ezcoding.common.foundation.core.application.ModuleLayerModule;
import com.ezcoding.common.foundation.core.enums.EnumMappableStrategy;
import com.ezcoding.common.foundation.core.enums.EnumMappableUtils;
import com.ezcoding.common.foundation.core.exception.BaseModuleExceptionBuilderFactory;
import com.ezcoding.common.foundation.core.exception.processor.*;
import com.ezcoding.common.foundation.core.message.builder.MessageBuildable;
import com.ezcoding.common.foundation.core.message.builder.MessageFactory;
import com.ezcoding.common.foundation.core.message.handler.JsonMessageBuilderHandler;
import com.ezcoding.common.foundation.core.message.MessageTypeEnum;
import com.ezcoding.common.foundation.core.tools.uuid.IdProduceable;
import com.ezcoding.common.foundation.core.tools.uuid.OriginalUuidProducer;
import com.ezcoding.common.foundation.core.tools.uuid.SnowflakeIdProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

import static com.ezcoding.common.foundation.core.exception.ModuleConstants.DEFAULT_APPLICATION_LAYER_MODULE;
import static com.ezcoding.common.foundation.core.exception.ModuleConstants.DEFAULT_MODULE_LAYER_MODULE;
import static org.springframework.util.StringUtils.tokenizeToStringArray;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-11 10:47
 */
@Configuration
@EnableConfigurationProperties(EzcodingFoundationConfigBean.class)
@ConditionalOnProperty(prefix = "ezcoding.foundation", name = "enabled", havingValue = "true", matchIfMissing = true)
public class EzcodingFoundationAutoConfiguration implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(EzcodingFoundationAutoConfiguration.class);

    @Autowired
    private EzcodingFoundationConfigBean ezcodingFoundationConfigBean;

    @Autowired(required = false)
    private List<ApplicationEnumConfigurer> enumConfigurers;

    /**
     * 注册默认分页类型
     */
    @Override
    public void afterPropertiesSet() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
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

    private void initEnumMapping() throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {
        EnumConfigBean enums = ezcodingFoundationConfigBean.getEnums();

        List<EnumMappableStrategy> strategies = new ArrayList<>();
        if (enumConfigurers != null && !enumConfigurers.isEmpty()) {
            enumConfigurers.forEach(conf -> conf.registerEnumStrategy(strategies));
        }
        if (enums.getStrategies() != null && !enums.getStrategies().isEmpty()) {
            String[] strategyNames = tokenizeToStringArray(enums.getStrategies(), ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
            for (String strategyName : strategyNames) {
                Class<EnumMappableStrategy> cls = (Class<EnumMappableStrategy>) Class.forName(strategyName);
                strategies.add(cls.newInstance());
            }
        }

        if (strategies.isEmpty()) {
            return;
        }
        if (enums.getPackages() != null && !enums.getPackages().isEmpty()) {
            String[] typeEnumsPackageArray = tokenizeToStringArray(enums.getPackages(), ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
            Set<Class<?>> classes = new HashSet<>();
            for (String typePackage : typeEnumsPackageArray) {
                classes.addAll(scanPackage(typePackage));
            }

            int count;
            for (Class<?> cls : classes) {
                if (!cls.isEnum()) {
                    continue;
                }
                count = 0;
                for (EnumMappableStrategy strategy : strategies) {
                    if (strategy.canMap((Class<? extends Enum<?>>) cls)) {
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("register strategy [{}] of enum [{}]", strategy.getClass().getName(), cls.getName());
                        }
                        EnumMappableUtils.register(strategy.map((Class<? extends Enum<?>>) cls));
                        count++;
                    }
                }
                if (count == 0) {
                    if (LOGGER.isWarnEnabled()) {
                        LOGGER.warn("can't find any strategy of enum [{}]", cls.getName());
                    }
                }
            }
        }
    }

    private Set<Class<?>> scanPackage(String typePackage) throws IOException, ClassNotFoundException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
        String pkg = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath(typePackage) + "/*.class";
        Set<Class<?>> set = new HashSet<>();
        Resource[] resources = resolver.getResources(pkg);
        if (resources != null && resources.length > 0) {
            MetadataReader metadataReader;
            for (Resource resource : resources) {
                if (resource.isReadable()) {
                    metadataReader = metadataReaderFactory.getMetadataReader(resource);
                    set.add(Class.forName(metadataReader.getClassMetadata().getClassName(), false, Thread.currentThread().getContextClassLoader()));
                }
            }
        }
        return set;
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
    public MessageFactory messageBuilder(ObjectMapper objectMapper, IdProduceable producer) {
        JsonMessageBuilderHandler jsonMessageBuilderHandler = new JsonMessageBuilderHandler();
        jsonMessageBuilderHandler.setObjectMapper(objectMapper);
        MessageFactory.configHandler(MessageTypeEnum.JSON, jsonMessageBuilderHandler);
        MessageFactory.setIdProducer(producer);

        MessageConfigBean message = ezcodingFoundationConfigBean.getMessage();
        MessageFactory instance = MessageFactory.getInstance();
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
