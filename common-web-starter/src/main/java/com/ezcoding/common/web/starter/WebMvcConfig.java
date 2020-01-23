package com.ezcoding.common.web.starter;

import com.ezcoding.common.core.user.resolve.CompositeUserLoader;
import com.ezcoding.common.core.user.resolve.IUserLoadable;
import com.ezcoding.common.core.user.resolve.IUserProxyable;
import com.ezcoding.common.foundation.core.exception.processor.WebEmptyApplicationExceptionProcessor;
import com.ezcoding.common.foundation.core.message.builder.IMessageBuilder;
import com.ezcoding.common.web.error.ApplicationErrorController;
import com.ezcoding.common.web.error.ApplicationExceptionErrorAttributes;
import com.ezcoding.common.web.filter.ApplicationContextHolderFilter;
import com.ezcoding.common.web.filter.FilterConstants;
import com.ezcoding.common.web.filter.IApplicationContextValueFetchable;
import com.ezcoding.common.web.resolver.JsonMessageMethodProcessor;
import com.ezcoding.common.web.resolver.JsonPageMethodProcessor;
import com.ezcoding.common.web.resolver.JsonRequestMessageResolver;
import com.ezcoding.common.web.resolver.UserArgumentResolver;
import com.ezcoding.common.web.resolver.parameter.*;
import com.ezcoding.common.web.resolver.returnValue.IResponseMessageReturnValueResolvable;
import com.ezcoding.common.web.resolver.returnValue.ResponseAppHeadResolver;
import com.ezcoding.common.web.resolver.returnValue.ResponseMessageResolver;
import com.ezcoding.common.web.resolver.returnValue.ResponseSystemHeadResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-20 11:19
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private IMessageBuilder messageBuilder;
    @Autowired
    private List<HttpMessageConverter<?>> messageConverters;
    @Autowired(required = false)
    private List<IApplicationWebConfigurer> applicationWebConfigurers;
    @Autowired(required = false)
    private IUserProxyable userProxyable;

    private void registerParameterResolver(List<IRequestMessageParameterResolvable> resolvables) {
        resolvables.add(new ReqeustMessageResolver());
        resolvables.add(new RequestSystemHeadResolver());
        resolvables.add(new ResquestAppHeadResolver());
        resolvables.add(new DefaultRequestMessageResolver(objectMapper));
    }

    private void registerReturnValueResolvers(List<IResponseMessageReturnValueResolvable> resolvables) {
        resolvables.add(new ResponseMessageResolver());
        resolvables.add(new ResponseSystemHeadResolver());
        resolvables.add(new ResponseAppHeadResolver());
    }

    private IRequestMessageParameterResolvable defaultParameterResolver() {
        return new DefaultRequestMessageResolver(objectMapper);
    }

    private JsonRequestMessageResolver jsonRequestMessageResolver() {
        return new JsonRequestMessageResolver(messageBuilder);
    }

    private JsonMessageMethodProcessor jsonMessageMethodProcessor() {
        JsonMessageMethodProcessor jsonMessageMethodProcessor = new JsonMessageMethodProcessor(messageConverters, jsonRequestMessageResolver());

        List<IRequestMessageParameterResolvable> parameterResolvers = Lists.newArrayList();
        List<IResponseMessageReturnValueResolvable> returnValueResolvers = Lists.newArrayList();
        this.registerParameterResolver(parameterResolvers);
        this.registerReturnValueResolvers(returnValueResolvers);

        jsonMessageMethodProcessor.registerParameterResolvables(parameterResolvers);
        jsonMessageMethodProcessor.registerReturnValueResolvables(returnValueResolvers);

        return jsonMessageMethodProcessor;
    }

    private JsonPageMethodProcessor jsonPageMethodProcessor() {
        return new JsonPageMethodProcessor(jsonRequestMessageResolver());
    }

    private IUserLoadable compositeUserLoader() {
        List<IUserLoadable> loaders = Lists.newArrayList();
        Optional
                .ofNullable(this.applicationWebConfigurers)
                .ifPresent(configurers -> configurers.forEach(configurer -> configurer.registerUserLoaders(loaders)));
        return new CompositeUserLoader(loaders);
    }

    private UserArgumentResolver userArgumentResolver() {
        return new UserArgumentResolver(this.compositeUserLoader(), userProxyable);
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        returnValueHandlers.add(jsonMessageMethodProcessor());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(jsonMessageMethodProcessor());
        argumentResolvers.add(jsonPageMethodProcessor());
        argumentResolvers.add(userArgumentResolver());
    }

    @Bean
    public FilterRegistrationBean<ApplicationContextHolderFilter> applicationContextHolderFilter() {
        List<IApplicationContextValueFetchable> fetchers = Lists.newLinkedList();
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
    @Bean(value = {"defaultLayerModuleProcessor", "webEmptyApplicationExceptionProcessor"})
    public WebEmptyApplicationExceptionProcessor webEmptyApplicationExceptionProcessor() {
        return new WebEmptyApplicationExceptionProcessor();
    }

    @Configuration
    @AutoConfigureAfter(RequestMappingHandlerAdapter.class)
    public static class WebLastConfig implements InitializingBean, BeanFactoryAware {

        private BeanFactory beanFactory;

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

        @Override
        public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
            this.beanFactory = beanFactory;
        }

    }

}
