package com.ezcoding.common.web.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodProcessor;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-09-07 23:33
 */
public class StandardMessageMethodProcessor extends AbstractMessageConverterMethodProcessor {

    protected StandardMessageMethodProcessor(List<HttpMessageConverter<?>> converters) {
        super(converters);
    }

    protected StandardMessageMethodProcessor(List<HttpMessageConverter<?>> converters, ContentNegotiationManager contentNegotiationManager) {
        super(converters, contentNegotiationManager);
    }

    protected StandardMessageMethodProcessor(List<HttpMessageConverter<?>> converters, ContentNegotiationManager manager, List<Object> requestResponseBodyAdvice) {
        super(converters, manager, requestResponseBodyAdvice);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(StandardParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return null;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.hasMethodAnnotation(StandardResult.class);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {

    }

}
