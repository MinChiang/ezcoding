package com.ezcoding.common.web.resolver;

import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.common.foundation.core.message.head.ResponseSystemHead;
import com.ezcoding.common.foundation.core.message.head.SuccessAppHead;
import com.ezcoding.common.web.resolver.parameter.IRequestMessageParameterResolvable;
import com.ezcoding.common.web.resolver.result.IResponseMessageReturnValueResolvable;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodProcessor;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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

    private JsonRequestMessageResolver jsonRequestMessageResolver;
    private List<IResponseMessageReturnValueResolvable> returnValueResolvables = new ArrayList<>();
    private List<IRequestMessageParameterResolvable> parameterResolvables = new ArrayList<>();

    public JsonMessageMethodProcessor(List<HttpMessageConverter<?>> converters,
                                      JsonRequestMessageResolver jsonRequestMessageResolver) {
        super(converters);
        this.jsonRequestMessageResolver = jsonRequestMessageResolver;
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
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws IOException {
        RequestMessage<JsonNode> requestMessage = this.jsonRequestMessageResolver.parse(webRequest.getNativeRequest(HttpServletRequest.class));

        //如果获取不到对象
        if (requestMessage == null) {
            return null;
        }

        IRequestMessageParameterResolvable resolvable = parameterResolvables
                .stream()
                .filter(resolver -> resolver.match(parameter.getParameterType()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("未配置默认的参数解析器"));
        Object result = resolvable.resolveReturnValue(requestMessage, parameter.getParameterAnnotation(JsonParam.class), parameter);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("获取到的值为: {}", result);
        }

        return result;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        //标识请求是否已经在该方法内完成处理
        mavContainer.setRequestHandled(true);

        ResponseMessage<Object> responseMessage = null;
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
     * 按照顺序注册出参解析器
     *
     * @param resolvers 需要注册的解析器
     */
    public void registerReturnValueResolvables(IResponseMessageReturnValueResolvable... resolvers) {
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
    public void registerReturnValueResolvables(List<IResponseMessageReturnValueResolvable> resolvers) {
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
    public void registerParameterResolvables(IRequestMessageParameterResolvable... resolvers) {
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
    public void registerParameterResolvables(List<IRequestMessageParameterResolvable> resolvers) {
        if (resolvers == null) {
            return;
        }
        parameterResolvables.addAll(resolvers);
    }

}
