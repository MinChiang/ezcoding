package com.ezcoding.common.foundation.core.message.builder;

import com.ezcoding.common.foundation.core.exception.ApplicationException;
import com.ezcoding.common.foundation.core.message.*;
import com.ezcoding.common.foundation.core.message.handler.JsonMessageBuilderHandler;
import com.ezcoding.common.foundation.core.message.handler.MessageBuilderHandleable;
import com.ezcoding.common.foundation.core.tools.uuid.IdProduceable;
import com.ezcoding.common.foundation.core.tools.uuid.OriginalUuidProducer;

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
public class MessageFactory implements MessageBuildable {

    private static Map<MessageTypeEnum, MessageBuilderHandleable> handlerMap = new HashMap<>();
    private static IdProduceable idProducer = OriginalUuidProducer.getInstance();

    private MessageBuilderHandleable defaultMessageBuilder = new JsonMessageBuilderHandler();

    private Charset defaultReadCharset = Charset.forName(DEFAULT_READ_CHARSET);
    private Charset defaultWriteCharset = Charset.forName(DEFAULT_WRITE_CHARSET);

    private MessageTypeEnum defaultReadMessageType = MessageTypeEnum.valueOf(DEFAULT_READ_MESSAGE_TYPE);
    private MessageTypeEnum defaultWriteMessageType = MessageTypeEnum.valueOf(DEFAULT_WRITE_MESSAGE_TYPE);

    private MessageFactory() {

    }

    public static void configHandler(MessageTypeEnum messageType, MessageBuilderHandleable messageBuilderHandler) {
        handlerMap.put(messageType, messageBuilderHandler);
    }

    public static MessageFactory getInstance() {
        return MessageBuilderHolder.INSTANCE;
    }

    public static Map<MessageTypeEnum, MessageBuilderHandleable> getHandlerMap() {
        return handlerMap;
    }

    public static void setHandlerMap(Map<MessageTypeEnum, MessageBuilderHandleable> handlerMap) {
        MessageFactory.handlerMap = handlerMap;
    }

    public static IdProduceable getIdProducer() {
        return idProducer;
    }

    public static void setIdProducer(IdProduceable idProducer) {
        MessageFactory.idProducer = idProducer;
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
            MessageBuilderHandleable handler = handlerMap.getOrDefault(type, defaultMessageBuilder);
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
    public <T> ResponseMessage<T> buildResponseMessage(ResponseSystemHead responseSystemHead, ResponseAppHead responseAppHead, T payload) {
        return new ResponseMessage<>(responseSystemHead, responseAppHead, payload);
    }

    @Override
    public <T> ResponseMessage<T> buildSuccessResponseMessage(Long totalItem, T payload) {
        return new ResponseMessage<>(new ResponseSystemHead(), new SuccessAppHead(new PageInfo(totalItem)), payload);
    }

    @Override
    public <T> ResponseMessage<T> buildSuccessResponseMessage(T payload) {
        return new ResponseMessage<>(new ResponseSystemHead(), new SuccessAppHead(), payload);
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
        MessageBuilderHandleable handler = handlerMap.getOrDefault(type, defaultMessageBuilder);
        return handler.message2String(responseMessage);
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

    public static <T> SuccessResponseFactory<T> success(T body) {
        return new SuccessResponseFactory<>(body);
    }

    public static SuccessResponseFactory<?> success() {
        return success(null);
    }

    public static <T> ErrorResponseFactory<T> error(T body) {
        return new ErrorResponseFactory<>(body);
    }

    public static <T> ErrorResponseFactory<?> error(ApplicationException exception) {
        return new ErrorResponseFactory<>().errorCode(exception.getIdentification()).errorMessage(exception.getSummary());
    }

    public static ErrorResponseFactory<?> error() {
        return new ErrorResponseFactory<>();
    }

    public static <T> DefaultRequestMessageFactory<T> create(T body) {
        return new DefaultRequestMessageFactory<>(body);
    }

    public static <T> DefaultRequestMessageFactory<T> create() {
        return new DefaultRequestMessageFactory<>();
    }

    private static final class MessageBuilderHolder {

        private static final MessageFactory INSTANCE = new MessageFactory();

    }

    /**
     * @author MinChiang
     * @version 1.0.0
     * @date 2019-09-01 14:44
     */
    public static class ErrorResponseFactory<T> extends AbstractBodyFactory<T> {

        private String errorCode;
        private String errorMessage;

        private ErrorResponseFactory(T body) {
            super(body);
        }

        private ErrorResponseFactory() {
        }

        public ErrorResponseFactory<T> errorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public ErrorResponseFactory<T> errorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        @Override
        public ResponseMessage<T> build() {
            return MessageBuilderHolder.INSTANCE.buildErrorResponseMessage(this.errorCode, this.errorMessage, this.body);
        }

    }

    /**
     * @author MinChiang
     * @version 1.0.0
     * @date 2019-09-01 14:41
     */
    public static class SuccessResponseFactory<T> extends AbstractBodyFactory<T> {

        private Long totalItem;

        private SuccessResponseFactory<T> totalItem(Long totalItem) {
            this.totalItem = totalItem;
            return this;
        }

        private SuccessResponseFactory(T body) {
            super(body);
        }

        private SuccessResponseFactory() {

        }

        @Override
        public ResponseMessage<T> build() {
            if (this.totalItem == null) {
                return MessageBuilderHolder.INSTANCE.buildSuccessResponseMessage(this.body);
            }
            return MessageBuilderHolder.INSTANCE.buildSuccessResponseMessage(this.totalItem, this.body);
        }

    }

    /**
     * @author MinChiang
     * @version 1.0.0
     * @date 2019-09-01 15:03
     */
    public static class DefaultRequestMessageFactory<T> extends AbstractBodyFactory<T> {

        private Integer pageSize;
        private Integer currentPage;

        private DefaultRequestMessageFactory(T body) {
            super(body);
        }

        private DefaultRequestMessageFactory() {
            this(null);
        }

        public DefaultRequestMessageFactory<T> pageSize(Integer pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public DefaultRequestMessageFactory<T> currentPage(Integer currentPage) {
            this.currentPage = currentPage;
            return this;
        }

        @Override
        public RequestMessage<T> build() {
            if (this.pageSize == null && this.currentPage == null) {
                return MessageBuilderHolder.INSTANCE.buildRequestMessage(this.body);
            }
            return MessageBuilderHolder.INSTANCE.buildRequestMessage(new PageInfo(this.currentPage, this.pageSize), this.body);
        }

    }

    /**
     * @author MinChiang
     * @version 1.0.0
     * @date 2019-09-01 14:38
     */
    public abstract static class AbstractBodyFactory<T> {

        protected T body;

        AbstractBodyFactory(T body) {
            this.body = body;
        }

        AbstractBodyFactory() {
        }

        /**
         * 构建响应实例
         *
         * @return 响应实例
         */
        public abstract AbstractMessage<T> build();

    }

}
