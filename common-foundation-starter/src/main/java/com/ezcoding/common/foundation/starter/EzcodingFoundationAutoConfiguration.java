package com.ezcoding.common.foundation.starter;

import com.ezcoding.common.foundation.core.application.ApplicationLayerModule;
import com.ezcoding.common.foundation.core.application.FunctionLayerModule;
import com.ezcoding.common.foundation.core.application.ModuleLayerModule;
import com.ezcoding.common.foundation.core.enums.*;
import com.ezcoding.common.foundation.core.exception.BaseModuleExceptionBuilderFactory;
import com.ezcoding.common.foundation.core.exception.processor.*;
import com.ezcoding.common.foundation.core.log.*;
import com.ezcoding.common.foundation.core.message.MessageFactory;
import com.ezcoding.common.foundation.core.message.MessageTypeEnum;
import com.ezcoding.common.foundation.core.message.handler.JsonMessageBuilderHandler;
import com.ezcoding.common.foundation.core.message.io.MessageIoFactory;
import com.ezcoding.common.foundation.core.tools.uuid.IdProduceable;
import com.ezcoding.common.foundation.core.tools.uuid.OriginalUuidProducer;
import com.ezcoding.common.foundation.core.tools.uuid.SnowflakeIdProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
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
import java.lang.reflect.Method;
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

    @Autowired(required = false)
    private IdProduceable idProduceable;

    /**
     * 注册默认分页类型
     */
    @Override
    public void afterPropertiesSet() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
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
        MessageFactory.setAppId(String.valueOf(dataCenterNo) + String.valueOf(categoryNo));
    }

    private void initEnumMapping() throws ClassNotFoundException, IOException {
        EnumConfigBean enums = ezcodingFoundationConfigBean.getEnums();

        List<EnumMappableStrategy> strategies = new ArrayList<>();
        if (enumConfigurers != null && !enumConfigurers.isEmpty()) {
            enumConfigurers.forEach(conf -> conf.registerEnumStrategy(strategies));
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
            strategies.add(new JacksonStrategy());
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

    @ConditionalOnMissingBean(MessageIoFactory.class)
    @ConditionalOnClass()
    @Bean
    public MessageIoFactory messageIoFactory(ObjectMapper objectMapper) {
        MessageConfigBean message = ezcodingFoundationConfigBean.getMessage();
        JsonMessageBuilderHandler jsonMessageBuilderHandler = new JsonMessageBuilderHandler();
        jsonMessageBuilderHandler.setObjectMapper(objectMapper);
        MessageIoFactory.configHandler(MessageTypeEnum.JSON, jsonMessageBuilderHandler);

        MessageIoFactory instance = MessageIoFactory.getInstance();
        instance.setDefaultMessageBuilder(jsonMessageBuilderHandler);
        instance.setDefaultReadCharset(Charset.forName(message.getReadCharset()));
        instance.setDefaultWriteCharset(Charset.forName(message.getWriteCharset()));

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

    @ConditionalOnProperty(prefix = "ezcoding.foundation.log", name = "enabled", havingValue = "true", matchIfMissing = true)
    @Aspect
    public static class ServiceLogAspect {

        @Autowired
        private EzcodingFoundationConfigBean ezcodingFoundationConfigBean;

        @Autowired
        private ServiceLoggerFactory serviceLoggerFactory;

        @Pointcut("@annotation(com.ezcoding.common.foundation.core.log.ServiceLog)")
        public void doLog() {

        }

        @Around(value = "doLog()")
        public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
            //实际调用的对象
            Object target = proceedingJoinPoint.getTarget();
            //获取参数
            MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
            Class<?>[] parameterTypes = signature.getParameterTypes();
            Object[] args = proceedingJoinPoint.getArgs();
            //获取方法
            Method method = target.getClass().getMethod(proceedingJoinPoint.getSignature().getName(), parameterTypes);

            ServiceLog serviceLog = method.getAnnotation(ServiceLog.class);
            ServiceLogger serviceLogger = serviceLoggerFactory.create(serviceLog, target, method);

            //打印入参
            serviceLogger.logBefore(args);

            //执行业务
            Object result = proceedingJoinPoint.proceed();

            //打印出参
            serviceLogger.logAfter(result);
            return result;
        }

        @Bean
        @ConditionalOnMissingBean
        public ServiceLoggerFactory serviceLoggerFactory() {
            LogConfigBean logConfig = ezcodingFoundationConfigBean.getLog();

            List<LogFormatter> formatters = getInstances(logConfig.getFormatterClass());
            List<LogParser> parsers = getInstances(logConfig.getParserClass());
            List<LogPrinter> printers = getInstances(logConfig.getPrinterClass());

            ServiceLoggerFactory serviceLoggerFactory = ServiceLoggerFactory.defaultFactory();
            serviceLoggerFactory.addLogFormatters(formatters);
            serviceLoggerFactory.addLogParsers(parsers);
            serviceLoggerFactory.addLogPrinters(printers);
            serviceLoggerFactory.setDefaultLogFormatter(getInstance(logConfig.getDefaultFormatterClass()));
            serviceLoggerFactory.setDefaultLogParser(getInstance(logConfig.getDefaultParserClass()));
            serviceLoggerFactory.setDefaultLogPrinter(getInstance(logConfig.getDefaultPrinterClass()));

            return serviceLoggerFactory;
        }

        /**
         * 实例化
         *
         * @param classStrings 列表
         * @param <T>          类型
         * @return 实例
         */
        private <T> List<T> getInstances(List<String> classStrings) {
            List<T> result = new ArrayList<>();
            for (String classString : classStrings) {
                result.add(getInstance(classString));
            }
            return result;
        }

        /**
         * 实例化
         *
         * @param classString 列表
         * @param <T>         类型
         * @return 实例
         */
        private <T> T getInstance(String classString) {
            try {
                Class<T> cls = (Class<T>) Class.forName(classString);
                return cls.newInstance();
            } catch (Exception e) {
                LOGGER.error("unable to find or instance class : {}", classString);
            }
            return null;
        }

    }

}
