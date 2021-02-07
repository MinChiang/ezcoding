package com.ezcoding.common.foundation.starter;

import com.ezcoding.common.foundation.core.application.ApplicationLayerModule;
import com.ezcoding.common.foundation.core.application.FunctionLayerModule;
import com.ezcoding.common.foundation.core.application.ModuleLayerModule;
import com.ezcoding.common.foundation.core.enums.*;
import com.ezcoding.common.foundation.core.exception.BaseModuleExceptionBuilderFactory;
import com.ezcoding.common.foundation.core.exception.processor.*;
import com.ezcoding.common.foundation.core.message.MessageFactory;
import com.ezcoding.common.foundation.core.tools.uuid.IdProduceable;
import com.ezcoding.common.foundation.core.tools.uuid.OriginalUuidProducer;
import com.ezcoding.common.foundation.core.tools.uuid.SnowflakeIdProducer;
import com.ezcoding.common.foundation.core.validation.PrependMessageInterpolator;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.validation.beanvalidation.LocaleContextMessageInterpolator;
import org.springframework.validation.beanvalidation.MessageSourceResourceBundleLocator;

import javax.validation.MessageInterpolator;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
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
@Import({LogConfiguration.class, LockConfiguration.class})
@EnableAspectJAutoProxy
public class EzcodingFoundationAutoConfiguration implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(EzcodingFoundationAutoConfiguration.class);

    @Autowired
    private EzcodingFoundationConfigBean ezcodingFoundationConfigBean;

    @Autowired(required = false)
    private List<FoundationConfigurer> foundationConfigurers;

    @Autowired(required = false)
    private IdProduceable idProduceable;

    /**
     * 注册默认分页类型
     */
    @Override
    public void afterPropertiesSet() throws ClassNotFoundException, IOException {
        initApplicationMetadata();
        initMessage();
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

    private void initMessage() {
        if (idProduceable != null) {
            MessageFactory.setSequenceNoProducer(idProduceable);
        }

        MessageConfigBean message = ezcodingFoundationConfigBean.getMessage();
        MessageFactory.setDefaultErrorResponseCode(message.getErrorResponseCode());
        MessageFactory.setDefaultSuccessResponseCode(message.getSuccessResponseCode());
        MessageFactory.setDefaultErrorResponseMessage(message.getErrorResponseMessage());
        MessageFactory.setDefaultSuccessResponseMessage(message.getSuccessResponseMessage());

        Long categoryNo = ezcodingFoundationConfigBean.getMetadata().getCategoryNo();
        Long dataCenterNo = ezcodingFoundationConfigBean.getMetadata().getDataCenterNo();
        MessageFactory.setAppId(String.valueOf(dataCenterNo) + categoryNo);
    }

    private void initEnumMapping() throws ClassNotFoundException, IOException {
        EnumConfigBean enums = ezcodingFoundationConfigBean.getEnums();

        List<EnumMappableStrategy> strategies = new ArrayList<>();
        if (foundationConfigurers != null && !foundationConfigurers.isEmpty()) {
            foundationConfigurers.forEach(conf -> conf.registerEnumStrategy(strategies));
        }

        //使用enum扫描策略
        if (enums.getStrategies() != null && !enums.getStrategies().isEmpty()) {
            //如果自定义配置不为空，则按照配置文件中的内容以及顺序进行配置
            String[] strategyNames = tokenizeToStringArray(enums.getStrategies(), ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
            for (String strategyName : strategyNames) {
                Class<EnumMappableStrategy> cls;
                try {
                    cls = (Class<EnumMappableStrategy>) Class.forName(strategyName);
                    EnumMappableStrategy enumMappableStrategy = cls.newInstance();
                    strategies.add(enumMappableStrategy);
                } catch (ClassNotFoundException e) {
                    LOGGER.error("can not find class: [{}]", strategyName);
                } catch (IllegalAccessException | InstantiationException e) {
                    LOGGER.error("can not instantiate class: [{}]", strategyName);
                }
            }
        } else {
            //如果自定义配置为空，则使用默认的配置
            strategies.add(new SimpleStrategy());
            strategies.add(new MappingStategy());
        }

        if (!strategies.isEmpty() && enums.getPackages() != null && !enums.getPackages().isEmpty()) {
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
                        ObjectEnumMappingInfo idToEnumMappingInfo = strategy.map((Class<? extends Enum<?>>) cls);
                        EnumMappableUtils.register(idToEnumMappingInfo);

                        Map<String, Enum<?>> stringToEnumMapping = new HashMap<>();
                        Map<Enum<?>, Object> enumToIdMapping = new HashMap<>();

                        for (Map.Entry<?, ? extends Enum<?>> entry : idToEnumMappingInfo.getMapping().entrySet()) {
                            stringToEnumMapping.put(entry.getKey().toString(), entry.getValue());
                            enumToIdMapping.put(entry.getValue(), entry.getKey());
                        }

                        //补充：加入String -> Enum映射关系
                        ObjectEnumMappingInfo stringToEnumMappingInfo = new ObjectEnumMappingInfo(new MappingPair(String.class, idToEnumMappingInfo.getMappingPair().getTargetClass()), stringToEnumMapping);
                        EnumMappableUtils.register(stringToEnumMappingInfo);

                        //补充：加入Enum -> id映射关系
                        EnumObjectMappingInfo enumObjectMappingInfo = new EnumObjectMappingInfo(new MappingPair(cls, idToEnumMappingInfo.getMappingPair().getSourceClass()), enumToIdMapping);
                        EnumMappableUtils.register(enumObjectMappingInfo);

                        count++;
                    }
                }
                if (count == 0) {
                    LOGGER.warn("can't find any strategy of enum [{}]", cls.getName());
                }
            }
        }

        //使用EnumHandleable自动注册映射关系
        if (enums.getHandlerPackages() != null && !enums.getHandlerPackages().isEmpty()) {
            String[] handlersPackage = tokenizeToStringArray(enums.getHandlerPackages(), ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
            Set<Class<?>> classes = new HashSet<>();
            for (String handlerPackage : handlersPackage) {
                classes.addAll(scanPackage(handlerPackage));
            }
            for (Class<?> cls : classes) {
                if (!EnumHandleable.class.isAssignableFrom(cls) || cls.isInterface()) {
                    continue;
                }
                try {
                    EnumMappableUtils.register((EnumHandleable) cls.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    LOGGER.error("can not instantiate EnumHandleable class: [{}]", cls.getName());
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
        Random random = new Random();
        Long datacenterId = metadata
                .map(MetadataConfigBean::getDataCenterNo)
                .orElse(random.nextLong());
        Long machineId = metadata
                .map(MetadataConfigBean::getCategoryNo)
                .orElse(random.nextLong());
        return new SnowflakeIdProducer(datacenterId, machineId);
    }

    @ConditionalOnMissingBean(OriginalUuidProducer.class)
    @Bean("originalUUIDProducer")
    public IdProduceable originalUuidProducer() {
        return OriginalUuidProducer.getInstance();
    }

    @ConditionalOnMissingBean(AbstractApplicationExceptionManager.class)
    @Bean("defaultExceptionManager")
    public ModuleApplicationExceptionManager moduleApplicationExceptionManager(@Autowired(required = false) @Qualifier(value = "defaultLayerModuleProcessor") AbstractLayerModuleProcessor defaultProcessor,
                                                                               @Autowired(required = false) List<FoundationConfigurer> registers) {
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
                                        List<FoundationConfigurer> registers,
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

    /**
     * 1.默认读取ValidationMessages.properties中的内容，注意此文件需要使用UTF-8进行编码
     * 2.开启快速校验模式，当遇到第一个校验失败时马上返回
     * 3.使用PrependMessageInterpolator默认消息插值模板，若想使用默认的消息插值方式，则在payload里面添加对应的Default.class
     *
     * @return 校验器
     */
    @Bean
    @ConditionalOnMissingBean
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

}
