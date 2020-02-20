package com.ezcoding.common.web.starter;

import com.ezcoding.common.foundation.core.exception.processor.WebDefaultApplicationExceptionProcessor;
import com.ezcoding.common.foundation.core.exception.processor.WebExceptionBuilderFactory;
import com.ezcoding.common.web.error.ApplicationErrorController;
import com.ezcoding.common.web.error.ApplicationExceptionErrorAttributes;
import com.ezcoding.common.web.filter.ApplicationContextHolderFilter;
import com.ezcoding.common.web.filter.FilterConstants;
import com.ezcoding.common.web.filter.IApplicationContextValueFetchable;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-27 10:43
 */
@Configuration
@EnableConfigurationProperties(EzcodingWebConfigBean.class)
public class WebCommonConfig implements InitializingBean {

    @Autowired(required = false)
    private List<IApplicationWebConfigurer> applicationWebConfigurers;
    @Autowired
    private MessageSource messageSource;

    @Override
    public void afterPropertiesSet() {
        WebExceptionBuilderFactory.setMessageSource(messageSource);
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        //将long转化为string，解决序列化long精度丢失的问题
        return jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder.serializerByType(Long.TYPE, ToStringSerializer.instance);
            jacksonObjectMapperBuilder.serializerByType(long.class, ToStringSerializer.instance);
        };
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
    @Bean(value = {"defaultLayerModuleProcessor", "webDefaultApplicationExceptionProcessor"})
    public WebDefaultApplicationExceptionProcessor webDefaultApplicationExceptionProcessor() {
        return new WebDefaultApplicationExceptionProcessor();
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
