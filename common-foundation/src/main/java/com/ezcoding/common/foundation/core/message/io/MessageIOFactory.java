package com.ezcoding.common.foundation.core.message.io;

import com.ezcoding.common.foundation.core.message.MessageTypeEnum;
import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.common.foundation.core.message.handler.JsonMessageBuilderHandler;
import com.ezcoding.common.foundation.core.message.handler.MessageBuilderHandleable;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-09-07 19:35
 */
public class MessageIOFactory implements MessageReadable, MessageWritable {

    public static final String DEFAULT_READ_CHARSET = StandardCharsets.UTF_8.name();
    public static final String DEFAULT_WRITE_CHARSET = StandardCharsets.UTF_8.name();
    public static final String DEFAULT_READ_MESSAGE_TYPE = MessageTypeEnum.JSON.name();
    public static final String DEFAULT_WRITE_MESSAGE_TYPE = MessageTypeEnum.JSON.name();

    private static Map<MessageTypeEnum, MessageBuilderHandleable> handlerMap = new HashMap<>();
    private MessageBuilderHandleable defaultMessageBuilder = new JsonMessageBuilderHandler();

    private Charset defaultReadCharset = Charset.forName(MessageIOFactory.DEFAULT_READ_CHARSET);
    private Charset defaultWriteCharset = Charset.forName(MessageIOFactory.DEFAULT_WRITE_CHARSET);

    private MessageTypeEnum defaultReadMessageType = MessageTypeEnum.valueOf(MessageIOFactory.DEFAULT_READ_MESSAGE_TYPE);
    private MessageTypeEnum defaultWriteMessageType = MessageTypeEnum.valueOf(MessageIOFactory.DEFAULT_WRITE_MESSAGE_TYPE);

    private MessageIOFactory() {
    }

    @Override
    public <T> RequestMessage<T> buildRequestMessage(InputStream is, Class<T> cls) {
        return this.buildRequestMessage(is, cls, defaultReadCharset, defaultReadMessageType);
    }

    @Override
    public <T> RequestMessage<T> buildRequestMessage(InputStream is, Class<T> cls, Charset charset, MessageTypeEnum type) {
        try {
            MessageBuilderHandleable handler = handlerMap.getOrDefault(type, defaultMessageBuilder);
            return handler.byte2Message(is, charset, cls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public byte[] buildResponseMessage(ResponseMessage<?> responseMessage, Charset charset) throws IOException {
        return buildResponseMessage(responseMessage, defaultWriteCharset, defaultWriteMessageType);
    }

    @Override
    public byte[] buildResponseMessage(ResponseMessage<?> responseMessage, Charset charset, MessageTypeEnum type) throws IOException {
        MessageBuilderHandleable handler = handlerMap.getOrDefault(type, defaultMessageBuilder);
        return handler.message2Byte(responseMessage, charset);
    }

    @Override
    public String buildResponseMessage(ResponseMessage<?> responseMessage, MessageTypeEnum type) throws IOException {
        MessageBuilderHandleable handler = handlerMap.getOrDefault(type, defaultMessageBuilder);
        return handler.message2String(responseMessage);
    }

    public static void configHandler(MessageTypeEnum messageType, MessageBuilderHandleable messageBuilderHandler) {
        if (messageType == null || messageBuilderHandler == null) {
            throw new IllegalArgumentException("messageType and messageBuilderHandler can't be null");
        }
        handlerMap.put(messageType, messageBuilderHandler);
    }

    public static void setHandlerMap(Map<MessageTypeEnum, MessageBuilderHandleable> handlerMap) {
        if (handlerMap == null || handlerMap.isEmpty()) {
            throw new IllegalArgumentException("handlerMap can't be empty");
        }
        MessageIOFactory.handlerMap = handlerMap;
    }

    public MessageBuilderHandleable getDefaultMessageBuilder() {
        return defaultMessageBuilder;
    }

    public void setDefaultMessageBuilder(MessageBuilderHandleable defaultMessageBuilder) {
        this.defaultMessageBuilder = defaultMessageBuilder;
    }

    public Charset getDefaultReadCharset() {
        return defaultReadCharset;
    }

    public void setDefaultReadCharset(Charset defaultReadCharset) {
        this.defaultReadCharset = defaultReadCharset;
    }

    public Charset getDefaultWriteCharset() {
        return defaultWriteCharset;
    }

    public void setDefaultWriteCharset(Charset defaultWriteCharset) {
        this.defaultWriteCharset = defaultWriteCharset;
    }

    public MessageTypeEnum getDefaultReadMessageType() {
        return defaultReadMessageType;
    }

    public void setDefaultReadMessageType(MessageTypeEnum defaultReadMessageType) {
        this.defaultReadMessageType = defaultReadMessageType;
    }

    public MessageTypeEnum getDefaultWriteMessageType() {
        return defaultWriteMessageType;
    }

    public void setDefaultWriteMessageType(MessageTypeEnum defaultWriteMessageType) {
        this.defaultWriteMessageType = defaultWriteMessageType;
    }

    public static MessageIOFactory getInstance() {
        return MessageIOFactoryHolder.INSTANCE;
    }

    private static final class MessageIOFactoryHolder {

        private static final MessageIOFactory INSTANCE = new MessageIOFactory();

    }

}
