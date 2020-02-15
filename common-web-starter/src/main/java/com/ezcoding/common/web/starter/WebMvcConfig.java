package com.ezcoding.common.web.starter;

import com.ezcoding.common.core.user.resolve.CompositeUserLoader;
import com.ezcoding.common.core.user.resolve.IUserLoadable;
import com.ezcoding.common.core.user.resolve.IUserProxyable;
import com.ezcoding.common.foundation.core.exception.processor.AbstractApplicationExceptionManager;
import com.ezcoding.common.foundation.core.exception.processor.ApplicationExceptionResolver;
import com.ezcoding.common.foundation.core.message.builder.IMessageBuilder;
import com.ezcoding.common.web.jwt.AuthSettings;
import com.ezcoding.common.web.resolver.JsonMessageMethodProcessor;
import com.ezcoding.common.web.resolver.JsonPageMethodProcessor;
import com.ezcoding.common.web.resolver.JsonRequestMessageResolver;
import com.ezcoding.common.web.resolver.UserArgumentResolver;
import com.ezcoding.common.web.resolver.parameter.*;
import com.ezcoding.common.web.resolver.result.IResponseMessageReturnValueResolvable;
import com.ezcoding.common.web.resolver.result.ResponseAppHeadResolver;
import com.ezcoding.common.web.resolver.result.ResponseMessageResolver;
import com.ezcoding.common.web.resolver.result.ResponseSystemHeadResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
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
    @Autowired
    private EzcodingWebConfigBean ezcodingWebConfigBean;
    @Resource(name = "defaultExceptionManager")
    private AbstractApplicationExceptionManager defaultExceptionManager;

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

}
