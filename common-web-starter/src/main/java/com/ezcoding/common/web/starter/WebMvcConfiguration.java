package com.ezcoding.common.web.starter;

import com.ezcoding.common.core.user.EmptyUserLoader;
import com.ezcoding.common.core.user.UserLoadable;
import com.ezcoding.common.foundation.core.enums.EnumMappableUtils;
import com.ezcoding.common.foundation.core.enums.MappingPair;
import com.ezcoding.common.foundation.core.exception.processor.AbstractApplicationExceptionManager;
import com.ezcoding.common.foundation.core.exception.processor.ApplicationExceptionResolver;
import com.ezcoding.common.foundation.core.validation.PrependMessageInterpolator;
import com.ezcoding.common.foundation.starter.EzcodingFoundationAutoConfiguration;
import com.ezcoding.common.foundation.starter.EzcodingFoundationConfigBean;
import com.ezcoding.common.web.resolver.result.*;
import com.ezcoding.common.web.util.ObjectMapperUtils;
import com.ezcoding.common.web.convertor.EnumToObjectConverter;
import com.ezcoding.common.web.convertor.ObjectToEnumConverter;
import com.ezcoding.common.web.jwt.AuthSettings;
import com.ezcoding.common.web.resolver.StandardMessageMethodProcessor;
import com.ezcoding.common.web.resolver.UserArgumentResolver;
import com.ezcoding.common.web.resolver.parameter.*;
import com.ezcoding.common.web.user.CompositeUserLoader;
import com.ezcoding.common.web.user.UserProxyable;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.validation.beanvalidation.LocaleContextMessageInterpolator;
import org.springframework.validation.beanvalidation.MessageSourceResourceBundleLocator;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.validation.MessageInterpolator;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

import static com.ezcoding.common.foundation.starter.EnumConfigBean.SPRING_CONVERTER;
import static org.springframework.util.StringUtils.tokenizeToStringArray;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-20 11:19
 */
