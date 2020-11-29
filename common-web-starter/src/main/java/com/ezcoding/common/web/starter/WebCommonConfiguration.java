package com.ezcoding.common.web.starter;

import com.ezcoding.common.foundation.core.enums.EnumMappableStrategy;
import com.ezcoding.common.foundation.core.enums.EnumMappableUtils;
import com.ezcoding.common.foundation.core.enums.JacksonStrategy;
import com.ezcoding.common.foundation.core.enums.MappingPair;
import com.ezcoding.common.foundation.core.exception.processor.WebDefaultApplicationExceptionProcessor;
import com.ezcoding.common.foundation.core.exception.processor.WebExceptionBuilderFactory;
import com.ezcoding.common.foundation.starter.ApplicationEnumConfigurer;
import com.ezcoding.common.foundation.starter.EzcodingFoundationConfigBean;
import com.ezcoding.common.web.convertor.StandardEnumDeserializer;
import com.ezcoding.common.web.convertor.StandardEnumSerializer;
import com.ezcoding.common.web.error.ApplicationErrorController;
import com.ezcoding.common.web.error.ApplicationExceptionErrorAttributes;
import com.ezcoding.common.web.filter.ApplicationContextHolderFilter;
import com.ezcoding.common.web.filter.ApplicationContextValueFetchable;
import com.ezcoding.common.web.filter.FilterConstants;
import com.ezcoding.common.web.user.RemoteUserProxy;
import com.ezcoding.common.web.user.UserProxyable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

import static com.ezcoding.common.foundation.starter.EnumConfigBean.JACKSON;
import static org.springframework.util.StringUtils.tokenizeToStringArray;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-27 10:43
 */
@Configuration
@EnableConfigurationProperties(EzcodingWebConfigBean.class)
public class WebCommonConfiguration implements InitializingBean, ApplicationEnumConfigurer {

    @Autowired(required = false)
    private List<ApplicationWebConfigurer> applicationWebConfigurers;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private EzcodingFoundationConfigBean ezcodingFoundationConfigBean;

    @Override
    public void afterPropertiesSet() {
        WebExceptionBuilderFactory.setMessageSource(messageSource);
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer defaultJackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> {
            //将long转化为string，解决序列化long精度丢失的问题
            jacksonObjectMapperBuilder.serializerByType(long.class, ToStringSerializer.instance);
            jacksonObjectMapperBuilder.serializerByType(Long.TYPE, ToStringSerializer.instance);
            jacksonObjectMapperBuilder.serializerByType(Long.class, ToStringSerializer.instance);

            jacksonObjectMapperBuilder.serializationInclusion(JsonInclude.Include.NON_NULL);
            jacksonObjectMapperBuilder.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            jacksonObjectMapperBuilder.featuresToEnable(MapperFeature.PROPAGATE_TRANSIENT_MARKER);
            jacksonObjectMapperBuilder.locale(Locale.getDefault());
        };
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer enumMappingJackson2ObjectMapperBuilderCustomizer() {
        String configContexts = ezcodingFoundationConfigBean.getEnums().getConfigContexts();
        if (configContexts == null || configContexts.isEmpty()) {
            return null;
        }

        String[] contexts = tokenizeToStringArray(configContexts, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
        for (String context : contexts) {
            if (Objects.equals(context, JACKSON)) {
                return jacksonObjectMapperBuilder -> {
                    Set<MappingPair> enumToObjectMapping = EnumMappableUtils.acquireEnumToObjectMapping();
                    for (MappingPair mappingPair : enumToObjectMapping) {
                        jacksonObjectMapperBuilder.serializers(new StandardEnumSerializer((Class<Enum<?>>) mappingPair.getSourceClass()));
                    }
                    Set<MappingPair> objectToEnumMapping = EnumMappableUtils.acquireObjectToEnumMapping();
                    for (MappingPair mappingPair : objectToEnumMapping) {
                        jacksonObjectMapperBuilder.deserializers(new StandardEnumDeserializer(mappingPair.getSourceClass(), (Class<Enum<?>>) mappingPair.getTargetClass()));
                    }
                };
            }
        }
        return null;
    }

    @Bean
    public FilterRegistrationBean<ApplicationContextHolderFilter> applicationContextHolderFilter() {
        List<ApplicationContextValueFetchable<?>> fetchers = new LinkedList<>();
        Optional
                .ofNullable(this.applicationWebConfigurers)
                .ifPresent(configures -> configures.forEach(configurer -> configurer.registerApplicationContextFetchers(fetchers)));
        ApplicationContextHolderFilter applicationContextHolderFilter = new ApplicationContextHolderFilter(fetchers);
        FilterRegistrationBean<ApplicationContextHolderFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(applicationContextHolderFilter);
        registrationBean.setName(FilterConstants.Name.APPLICATION_CONTEXT_HOLDER_NAME);
        registrationBean.setOrder(FilterConstants.Order.APPLICATION_CONTEXT_HOLDER_ORDER);
        return registrationBean;
    }

    @Bean
    public ApplicationErrorController basicErrorController(ErrorAttributes errorAttributes, ServerProperties serverProperties, List<ErrorViewResolver> errorViewResolvers) {
        return new ApplicationErrorController(errorAttributes, serverProperties.getError(), errorViewResolvers);
    }

    @Bean
    public ApplicationExceptionErrorAttributes applicationExceptionErrorAttributes() {
        return new ApplicationExceptionErrorAttributes();
    }

    /**
     * 此类覆盖common-starter中的emptyApplicationExceptionProcessor
     *
     * @return web容器空执行器
     */
    @Bean(value = {"defaultLayerModuleProcessor", "webDefaultApplicationExceptionProcessor"})
    public WebDefaultApplicationExceptionProcessor webDefaultApplicationExceptionProcessor() {
        return new WebDefaultApplicationExceptionProcessor();
    }

    @Bean
    @ConditionalOnMissingBean(UserProxyable.class)
    public UserProxyable userProxy() {
        return new RemoteUserProxy();
    }

    @Override
    public void registerEnumStrategy(List<EnumMappableStrategy> strategies) {
        strategies.add(new JacksonStrategy());
    }

}
