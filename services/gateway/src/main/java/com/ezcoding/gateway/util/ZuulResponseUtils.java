package com.ezcoding.gateway.util;

import com.ezcoding.common.foundation.core.exception.AbstractApplicationException;
import com.ezcoding.common.foundation.core.exception.ExceptionBuilderFactory;
import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.common.foundation.core.message.builder.MessageBuilder;
import com.ezcoding.common.foundation.core.message.type.MessageTypeEnum;
import com.netflix.util.Pair;
import com.netflix.zuul.context.RequestContext;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-21 10:29
 */
public class ZuulResponseUtils {

    private static final MessageTypeEnum DEFAULT_MESSAGE_TYPE = MessageTypeEnum.JSON;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.OK;

    /**
     * 返回响应信息
     *
     * @param responseMessage 返回相应信息内容
     * @param messageType     响应类型
     * @param charset         响应编码
     * @param status          响应状态码
     */
    public static void response(ResponseMessage<?> responseMessage, MessageTypeEnum messageType, Charset charset, HttpStatus status) {
        RequestContext currentContext = RequestContext.getCurrentContext();
        MessageBuilder instance = MessageBuilder.getInstance();

        if (messageType == null) {
            messageType = DEFAULT_MESSAGE_TYPE;
        }
        if (charset == null) {
            charset = DEFAULT_CHARSET;
        }
        if (status == null) {
            status = DEFAULT_HTTP_STATUS;
        }

        currentContext.setResponseStatusCode(status.value());
        try {
            currentContext.setResponseBody(instance.buildResponseMessage(responseMessage, messageType));
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentContext.getZuulResponseHeaders().add(new Pair<>("Content-Type", messageType.getType() + ";charset=" + charset.displayName()));

        //不对本次请求进行路由处理
        currentContext.setSendZuulResponse(false);
    }

    /**
     * 返回成功响应信息
     *
     * @param responseMessage 返回相应信息内容
     * @param messageType     响应类型
     * @param charset         响应编码
     */
    public static void responseSuccess(ResponseMessage<?> responseMessage, MessageTypeEnum messageType, Charset charset) {
        response(responseMessage, messageType, charset, DEFAULT_HTTP_STATUS);
    }

    /**
     * 返回错误响应信息
     *
     * @param responseMessage 返回相应信息内容
     * @param messageType     响应类型
     * @param charset         响应编码
     */
    public static void responseError(ResponseMessage<?> responseMessage, MessageTypeEnum messageType, Charset charset, HttpStatus status) {
        response(responseMessage, messageType, charset, status);
    }

    /**
     * 返回成功响应信息
     *
     * @param responseMessage 返回相应信息内容
     */
    public static void responseSuccess(ResponseMessage<?> responseMessage) {
        response(responseMessage, DEFAULT_MESSAGE_TYPE, DEFAULT_CHARSET, DEFAULT_HTTP_STATUS);
    }

    /**
     * 返回错误响应信息
     *
     * @param responseMessage 返回相应信息内容
     */
    public static void responseError(ResponseMessage<?> responseMessage, HttpStatus status) {
        response(responseMessage, DEFAULT_MESSAGE_TYPE, DEFAULT_CHARSET, status);
    }

    /**
     * 返回错误响应信息
     *
     * @param exception   错误内容
     * @param messageType 响应类型
     * @param charset     响应编码
     */
    public static void responseError(AbstractApplicationException exception, MessageTypeEnum messageType, Charset charset, HttpStatus status) {
        response(MessageBuilder.getInstance().buildErrorResponseMessage(exception, null), messageType, charset, status);
    }

    /**
     * 返回错误响应信息
     *
     * @param factory     错误内容
     * @param messageType 响应类型
     * @param charset     响应编码
     */
    public static void responseError(ExceptionBuilderFactory<?> factory, MessageTypeEnum messageType, Charset charset, HttpStatus status) {
        response(MessageBuilder.getInstance().buildErrorResponseMessage(factory, null), messageType, charset, status);
    }

    /**
     * 返回错误响应信息
     *
     * @param exception 错误内容
     */
    public static void responseError(AbstractApplicationException exception, HttpStatus status) {
        response(MessageBuilder.getInstance().buildErrorResponseMessage(exception, null), DEFAULT_MESSAGE_TYPE, DEFAULT_CHARSET, status);
    }

    /**
     * 返回错误响应信息
     *
     * @param factory 错误内容
     */
    public static void responseError(ExceptionBuilderFactory<?> factory, HttpStatus status) {
        response(MessageBuilder.getInstance().buildErrorResponseMessage(factory, null), DEFAULT_MESSAGE_TYPE, DEFAULT_CHARSET, status);
    }

}