@Configuration
@AutoConfigureAfter(value = EzcodingFoundationAutoConfiguration.class)
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private HttpMessageConverters httpMessageConverters;
    @Autowired(required = false)
    private List<ApplicationWebConfigurer> applicationWebConfigurers;
    @Autowired
    private UserProxyable userProxyable;
    @Autowired
    private EzcodingWebConfigBean ezcodingWebConfigBean;
    @Autowired
    @Qualifier("defaultExceptionManager")
    private AbstractApplicationExceptionManager defaultExceptionManager;
    @Autowired
    private StandardMessageMethodProcessor standardMessageMethodProcessor;
    @Autowired
    private EzcodingFoundationConfigBean ezcodingFoundationConfigBean;

    private void registerDefaultParameterResolver(List<RequestMessageParameterResolvable> resolvables) {
        resolvables.add(new ReqeustMessageResolver());
        resolvables.add(new RequestSystemHeadResolver());
        resolvables.add(new ResquestAppHeadResolver());
        resolvables.add(new PageInfoRequestMessageResolver());
        resolvables.add(new DefaultRequestMessageResolver(ObjectMapperUtils.message()));
    }

    private void registerDefaultReturnValueResolvers(List<ResponseMessageReturnValueResolvable> resolvables) {
        resolvables.add(new ResponseMessageResolver());
        resolvables.add(new PageInfoResolver());
    }

    @Bean
    public StandardMessageMethodProcessor jsonMessageMethodProcessor() {
        StandardMessageMethodProcessor standardMessageMethodProcessor = new StandardMessageMethodProcessor(httpMessageConverters.getConverters());

        List<RequestMessageParameterResolvable> parameterResolvers = new ArrayList<>();
        List<ResponseMessageReturnValueResolvable> returnValueResolvers = new ArrayList<>();
        this.registerDefaultParameterResolver(parameterResolvers);
        this.registerDefaultReturnValueResolvers(returnValueResolvers);

        Optional
                .ofNullable(this.applicationWebConfigurers)
                .ifPresent(configures -> configures.forEach(configurer -> {
                    configurer.registerRequestMessageParameterResolvers(parameterResolvers);
                    configurer.registerResponseMessageReturnValueResolvers(returnValueResolvers);
                }));

        standardMessageMethodProcessor.registerParameterResolvables(parameterResolvers);
        standardMessageMethodProcessor.registerReturnValueResolvables(returnValueResolvers);

        return standardMessageMethodProcessor;
    }

    /**
     * 1.默认读取ValidationMessages.properties中的内容，注意此文件需要使用UTF-8进行编码
     * 2.开启快速校验模式，当遇到第一个校验失败时马上返回
     * 3.使用PrependMessageInterpolator默认消息插值模板，若想使用默认的消息插值方式，则在payload里面添加对应的Default.class
     *
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

    private UserLoadable compositeUserLoader() {
        List<UserLoadable> loaders = new ArrayList<>();
        Optional<List<ApplicationWebConfigurer>> applicationWebConfigurers = Optional.ofNullable(this.applicationWebConfigurers);
        applicationWebConfigurers.ifPresent(configurers -> {
            configurers.forEach(configurer -> configurer.configUserLoaders(loaders));
            if (loaders.isEmpty()) {
                configurers.forEach(configurer -> configurer.registerUserLoaders(loaders));
            }
        });
        //注册默认的用户加载器
        loaders.add(new EmptyUserLoader());
        return new CompositeUserLoader(loaders);
    }

    private UserArgumentResolver userArgumentResolver() {
        return new UserArgumentResolver(compositeUserLoader(), userProxyable);
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        returnValueHandlers.add(this.standardMessageMethodProcessor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(this.standardMessageMethodProcessor);
        argumentResolvers.add(userArgumentResolver());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        AuthSettings authSettings = ezcodingWebConfigBean.getAuthSettings();
        //允许跨域标识
        registry
                .addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*")
                //当进行跨域请求时，前端APP只能获取默认的几个header，此时需要设置Access-Control-Expose-Headers的值
                .exposedHeaders(authSettings.getHeader())
                .allowCredentials(true);
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        if (defaultExceptionManager != null) {
            ApplicationExceptionResolver applicationExceptionResolver = new ApplicationExceptionResolver(defaultExceptionManager);
            resolvers.add(0, applicationExceptionResolver);
        }
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        String configContexts = ezcodingFoundationConfigBean.getEnums().getConfigContexts();
        if (configContexts == null || configContexts.isEmpty()) {
            return;
        }

        String[] contexts = tokenizeToStringArray(configContexts, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
        for (String context : contexts) {
            if (Objects.equals(context, SPRING_CONVERTER)) {
                Set<MappingPair> objectToEnumMapping = EnumMappableUtils.acquireObjectToEnumMapping();
                for (MappingPair mappingPair : objectToEnumMapping) {
                    registry.addConverter(new ObjectToEnumConverter(mappingPair.getSourceClass(), (Class<? extends Enum<?>>) mappingPair.getTargetClass()));
                }

                Set<MappingPair> enumToObjectMapping = EnumMappableUtils.acquireEnumToObjectMapping();
                for (MappingPair mappingPair : enumToObjectMapping) {
                    registry.addConverter(new EnumToObjectConverter((Class<? extends Enum<?>>) mappingPair.getSourceClass(), mappingPair.getTargetClass()));
                }
            }
        }
    }

    /**
     * 解决循环依赖的问题，调整自定义returnValueHandlers和默认returnValueHandlers的位置
     */
    @Configuration
    @AutoConfigureAfter(RequestMappingHandlerAdapter.class)
    public static class WebLastConfig implements InitializingBean, BeanFactoryAware {

        private BeanFactory beanFactory;

        @Override
        public void afterPropertiesSet() {
            RequestMappingHandlerAdapter adapter = beanFactory.getBean(RequestMappingHandlerAdapter.class);

            List<HandlerMethodReturnValueHandler> customReturnValueHandlers = adapter.getCustomReturnValueHandlers();
            List<HandlerMethodReturnValueHandler> returnValueHandlers = adapter.getReturnValueHandlers();
            if (customReturnValueHandlers != null && !customReturnValueHandlers.isEmpty() && returnValueHandlers != null && !returnValueHandlers.isEmpty()) {
                //returnValueHandlers为不可变对象，需要重新设置一个新的list进行设置
                List<HandlerMethodReturnValueHandler> dest = new ArrayList<>(returnValueHandlers);
                dest.removeAll(customReturnValueHandlers);
                dest.addAll(0, customReturnValueHandlers);
                adapter.setReturnValueHandlers(dest);
            }

            List<HandlerMethodArgumentResolver> customArgumentResolvers = adapter.getCustomArgumentResolvers();
            List<HandlerMethodArgumentResolver> argumentResolvers = adapter.getArgumentResolvers();
            if (customArgumentResolvers != null && !customArgumentResolvers.isEmpty() && argumentResolvers != null && !argumentResolvers.isEmpty()) {
                List<HandlerMethodArgumentResolver> dest = new ArrayList<>(argumentResolvers);
                dest.removeAll(customArgumentResolvers);
                dest.addAll(0, customArgumentResolvers);
                adapter.setArgumentResolvers(dest);
            }
        }

        @Override
        public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
            this.beanFactory = beanFactory;
        }

    }

    /**
     * 为了请求对应的后台而做的鉴权请求模板
     * @return 请求模板
     */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
//        MappingJackson2HttpMessageConverter messageConverter =
//                restTemplate
//                        .getMessageConverters()
//                        .stream()
//                        .filter(MappingJackson2HttpMessageConverter.class::isInstance)
//                        .map(MappingJackson2HttpMessageConverter.class::cast)
//                        .forEach();
//        messageConverter.setObjectMapper();
        return restTemplate;
    }

}
