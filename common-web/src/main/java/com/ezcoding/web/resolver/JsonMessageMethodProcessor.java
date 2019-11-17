package com.ezcoding.web.resolver;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezcoding.base.web.util.PageUtils;
import com.ezcoding.common.foundation.core.exception.CommonApplicationExceptionConstants;
import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.common.foundation.core.message.builder.IMessageBuilder;
import com.ezcoding.common.foundation.core.message.head.*;
import com.ezcoding.common.foundation.util.ConvertUtils;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
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
import java.util.List;
import java.util.Set;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-06 21:18
 */
public class JsonMessageMethodProcessor extends AbstractMessageConverterMethodProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonMessageMethodProcessor.class);
    private static final String PATH_PREFIX = "/";

    private IMessageBuilder messageBuilder;
    private ObjectMapper objectMapper;
    private Validator validator;
    private boolean openObjectValidate = false;
    private JsonRequestMessageResolver requestMessageResolver;

    public JsonMessageMethodProcessor(List<HttpMessageConverter<?>> converters) {
        super(converters);
    }

    public JsonMessageMethodProcessor(List<HttpMessageConverter<?>> converters, ContentNegotiationManager contentNegotiationManager) {
        super(converters, contentNegotiationManager);
    }

    public JsonMessageMethodProcessor(List<HttpMessageConverter<?>> converters, ContentNegotiationManager manager, List<Object> requestResponseBodyAdvice) {
        super(converters, manager, requestResponseBodyAdvice);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(JsonParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws IOException {
        RequestMessage<JsonNode> requestMessage = this.requestMessageResolver.resolve(
                webRequest.getNativeRequest(HttpServletRequest.class)
        );

        //如果获取不到对象，且参数类型是基础类型，直接返回默认对象
        if (requestMessage == null) {
            if (parameter.getParameterType().isPrimitive()) {
                return ConvertUtils.convert(null, parameter.getParameterType());
            }
            return null;
        }

        JsonParam parameterAnnotation = parameter.getParameterAnnotation(JsonParam.class);
        String defaultValue = parameterAnnotation.defaultValue();

        Object result = null;
        Class<?> parameterType = parameter.getParameterType();
        if (parameterType.isAssignableFrom(RequestMessage.class)) {
            result = requestMessage;
        } else if (parameterType.isAssignableFrom(RequestSystemHead.class)) {
            result = requestMessage.getSystemHead();
        } else if (parameterType.isAssignableFrom(RequestAppHead.class)) {
            result = requestMessage.getAppHead();
        } else {
            JsonNode payload = requestMessage.getPayload();
            String value = parameterAnnotation.value();
            if (StringUtils.isNotBlank(value)) {
                if (!value.startsWith(PATH_PREFIX)) {
                    value = (PATH_PREFIX + value);
                }
                try {
                    payload = payload.at(value);
                } catch (Exception e) {
                    throw CommonApplicationExceptionConstants.COMMON_PARAM_PARSE_ERROR.instance().param("未知的参数路径" + value).cause(e).build();
                }
            }
            if (payload == null || payload.isMissingNode()) {
                //查不到节点且参数必填，报错
                if (parameterAnnotation.required()) {
                    throw new IllegalArgumentException("方法" + parameter.getMethod() + "参数" + parameter.getParameterName() + "为必输");
                }
                result = ConvertUtils.convert(defaultValue, parameter.getParameterType());
            } else {
                //解决参数类型中的泛型问题
                JavaType javaType = this.objectMapper.constructType(parameter.getGenericParameterType());
                try {
                    result = this.objectMapper.convertValue(payload, javaType);
                } catch (Exception e) {
                    throw CommonApplicationExceptionConstants.COMMON_PARAM_PARSE_ERROR.instance().param("无法将" + payload + "转化为类型" + javaType).cause(e).build();
                }
            }
        }

        //参数校验，校验不通过直接返回异常
        if (this.openObjectValidate && this.validator != null) {
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

        ResponseMessage responseMessage;
        //以标准的方式写出报文
        if (returnValue instanceof ResponseMessage) {
            responseMessage = (ResponseMessage) returnValue;
        } else if (returnValue instanceof ResponseSystemHead) {
            responseMessage = new ResponseMessage<>((ResponseSystemHead) returnValue, new SuccessAppHead(), null);
        } else if (returnValue instanceof ResponseAppHead) {
            responseMessage = new ResponseMessage<>(new ResponseSystemHead(), (ResponseAppHead) returnValue, null);
        } else if (returnValue instanceof Page) {
            Page page = (Page) returnValue;
            PageInfo pageInfo = PageUtils.convertToPageInfo(page);
            responseMessage = new ResponseMessage<>(new ResponseSystemHead(), new SuccessAppHead(pageInfo), page.getRecords());
        } else {
            responseMessage = new ResponseMessage<>(new ResponseSystemHead(), new SuccessAppHead(), returnValue);
        }

        ServletServerHttpRequest inputMessage = this.createInputMessage(webRequest);
        ServletServerHttpResponse outputMessage = this.createOutputMessage(webRequest);
        this.writeWithMessageConverters(responseMessage, returnType, inputMessage, outputMessage);
    }

    public IMessageBuilder getMessageBuilder() {
        return this.messageBuilder;
    }

    public void setMessageBuilder(IMessageBuilder messageBuilder) {
        this.messageBuilder = messageBuilder;
    }

    public ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Validator getValidator() {
        return this.validator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public boolean isOpenObjectValidate() {
        return this.openObjectValidate;
    }

    public void setOpenObjectValidate(boolean openObjectValidate) {
        this.openObjectValidate = openObjectValidate;
    }

    public JsonRequestMessageResolver getRequestMessageResolver() {
        return this.requestMessageResolver;
    }

    public void setRequestMessageResolver(JsonRequestMessageResolver requestMessageResolver) {
        this.requestMessageResolver = requestMessageResolver;
    }
}
