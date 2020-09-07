package com.ezcoding.common.foundation.core.message;

import com.ezcoding.common.foundation.core.exception.ApplicationException;
import com.ezcoding.common.foundation.core.tools.uuid.IdProduceable;
import com.ezcoding.common.foundation.core.tools.uuid.OriginalUuidProducer;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public class MessageFactory {

    private static IdProduceable idProducer = OriginalUuidProducer.getInstance();

    private MessageFactory() {

    }

    public static IdProduceable getIdProducer() {
        return idProducer;
    }

    public static void setIdProducer(IdProduceable idProducer) {
        if (idProducer == null) {
            throw new IllegalArgumentException("idProducer can't be null");
        }
        MessageFactory.idProducer = idProducer;
        //需要配套设置报文生成器
        RequestSystemHead.setSequenceNoProducer(idProducer);
        ResponseSystemHead.setSequenceNoProducer(idProducer);
    }

    /**
     * 构造请求信息
     *
     * @param payload 请求对象
     * @return 请求信息
     */
    public static <T> RequestMessage<T> buildRequestMessage(T payload) {
        return buildRequestMessage(null, payload);
    }

    /**
     * 构造请求信息
     *
     * @param pageInfo 分页信息
     * @param payload  请求对象
     * @return 请求信息
     */
    public static <T> RequestMessage<T> buildRequestMessage(PageInfo pageInfo, T payload) {
        return new RequestMessage<>(new RequestSystemHead(), new RequestAppHead(pageInfo), payload);
    }

    /**
     * 构造请求信息
     *
     * @param requestSystemHead 系统请求头
     * @param requestAppHead    应用请求头
     * @param payload           请求对象
     * @return 请求信息
     */
    public static <T> RequestMessage<T> buildRequestMessage(RequestSystemHead requestSystemHead, RequestAppHead requestAppHead, T payload) {
        return new RequestMessage<>(requestSystemHead, requestAppHead, payload);
    }

    /**
     * 构造成功响应信息
     *
     * @param payload 响应对象
     * @return 成功响应信息
     */
    public static <T> ResponseMessage<T> buildSuccessResponseMessage(T payload) {
        return new ResponseMessage<>(new ResponseSystemHead(), new SuccessAppHead(), payload);
    }

    /**
     * 构造成功响应信息
     *
     * @param totalItem 查询对象总数量
     * @param payload   响应对象
     * @return 成功响应信息
     */
    public static <T> ResponseMessage<T> buildSuccessResponseMessage(Long totalItem, T payload) {
        return new ResponseMessage<>(new ResponseSystemHead(), new SuccessAppHead(new PageInfo(totalItem)), payload);
    }

    /**
     * 构造成功响应信息
     *
     * @param responseSystemHead 系统请求头
     * @param responseAppHead    应用请求头
     * @param payload            响应对象
     * @return 成功响应信息
     */
    public static <T> ResponseMessage<T> buildResponseMessage(ResponseSystemHead responseSystemHead, ResponseAppHead responseAppHead, T payload) {
        return new ResponseMessage<>(responseSystemHead, responseAppHead, payload);
    }

    /**
     * 构造成功响应信息，无响应体
     *
     * @return 成功响应信息
     */
    public static <T> ResponseMessage<T> buildSuccessResponseMessage() {
        return buildSuccessResponseMessage(null);
    }

    /**
     * 构造失败响应信息
     *
     * @return 失败响应信息
     */
    public static <T> ResponseMessage<T> buildErrorResponseMessage() {
        return buildErrorResponseMessage(ErrorAppHead.getDefaultErrorCode(), ErrorAppHead.getDefaultErrorMessage());
    }

    /**
     * 构造失败响应信息
     *
     * @param returnCode    响应结果号码
     * @param returnMessage 响应信息内容
     * @param payload       返回内容
     * @return 失败响应信息
     */
    public static <T> ResponseMessage<T> buildErrorResponseMessage(String returnCode, String returnMessage, T payload) {
        return new ResponseMessage<>(new ResponseSystemHead(), new ErrorAppHead(returnCode, returnMessage), payload);
    }

    /**
     * 构造失败响应信息
     *
     * @param applicationException 程序异常
     * @param payload              返回内容
     * @return 失败响应信息
     */
    public static <T> ResponseMessage<T> buildErrorResponseMessage(ApplicationException applicationException, T payload) {
        return buildErrorResponseMessage(applicationException.getIdentification(), applicationException.getMessage(), payload);
    }

    /**
     * 构造失败响应信息
     *
     * @param returnCode    返回码
     * @param returnMessage 返回内容
     * @return 失败响应信息
     */
    public static <T> ResponseMessage<T> buildErrorResponseMessage(String returnCode, String returnMessage) {
        return buildErrorResponseMessage(returnCode, returnMessage, null);
    }

    /**
     * 构造失败响应信息
     *
     * @param businessException 程序异常
     * @return 失败响应信息
     */
    public static <T> ResponseMessage<T> buildErrorResponseMessage(ApplicationException businessException) {
        return buildErrorResponseMessage(businessException.getIdentification(), businessException.getMessage(), null);
    }

    public static void setDefaultErrorResponseCode(String defaultErrorResponseCode) {
        ErrorAppHead.setDefaultErrorCode(defaultErrorResponseCode);
    }

    public static void setDefaultErrorResponseMessage(String defaultErrorResponseMessage) {
        ErrorAppHead.setDefaultErrorMessage(defaultErrorResponseMessage);
    }

    public static void setDefaultSuccessResponseCode(String defaultSuccessResponseCode) {
        SuccessAppHead.setDefaultSuccessCode(defaultSuccessResponseCode);
    }

    public static void setDefaultSuccessResponseMessage(String defaultSuccessResponseMessage) {
        SuccessAppHead.setDefaultSuceessMessage(defaultSuccessResponseMessage);
    }

    public static void setDefaultId(String defaultId) {
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
            return buildErrorResponseMessage(this.errorCode, this.errorMessage, this.body);
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
                return buildSuccessResponseMessage(this.body);
            }
            return buildSuccessResponseMessage(this.totalItem, this.body);
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
                return buildRequestMessage(this.body);
            }
            return buildRequestMessage(new PageInfo(this.currentPage, this.pageSize), this.body);
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
