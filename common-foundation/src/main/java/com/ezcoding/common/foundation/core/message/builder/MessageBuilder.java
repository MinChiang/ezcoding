package com.ezcoding.common.foundation.core.message.builder;

import com.ezcoding.common.foundation.core.exception.ApplicationException;
import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.common.foundation.core.message.handler.IMessageBuilderHandler;
import com.ezcoding.common.foundation.core.message.handler.JsonMessageBuilderHandler;
import com.ezcoding.common.foundation.core.message.head.*;
import com.ezcoding.common.foundation.core.message.type.MessageTypeEnum;
import com.ezcoding.common.foundation.core.tools.uuid.IUUIDProducer;
import com.ezcoding.common.foundation.core.tools.uuid.OriginalUUIDProducer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public class MessageBuilder implements IMessageBuilder {

    private static Map<MessageTypeEnum, IMessageBuilderHandler> handlerMap = new HashMap<>();
    private static IUUIDProducer idProducer = OriginalUUIDProducer.getInstance();

    private IMessageBuilderHandler defaultMessageBuilder = new JsonMessageBuilderHandler();

    private Charset defaultReadCharset = Charset.forName(DEFAULT_READ_CHARSET);
    private Charset defaultWriteCharset = Charset.forName(DEFAULT_WRITE_CHARSET);

    private MessageTypeEnum defaultReadMessageType = MessageTypeEnum.valueOf(DEFAULT_READ_MESSAGE_TYPE);
    private MessageTypeEnum defaultWriteMessageType = MessageTypeEnum.valueOf(DEFAULT_WRITE_MESSAGE_TYPE);

    private MessageBuilder() {

    }

    public static void configHandler(MessageTypeEnum messageType, IMessageBuilderHandler messageBuilderHandler) {
        handlerMap.put(messageType, messageBuilderHandler);
    }

    public static MessageBuilder getInstance() {
        return MessageBuilderHolder.INSTANCE;
    }

    public static Map<MessageTypeEnum, IMessageBuilderHandler> getHandlerMap() {
        return handlerMap;
    }

    public static void setHandlerMap(Map<MessageTypeEnum, IMessageBuilderHandler> handlerMap) {
        MessageBuilder.handlerMap = handlerMap;
    }

    public static IUUIDProducer getIdProducer() {
        return idProducer;
    }

    public static void setIdProducer(IUUIDProducer idProducer) {
        MessageBuilder.idProducer = idProducer;
        //需要配套设置报文生成器
        RequestSystemHead.setSequenceNoProducer(idProducer);
        ResponseSystemHead.setSequenceNoProducer(idProducer);
    }

    @Override
    public <T> RequestMessage<T> buildRequestMessage(InputStream is, Class<T> cls) {
        return this.buildRequestMessage(is, cls, defaultReadCharset, defaultReadMessageType);
    }

    @Override
    public <T> RequestMessage<T> buildRequestMessage(InputStream is, Class<T> cls, Charset charset, MessageTypeEnum type) {
        try {
            IMessageBuilderHandler handler = handlerMap.getOrDefault(type, defaultMessageBuilder);
            return handler.byte2Message(is, charset, cls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> RequestMessage<T> buildRequestMessage(T payload) {
        return this.buildRequestMessage(null, payload);
    }

    @Override
    public <T> RequestMessage<T> buildRequestMessage(PageInfo pageInfo, T payload) {
        return new RequestMessage<>(new RequestSystemHead(), new RequestAppHead(pageInfo), payload);
    }

    @Override
    public <T> RequestMessage<T> buildRequestMessage(RequestSystemHead requestSystemHead, RequestAppHead requestAppHead, T payload) {
        return new RequestMessage<>(requestSystemHead, requestAppHead, payload);
    }

    @Override
    public <T> ResponseMessage<T> buildSuccessResponseMessage(T payload) {
        return new ResponseMessage<>(new ResponseSystemHead(), new SuccessAppHead(), payload);
    }

    @Override
    public <T> ResponseMessage<T> buildSuccessResponseMessage(long totalItem, T payload) {
        return new ResponseMessage<>(new ResponseSystemHead(), new SuccessAppHead(new PageInfo(totalItem)), payload);
    }

    @Override
    public <T> ResponseMessage<T> buildResponseMessage(ResponseSystemHead responseSystemHead, ResponseAppHead responseAppHead, T payload) {
        return new ResponseMessage<>(responseSystemHead, responseAppHead, payload);
    }

    @Override
    public <T> ResponseMessage<T> buildSuccessResponseMessage() {
        return this.buildSuccessResponseMessage(null);
    }

    @Override
    public <T> ResponseMessage<T> buildErrorResponseMessage() {
        return this.buildErrorResponseMessage(ErrorAppHead.getDefaultErrorCode(), ErrorAppHead.getDefaultErrorMessage());
    }

    @Override
    public <T> ResponseMessage<T> buildErrorResponseMessage(ApplicationException businessException, T payload) {
        return this.buildErrorResponseMessage(businessException.getIdentification(), businessException.getMessage(), payload);
    }

    @Override
    public <T> ResponseMessage<T> buildErrorResponseMessage(ApplicationException businessException) {
        return this.buildErrorResponseMessage(businessException.getIdentification(), businessException.getMessage(), null);
    }

    @Override
    public <T> ResponseMessage<T> buildErrorResponseMessage(String returnCode, String returnMessage) {
        return this.buildErrorResponseMessage(returnCode, returnMessage, null);
    }

    @Override
    public <T> ResponseMessage<T> buildErrorResponseMessage(String returnCode, String returnMessage, T payload) {
        return new ResponseMessage<>(new ResponseSystemHead(), new ErrorAppHead(returnCode, returnMessage), payload);
    }

    @Override
    public String buildResponseMessage(ResponseMessage<?> responseMessage, MessageTypeEnum type) throws IOException {
        IMessageBuilderHandler handler = handlerMap.getOrDefault(type, defaultMessageBuilder);
        return handler.message2String(responseMessage);
    }

    @Override
    public byte[] buildResponseMessage(ResponseMessage<?> responseMessage, Charset charset) throws IOException {
        return buildResponseMessage(responseMessage, defaultWriteCharset, defaultWriteMessageType);
    }

    @Override
    public byte[] buildResponseMessage(ResponseMessage<?> responseMessage, Charset charset, MessageTypeEnum type) throws IOException {
        IMessageBuilderHandler handler = handlerMap.getOrDefault(type, defaultMessageBuilder);
        return handler.message2Byte(responseMessage, charset);
    }

    public IMessageBuilderHandler getDefaultMessageBuilder() {
        return defaultMessageBuilder;
    }

    public void setDefaultMessageBuilder(IMessageBuilderHandler defaultMessageBuilder) {
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

    public void setDefaultErrorResponseCode(String defaultErrorResponseCode) {
        ErrorAppHead.setDefaultErrorCode(defaultErrorResponseCode);
    }

    public void setDefaultErrorResponseMessage(String defaultErrorResponseMessage) {
        ErrorAppHead.setDefaultErrorMessage(defaultErrorResponseMessage);
    }

    public void setDefaultSuccessResponseCode(String defaultSuccessResponseCode) {
        SuccessAppHead.setDefaultSuccessCode(defaultSuccessResponseCode);
    }

    public void setDefaultSuccessResponseMessage(String defaultSuccessResponseMessage) {
        SuccessAppHead.setDefaultSuceessMessage(defaultSuccessResponseMessage);
    }

    public void setDefaultId(String defaultId) {
        RequestSystemHead.setDefaultConsumerId(defaultId);
        ResponseSystemHead.setDefaultProviderId(defaultId);
    }

    private static final class MessageBuilderHolder {

        private static MessageBuilder INSTANCE = new MessageBuilder();

    }

}
