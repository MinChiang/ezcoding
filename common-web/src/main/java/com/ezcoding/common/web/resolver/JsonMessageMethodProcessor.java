package com.ezcoding.common.web.resolver;

import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.common.foundation.core.message.ResponseSystemHead;
import com.ezcoding.common.foundation.core.message.SuccessAppHead;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-06 21:18
 */
public class JsonMessageMethodProcessor extends AbstractMessageConverterMethodProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonMessageMethodProcessor.class);

    private static final String REQUEST_MESSAGE = "__REQUEST_MESSAGE__";

    private final List<ResponseMessageReturnValueResolvable> returnValueResolvables = new ArrayList<>();
    private final List<RequestMessageParameterResolvable> parameterResolvables = new ArrayList<>();

    public JsonMessageMethodProcessor(List<HttpMessageConverter<?>> converters) {
        super(converters);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(JsonParam.class);
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.hasMethodAnnotation(JsonResult.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        RequestMessage<JsonNode> requestMessage = acquireCurrentRequestMessage();
        if (requestMessage == null) {
            ParameterizedTypeImpl parameterizedType = ParameterizedTypeImpl.make(RequestMessage.class, new Type[]{JsonNode.class}, null);
            requestMessage = (RequestMessage<JsonNode>) this.readWithMessageConverters(webRequest, parameter, parameterizedType);
            //如果仍然获取不到对象
            if (requestMessage == null) {
                return null;
            }
            persistCurrentRequestMessage(requestMessage);
        }
        //清除对应的请求信息
        if (parameter.getParameterIndex() + 1 == parameter.getMethod().getParameterCount()) {
            clearCurrentRequestMessage();
        }
        //解析参数
        RequestMessageParameterResolvable resolvable = parameterResolvables
                .stream()
                .filter(resolver -> resolver.match(parameter.getParameterType()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("can not find default parameter resolver"));
        return resolvable.resolve(requestMessage, parameter.getParameterAnnotation(JsonParam.class), parameter);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        //标识请求是否已经在该方法内完成处理
        mavContainer.setRequestHandled(true);

        ResponseMessage<Object> responseMessage;
        if (returnValue == null) {
            responseMessage = new ResponseMessage<>(new ResponseSystemHead(), new SuccessAppHead(), returnValue);
        } else {
            responseMessage = this.returnValueResolvables.stream()
                    .filter(resolver -> resolver.match(returnValue.getClass()))
                    .map(resolver -> resolver.resolveReturnValue(returnValue, returnType))
                    .findFirst()
                    .orElse(new ResponseMessage<>(new ResponseSystemHead(), new SuccessAppHead(), returnValue));
        }

        ServletServerHttpRequest inputMessage = this.createInputMessage(webRequest);
        ServletServerHttpResponse outputMessage = this.createOutputMessage(webRequest);
        this.writeWithMessageConverters(responseMessage, returnType, inputMessage, outputMessage);
    }

    /**
     * 获取当前报文
     *
     * @return 当前报文
     */
    private RequestMessage<JsonNode> acquireCurrentRequestMessage() {
        //能够获取请求报文，直接返回
        return (RequestMessage<JsonNode>) RequestContextHolder.getRequestAttributes().getAttribute(REQUEST_MESSAGE, RequestAttributes.SCOPE_REQUEST);
    }

    /**
     * 保存当前报文
     *
     * @param requestMessage 待保存的报文
     */
    private void persistCurrentRequestMessage(RequestMessage<JsonNode> requestMessage) {
        RequestContextHolder.getRequestAttributes().setAttribute(REQUEST_MESSAGE, requestMessage, RequestAttributes.SCOPE_REQUEST);
    }

    /**
     * 移除当前报文
     */
    private void clearCurrentRequestMessage() {
        RequestContextHolder.getRequestAttributes().removeAttribute(REQUEST_MESSAGE, RequestAttributes.SCOPE_REQUEST);
    }

    /**
     * 按照顺序注册出参解析器
     *
     * @param resolvers 需要注册的解析器
     */
    public void registerReturnValueResolvables(ResponseMessageReturnValueResolvable... resolvers) {
        if (resolvers == null) {
            return;
        }
        returnValueResolvables.addAll(Arrays.asList(resolvers));
    }

    /**
     * 按照顺序注册出参解析器
     *
     * @param resolvers 需要注册的解析器
     */
    public void registerReturnValueResolvables(List<ResponseMessageReturnValueResolvable> resolvers) {
        if (resolvers == null) {
            return;
        }
        returnValueResolvables.addAll(resolvers);
    }

    /**
     * 按照顺序注册入参解析器
     *
     * @param resolvers 需要注册的解析器
     */
    public void registerParameterResolvables(RequestMessageParameterResolvable... resolvers) {
        if (resolvers == null) {
            return;
        }
        parameterResolvables.addAll(Arrays.asList(resolvers));
    }

    /**
     * 按照顺序注册入参解析器
     *
     * @param resolvers 需要注册的入参解析器
     */
    public void registerParameterResolvables(List<RequestMessageParameterResolvable> resolvers) {
        if (resolvers == null) {
            return;
        }
        parameterResolvables.addAll(resolvers);
    }

}
