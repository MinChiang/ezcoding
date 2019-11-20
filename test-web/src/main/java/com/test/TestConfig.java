package com.test;

import com.ezcoding.web.resolver.JsonMessageMethodProcessor;
import com.ezcoding.web.resolver.parameter.IRequestMessageParameterResolvable;
import com.ezcoding.web.resolver.parameter.ReqeustMessageResolver;
import com.ezcoding.web.resolver.parameter.RequestSystemHeadResolver;
import com.ezcoding.web.resolver.parameter.ResquestAppHeadResolver;
import com.ezcoding.web.resolver.returnValue.IResponseMessageReturnValueResolvable;
import com.ezcoding.web.resolver.returnValue.ResponseAppHeadResolver;
import com.ezcoding.web.resolver.returnValue.ResponseMessageResolver;
import com.ezcoding.web.resolver.returnValue.ResponseSystemHeadResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-20 11:19
 */
@Configuration
public class TestConfig implements WebMvcConfigurer {

    @Autowired
    private List<HttpMessageConverter<?>> messageConverters;

    @Bean
    public JsonMessageMethodProcessor jsonMessageMethodProcessor() {
        IRequestMessageParameterResolvable requestMessageResolver = new ReqeustMessageResolver();
        IRequestMessageParameterResolvable requestSystemHeadResolver = new RequestSystemHeadResolver();
        IRequestMessageParameterResolvable resquestAppHeadResolver = new ResquestAppHeadResolver();

        IResponseMessageReturnValueResolvable responseMessageResolver = new ResponseMessageResolver();
        IResponseMessageReturnValueResolvable responseSystemHeadResolver = new ResponseSystemHeadResolver();
        IResponseMessageReturnValueResolvable responseAppHeadResolver = new ResponseAppHeadResolver();

        JsonMessageMethodProcessor jsonMessageMethodProcessor = new JsonMessageMethodProcessor(messageConverters);
        jsonMessageMethodProcessor.registerParameterResolvables();
        jsonMessageMethodProcessor.registerParameterResolvables(requestMessageResolver, requestSystemHeadResolver, resquestAppHeadResolver);
        jsonMessageMethodProcessor.registerReturnValueResolvables(responseMessageResolver, responseSystemHeadResolver, responseAppHeadResolver);
        return jsonMessageMethodProcessor;
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        returnValueHandlers.add(jsonMessageMethodProcessor());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(jsonMessageMethodProcessor());
    }

    @Configuration
    @AutoConfigureAfter(RequestMappingHandlerAdapter.class)
    static
    class WebLastConfig implements InitializingBean, BeanFactoryAware {

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
