package com.ezcoding.common.web.resolver;

import com.ezcoding.common.foundation.core.message.*;
import com.ezcoding.common.web.resolver.parameter.RequestMessageParameterResolvable;
import com.ezcoding.common.web.resolver.result.ResponseMessageReturnValueResolvable;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodProcessor;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-06 21:18
 */
public class StandardMessageMethodProcessor extends AbstractMessageConverterMethodProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(StandardMessageMethodProcessor.class);

    private static final String REQUEST_MESSAGE = "__REQUEST_MESSAGE__";

    private final List<ResponseMessageReturnValueResolvable> returnValueResolvers = new ArrayList<>();
    private final List<RequestMessageParameterResolvable> parameterResolvers = new ArrayList<>();

    public StandardMessageMethodProcessor(List<HttpMessageConverter<?>> converters) {
        super(converters);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(StandardParam.class);
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.hasMethodAnnotation(StandardResult.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        RequestMessage<JsonNode> requestMessage = acquireCurrentRequestMessage();
        if (requestMessage == null) {
            ParameterizedTypeImpl parameterizedType = ParameterizedTypeImpl.make(RequestMessage.class, new Type[]{JsonNode.class}, null);
            requestMessage = (RequestMessage<JsonNode>) this.readWithMessageConverters(webRequest, parameter, parameterizedType);
            //??????????????????????????????
            if (requestMessage == null) {
                return null;
            }
            persistCurrentRequestMessage(requestMessage);
        }
        //???????????????????????????
//        if (parameter.getParameterIndex() + 1 == parameter.getMethod().getParameterCount()) {
//            clearCurrentRequestMessage();
//        }
        //????????????
        RequestMessageParameterResolvable resolvable = parameterResolvers
                .stream()
                .filter(resolver -> resolver.match(parameter.getParameterType()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("can not find default parameter resolver"));
        return resolvable.resolve(requestMessage, parameter.getParameterAnnotation(StandardParam.class), parameter);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        //???????????????????????????????????????????????????
        mavContainer.setRequestHandled(true);

        ResponseMessage<Object> responseMessage;
        if (returnValue == null) {
            responseMessage = MessageFactory.buildSuccessResponseMessage();
        } else {
            responseMessage = this.returnValueResolvers.stream()
                    .filter(resolver -> resolver.match(returnValue.getClass()))
                    .map(resolver -> resolver.resolveReturnValue(returnValue, returnType))
                    .findFirst()
                    .orElseGet(() -> MessageFactory.buildSuccessResponseMessage(returnValue));
        }

        ServletServerHttpRequest inputMessage = this.createInputMessage(webRequest);
        ServletServerHttpResponse outputMessage = this.createOutputMessage(webRequest);
        this.writeWithMessageConverters(responseMessage, returnType, inputMessage, outputMessage);
    }

    /**
     * ??????????????????
     *
     * @return ????????????
     */
    private RequestMessage<JsonNode> acquireCurrentRequestMessage() {
        return Optional
                .ofNullable(RequestContextHolder.getRequestAttributes())
                .map(requestAttributes -> (RequestMessage<JsonNode>) requestAttributes.getAttribute(REQUEST_MESSAGE, RequestAttributes.SCOPE_REQUEST))
                .orElse(null);
    }

    /**
     * ??????????????????
     *
     * @param requestMessage ??????????????????
     */
    private void persistCurrentRequestMessage(RequestMessage<JsonNode> requestMessage) {
        Optional
                .ofNullable(RequestContextHolder.getRequestAttributes())
                .ifPresent(requestAttributes -> requestAttributes.setAttribute(REQUEST_MESSAGE, requestMessage, RequestAttributes.SCOPE_REQUEST));
    }

    /**
     * ??????????????????
     */
    private void clearCurrentRequestMessage() {
        Optional
                .ofNullable(RequestContextHolder.getRequestAttributes())
                .ifPresent(requestAttributes -> requestAttributes.removeAttribute(REQUEST_MESSAGE, RequestAttributes.SCOPE_REQUEST));
    }

    /**
     * ?????????????????????????????????
     *
     * @param resolvers ????????????????????????
     */
    public void registerReturnValueResolvables(ResponseMessageReturnValueResolvable... resolvers) {
        if (resolvers == null) {
            return;
        }
        returnValueResolvers.addAll(Arrays.asList(resolvers));
    }

    /**
     * ?????????????????????????????????
     *
     * @param resolvers ????????????????????????
     */
    public void registerReturnValueResolvables(List<ResponseMessageReturnValueResolvable> resolvers) {
        if (resolvers == null) {
            return;
        }
        returnValueResolvers.addAll(resolvers);
    }

    /**
     * ?????????????????????????????????
     *
     * @param resolvers ????????????????????????
     */
    public void registerParameterResolvables(RequestMessageParameterResolvable... resolvers) {
        if (resolvers == null) {
            return;
        }
        parameterResolvers.addAll(Arrays.asList(resolvers));
    }

    /**
     * ?????????????????????????????????
     *
     * @param resolvers ??????????????????????????????
     */
    public void registerParameterResolvables(List<RequestMessageParameterResolvable> resolvers) {
        if (resolvers == null) {
            return;
        }
        parameterResolvers.addAll(resolvers);
    }

}
