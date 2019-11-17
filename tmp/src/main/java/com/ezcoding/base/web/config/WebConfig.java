package com.ezcoding.base.web.config;

import com.ezcoding.base.web.extend.spring.convertor.StringToEnumConverterFactory;
import com.ezcoding.base.web.extend.spring.error.ApplicationErrorController;
import com.ezcoding.base.web.extend.spring.filter.*;
import com.ezcoding.base.web.extend.spring.resolver.CurrentUserResolver;
import com.ezcoding.base.web.extend.spring.resolver.JsonMessageMethodProcessor;
import com.ezcoding.base.web.extend.spring.resolver.JsonPageMethodProcessor;
import com.ezcoding.base.web.extend.spring.resolver.JsonRequestMessageResolver;
import com.ezcoding.base.web.extend.spring.security.authentication.FeignUserLoader;
import com.ezcoding.base.web.extend.spring.security.authentication.IUserLoadable;
import com.ezcoding.base.web.extend.spring.security.authentication.UserProxy;
import com.ezcoding.base.web.extend.spring.security.authentication.UserResolver;
import com.ezcoding.common.foundation.core.message.builder.IMessageBuilder;
import com.ezcoding.common.foundation.core.tools.jwt.AuthSettings;
import com.ezcoding.sdk.account.user.api.UserFeignClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-16 22:29
 */
@Configuration
public class WebConfig implements WebMvcConfigurer, BeanFactoryAware {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private IMessageBuilder messageBuilder;
    @Autowired
    private List<HttpMessageConverter<?>> messageConverters;
    @Autowired
    private JsonRequestMessageResolver requestMessageResolver;
    @Autowired
    private AuthSettings authSettings;
    @Autowired
    private Validator validator;

    private BeanFactory beanFactory;

    @Bean
    public JsonMessageMethodProcessor jsonMessageMethodProcessor() {
        JsonMessageMethodProcessor jsonMessageMethodProcessor = new JsonMessageMethodProcessor(messageConverters);
        jsonMessageMethodProcessor.setMessageBuilder(messageBuilder);
        jsonMessageMethodProcessor.setObjectMapper(objectMapper);
        jsonMessageMethodProcessor.setRequestMessageResolver(requestMessageResolver);
        jsonMessageMethodProcessor.setOpenObjectValidate(true);
        jsonMessageMethodProcessor.setValidator(validator);
        return jsonMessageMethodProcessor;
    }

    @Bean
    public JsonPageMethodProcessor jsonPageMethodProcessor() {
        JsonPageMethodProcessor jsonPageMethodProcessor = new JsonPageMethodProcessor();
        jsonPageMethodProcessor.setMessageBuilder(messageBuilder);
        jsonPageMethodProcessor.setRequestMessageResolver(requestMessageResolver);
        return jsonPageMethodProcessor;
    }

    @Bean
    public CurrentUserResolver currentUserResolver() {
        return new CurrentUserResolver();
    }

    @Bean
    public IApplicationContextValueFetchable userTokenContextSettable() {
        return new UserTokenContextSettable(this.authSettings.getHeader());
    }

    @Bean
    public FilterRegistrationBean<ApplicationContextHolderFilter> applicationContextHolderFilter(List<IApplicationContextValueFetchable> settables) {
        ApplicationContextHolderFilter applicationContextHolderFilter = new ApplicationContextHolderFilter(settables);
        FilterRegistrationBean<ApplicationContextHolderFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(applicationContextHolderFilter);
        registrationBean.setName("applicationContextHolderFilter");
        registrationBean.setOrder(FilterOrderConstants.APPLICATION_CONTEXT_HOLDER);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<SeataXidInjectFilter> seataXidInjectFilter() {
        SeataXidInjectFilter seataXidInjectFilter = new SeataXidInjectFilter();
        FilterRegistrationBean<SeataXidInjectFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(seataXidInjectFilter);
        registrationBean.setName("seataXidInjectFilter");
        registrationBean.setOrder(FilterOrderConstants.SEATA_XID_INJECT);
        return registrationBean;
    }

    @Bean
    public ApplicationErrorController basicErrorController(ErrorAttributes errorAttributes, ServerProperties serverProperties, List<ErrorViewResolver> errorViewResolvers) {
        return new ApplicationErrorController(errorAttributes, serverProperties.getError(), errorViewResolvers);
    }

    @Bean
    @ConditionalOnMissingBean(IUserLoadable.class)
    public IUserLoadable feighUserLoader(UserFeignClient userFeignClient) {
        return new FeignUserLoader(userFeignClient);
    }

    @Bean
    public UserResolver userResolver(IUserLoadable loader) {
        UserProxy.configLoader(loader);
        return UserResolver.getInstance();
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        //将long转化为string，解决序列化long精度丢失的问题
        return new Jackson2ObjectMapperBuilderCustomizer() {
            @Override
            public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
                jacksonObjectMapperBuilder.serializerByType(Long.TYPE, ToStringSerializer.instance);
                jacksonObjectMapperBuilder.serializerByType(long.class, ToStringSerializer.instance);
            }
        };
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        returnValueHandlers.add(jsonMessageMethodProcessor());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(jsonMessageMethodProcessor());
        argumentResolvers.add(jsonPageMethodProcessor());
        argumentResolvers.add(currentUserResolver());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new StringToEnumConverterFactory());
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
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

    @Configuration
    @AutoConfigureAfter(RequestMappingHandlerAdapter.class)
    class WebLastConfig implements InitializingBean {

        @Override
        public void afterPropertiesSet() {
            RequestMappingHandlerAdapter adapter = beanFactory.getBean(RequestMappingHandlerAdapter.class);

            List<HandlerMethodReturnValueHandler> customReturnValueHandlers = adapter.getCustomReturnValueHandlers();
            List<HandlerMethodReturnValueHandler> returnValueHandlers = adapter.getReturnValueHandlers();
            if (CollectionUtils.isNotEmpty(customReturnValueHandlers) && CollectionUtils.isNotEmpty(returnValueHandlers)) {
                //returnValueHandlers为不可变对象，需要重新设置一个新的list进行设置
                List<HandlerMethodReturnValueHandler> dest = new ArrayList<>(returnValueHandlers);
                dest.removeAll(customReturnValueHandlers);
                dest.addAll(0, customReturnValueHandlers);
                adapter.setReturnValueHandlers(dest);
            }

            List<HandlerMethodArgumentResolver> customArgumentResolvers = adapter.getCustomArgumentResolvers();
            List<HandlerMethodArgumentResolver> argumentResolvers = adapter.getArgumentResolvers();
            if (CollectionUtils.isNotEmpty(customArgumentResolvers) && CollectionUtils.isNotEmpty(argumentResolvers)) {
                List<HandlerMethodArgumentResolver> dest = new ArrayList<>(argumentResolvers);
                dest.removeAll(customArgumentResolvers);
                dest.addAll(0, customArgumentResolvers);
                adapter.setArgumentResolvers(dest);
            }
        }

    }

}
