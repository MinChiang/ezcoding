package com.ezcoding.common.foundation.core.message.builder;

import com.ezcoding.common.foundation.core.exception.AbstractApplicationException;
import com.ezcoding.common.foundation.core.exception.ExceptionBuilderFactory;
import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.common.foundation.core.message.handler.IMessageBuilderHandler;
import com.ezcoding.common.foundation.core.message.handler.JsonMessageBuilderHandler;
import com.ezcoding.common.foundation.core.message.head.*;
import com.ezcoding.common.foundation.core.message.type.MessageTypeEnum;
import com.ezcoding.common.foundation.core.tools.uuid.IUUIDProducer;
import com.ezcoding.common.foundation.core.tools.uuid.OriginalUUIDProducer;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    private Charset defaultReadCharset = StandardCharsets.UTF_8;
    private Charset defaultWriteCharset = StandardCharsets.UTF_8;

    private MessageTypeEnum defaultReadMessageType = MessageTypeEnum.JSON;
    private MessageTypeEnum defaultWriteMessageType = MessageTypeEnum.JSON;

    private String defaultConsumerId;

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
        byte[] bytes;
        try {
            bytes = IOUtils.toByteArray(is);
            IMessageBuilderHandler handler = handlerMap.getOrDefault(type, defaultMessageBuilder);
            return handler.byte2Message(bytes, charset, cls);
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
        return new RequestMessage<>(new RequestSystemHead(defaultConsumerId, idProducer.produce()), new RequestAppHead(pageInfo), payload);
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
    public ResponseMessage buildSuccessResponseMessage() {
        return this.buildSuccessResponseMessage(null);
    }

    @Override
    public ResponseMessage buildErrorResponseMessage() {
        return this.buildErrorResponseMessage(ErrorAppHead.getDefaultErrorCode(), ErrorAppHead.getDefaultErrorMessage(), null);
    }

    @Override
    public <T> ResponseMessage<T> buildErrorResponseMessage(String returnCode, List<String> returnMessage, T payload) {
        return new ResponseMessage<>(new ResponseSystemHead(), new ErrorAppHead(returnCode, returnMessage), payload);
    }

    @Override
    public <T> ResponseMessage<T> buildErrorResponseMessage(AbstractApplicationException businessException, T payload) {
        return this.buildErrorResponseMessage(businessException.getCode(), businessException.getMessage(), payload);
    }

    @Override
    public <T> ResponseMessage<T> buildErrorResponseMessage(ExceptionBuilderFactory factory, T payload) {
        return this.buildErrorResponseMessage(factory.instance().build(), payload);
    }

    @Override
    public <T> ResponseMessage<T> buildErrorResponseMessage(String returnCode, String returnMessage, T payload) {
        ArrayList<String> strings = new ArrayList<>(1);
        strings.add(returnMessage);
        return this.buildErrorResponseMessage(returnCode, strings, payload);
    }

    @Override
    public String buildResponseMessage(ResponseMessage responseMessage, MessageTypeEnum type) throws IOException {
        IMessageBuilderHandler handler = handlerMap.getOrDefault(type, defaultMessageBuilder);
        return handler.message2String(responseMessage);
    }

    @Override
    public byte[] buildResponseMessage(ResponseMessage responseMessage, Charset charset) throws IOException {
        return buildResponseMessage(responseMessage, defaultWriteCharset, defaultWriteMessageType);
    }

    @Override
    public byte[] buildResponseMessage(ResponseMessage responseMessage, Charset charset, MessageTypeEnum type) throws IOException {
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

    public String getDefaultConsumerId() {
        return defaultConsumerId;
    }

    public void setDefaultConsumerId(String defaultConsumerId) {
        this.defaultConsumerId = defaultConsumerId;
    }

    private static final class MessageBuilderHolder {
        private static MessageBuilder INSTANCE = new MessageBuilder();
    }
}
