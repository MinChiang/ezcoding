package com.ezcoding.web.resolver;

import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.common.foundation.core.message.head.ResponseSystemHead;
import com.ezcoding.common.foundation.core.message.head.SuccessAppHead;
import com.ezcoding.common.foundation.util.ConvertUtils;
import com.ezcoding.web.resolver.parameter.DefaultRequestMessageResolver;
import com.ezcoding.web.resolver.parameter.IRequestMessageParameterResolvable;
import com.ezcoding.web.resolver.returnValue.IResponseMessageReturnValueResolvable;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodProcessor;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-06 21:18
 */
public class JsonMessageMethodProcessor extends AbstractMessageConverterMethodProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonMessageMethodProcessor.class);

    private Validator validator;
    private JsonRequestMessageResolver requestMessageResolver = new JsonRequestMessageResolver();
    private IRequestMessageParameterResolvable defaultParameterResolver = new DefaultRequestMessageResolver(new ObjectMapper());
    private List<IResponseMessageReturnValueResolvable> returnValueResolvables = Lists.newLinkedList();
    private List<IRequestMessageParameterResolvable> parameterResolvables = Lists.newLinkedList();

    public JsonMessageMethodProcessor(List<HttpMessageConverter<?>> converters) {
        super(converters);
    }

    public JsonMessageMethodProcessor(List<HttpMessageConverter<?>> converters, ContentNegotiationManager contentNegotiationManager) {
        super(converters, contentNegotiationManager);
    }

    public JsonMessageMethodProcessor(List<HttpMessageConverter<?>> converters, ContentNegotiationManager manager, List<Object> requestResponseBodyAdvice) {
        super(converters, manager, requestResponseBodyAdvice);
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
     * @param order    需要加入的
     * @param resolver 需要注册的解析器
     */
    public void registerReturnValueResolvables(int order, IResponseMessageReturnValueResolvable resolver) {
        if (resolver == null) {
            return;
        }
        returnValueResolvables.add(order, resolver);
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
     * @param order    需要加入的
     * @param resolver 需要注册的解析器
     */
    public void registerParameterResolvables(int order, IRequestMessageParameterResolvable resolver) {
        if (resolver == null) {
            return;
        }
        parameterResolvables.add(order, resolver);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(JsonParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws IOException {
        RequestMessage<JsonNode> requestMessage = this.requestMessageResolver.parse(
                webRequest.getNativeRequest(HttpServletRequest.class)
        );

        //如果获取不到对象
        if (requestMessage == null) {
            return ConvertUtils.convert(null, parameter.getParameterType());
        }

        IRequestMessageParameterResolvable resolvable = parameterResolvables
                .stream()
                .filter(resolver -> resolver.match(parameter.getParameterType()))
                .findFirst()
                .orElse(defaultParameterResolver);
        Object result = resolvable.resolveReturnValue(requestMessage, parameter.getParameterAnnotation(JsonParam.class), parameter);

        //参数校验，校验不通过直接返回异常
        if (this.validator != null) {
            this.validateArgument(parameter, result);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("获取到的值为: {}", result);
        }

        return result;
    }

    /**
     * 校验传入的参数
     *
     * @param methodParameter 方法和参数信息
     * @param object          待校验的参数
     */
    private void validateArgument(MethodParameter methodParameter, Object object) {
        if (methodParameter.hasParameterAnnotation(Validated.class)) {
            Validated validated = methodParameter.getParameterAnnotation(Validated.class);
            Class<?>[] groups = validated.value();
            Set<ConstraintViolation<Object>> validate = this.validator.validate(object, groups);
            if (validate.size() > 0) {
                throw new ConstraintViolationException(validate);
            }
        }
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.hasMethodAnnotation(JsonResult.class);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        //标识请求是否已经在该方法内完成处理
        mavContainer.setRequestHandled(true);

        ResponseMessage<Object> responseMessage
                = returnValueResolvables
                .stream()
                .filter(resolver -> resolver.match(returnValue.getClass()))
                .map(resolver -> resolver.resolveReturnValue(returnValue, returnType))
                .findFirst()
                .orElse(new ResponseMessage<>(new ResponseSystemHead(), new SuccessAppHead(), returnValue));

        ServletServerHttpRequest inputMessage = this.createInputMessage(webRequest);
        ServletServerHttpResponse outputMessage = this.createOutputMessage(webRequest);
        this.writeWithMessageConverters(responseMessage, returnType, inputMessage, outputMessage);
    }

    public Validator getValidator() {
        return this.validator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public JsonRequestMessageResolver getRequestMessageResolver() {
        return this.requestMessageResolver;
    }

    public void setRequestMessageResolver(JsonRequestMessageResolver requestMessageResolver) {
        this.requestMessageResolver = requestMessageResolver;
    }

}
