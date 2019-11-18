package com.ezcoding.web.resolver;

import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.foundation.core.message.builder.IMessageBuilder;
import com.ezcoding.common.foundation.core.message.type.MessageTypeEnum;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-03-29 11:17
 */
public class JsonRequestMessageResolver {

    private static final Charset CHARSET_READ_DEFAULT = StandardCharsets.UTF_8;
    private static final String REQUEST_MESSAGE = "__REQUEST_MESSAGE__";

    private IMessageBuilder messageBuilder;

    public JsonRequestMessageResolver(IMessageBuilder messageBuilder) {
        this.messageBuilder = messageBuilder;
    }

    public JsonRequestMessageResolver() {
    }

    /**
     * 根据请求解析原生报文
     *
     * @param servletRequest 请求
     * @return 原生报文
     * @throws IOException 解析失败
     */
    public RequestMessage<JsonNode> resolve(HttpServletRequest servletRequest) throws IOException {
        if (servletRequest == null) {
            return null;
        }

        RequestMessage<JsonNode> requestMessage = (RequestMessage<JsonNode>) RequestContextHolder.getRequestAttributes().getAttribute(REQUEST_MESSAGE, RequestAttributes.SCOPE_REQUEST);
        if (requestMessage != null) {
            //能够获取请求报文，直接返回
            return requestMessage;
        }

        try {
            requestMessage = messageBuilder.buildRequestMessage(servletRequest.getInputStream(), JsonNode.class, CHARSET_READ_DEFAULT, MessageTypeEnum.JSON);
            RequestContextHolder.getRequestAttributes().setAttribute(REQUEST_MESSAGE, requestMessage, RequestAttributes.SCOPE_REQUEST);
        } catch (IOException e) {
            throw new IOException("报文解析出现异常");
        }
        return requestMessage;
    }

    public IMessageBuilder getMessageBuilder() {
        return messageBuilder;
    }

    public void setMessageBuilder(IMessageBuilder messageBuilder) {
        this.messageBuilder = messageBuilder;
    }

}
