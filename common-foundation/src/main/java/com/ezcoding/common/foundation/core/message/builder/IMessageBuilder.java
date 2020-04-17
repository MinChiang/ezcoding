package com.ezcoding.common.foundation.core.message.builder;

import com.ezcoding.common.foundation.core.exception.ApplicationException;
import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.common.foundation.core.message.head.*;
import com.ezcoding.common.foundation.core.message.type.MessageTypeEnum;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public interface IMessageBuilder {

    String DEFAULT_READ_CHARSET = "UTF-8";
    String DEFAULT_WRITE_CHARSET = "UTF-8";
    String DEFAULT_READ_MESSAGE_TYPE = MessageTypeEnum.JSON.name();
    String DEFAULT_WRITE_MESSAGE_TYPE = MessageTypeEnum.JSON.name();

    /**
     * 构造请求信息
     *
     * @param is  输入流
     * @param cls 载体的类型
     * @return 请求信息
     * @throws IOException IO异常
     */
    <T> RequestMessage<T> buildRequestMessage(InputStream is, Class<T> cls) throws IOException;

    /**
     * 构造请求信息
     *
     * @param is      输入流
     * @param cls     载体的类型
     * @param charset 编码格式
     * @param type    请求格式类型
     * @return 请求信息
     * @throws IOException IO异常
     */
    <T> RequestMessage<T> buildRequestMessage(InputStream is, Class<T> cls, Charset charset, MessageTypeEnum type) throws IOException;

    /**
     * 构造请求信息
     *
     * @param payload 请求对象
     * @return 请求信息
     * @throws IOException IO异常
     */
    <T> RequestMessage<T> buildRequestMessage(T payload) throws IOException;

    /**
     * 构造请求信息
     *
     * @param pageInfo 分页信息
     * @param payload  请求对象
     * @return 请求信息
     * @throws IOException IO异常
     */
    <T> RequestMessage<T> buildRequestMessage(PageInfo pageInfo, T payload) throws IOException;

    /**
     * 构造请求信息
     *
     * @param requestSystemHead 系统请求头
     * @param requestAppHead    应用请求头
     * @param payload           请求对象
     * @return 请求信息
     * @throws IOException IO异常
     */
    <T> RequestMessage<T> buildRequestMessage(RequestSystemHead requestSystemHead, RequestAppHead requestAppHead, T payload) throws IOException;

    /**
     * 构造成功响应信息
     *
     * @param payload 响应对象
     * @return 成功响应信息
     * @throws IOException IO异常
     */
    <T> ResponseMessage<T> buildSuccessResponseMessage(T payload) throws IOException;

    /**
     * 构造成功响应信息
     *
     * @param totalItem 查询对象总数量
     * @param payload   响应对象
     * @return 成功响应信息
     * @throws IOException IO异常
     */
    <T> ResponseMessage<T> buildSuccessResponseMessage(long totalItem, T payload) throws IOException;

    /**
     * 构造成功响应信息
     *
     * @param responseSystemHead 系统请求头
     * @param responseAppHead    应用请求头
     * @param payload            响应对象
     * @return 成功响应信息
     */
    <T> ResponseMessage<T> buildResponseMessage(ResponseSystemHead responseSystemHead, ResponseAppHead responseAppHead, T payload);

    /**
     * 构造成功响应信息，无响应体
     *
     * @return 成功响应信息
     * @throws IOException IO异常
     */
    <T> ResponseMessage<T> buildSuccessResponseMessage() throws IOException;

    /**
     * 构造失败响应信息
     *
     * @return 失败响应信息
     * @throws IOException IO异常
     */
    <T> ResponseMessage<T> buildErrorResponseMessage() throws IOException;

    /**
     * 构造失败响应信息
     *
     * @param returnCode    响应结果号码
     * @param returnMessage 响应信息内容
     * @param payload       返回内容
     * @return 失败响应信息
     * @throws IOException IO异常
     */
    <T> ResponseMessage<T> buildErrorResponseMessage(String returnCode, String returnMessage, T payload) throws IOException;

    /**
     * 构造失败响应信息
     *
     * @param returnCode    响应结果号码
     * @param returnMessage 响应信息内容数组
     * @param payload       返回内容
     * @return 失败响应信息
     * @throws IOException IO异常
     */
    <T> ResponseMessage<T> buildErrorResponseMessage(String returnCode, List<String> returnMessage, T payload) throws IOException;

    /**
     * 构造失败响应信息
     *
     * @param applicationException 程序异常
     * @param payload              返回内容
     * @return 失败响应信息
     * @throws IOException IO异常
     */
    <T> ResponseMessage<T> buildErrorResponseMessage(ApplicationException applicationException, T payload) throws IOException;

    /**
     * 构造失败响应信息
     * @param returnCode 返回码
     * @param returnMessage 返回内容
     * @return 失败响应信息
     */
    <T> ResponseMessage<T> buildErrorResponseMessage(String returnCode, String returnMessage);

    /**
     * 构造失败响应信息
     *
     * @param businessException 程序异常
     * @return 失败响应信息
     * @throws IOException IO异常
     */
    <T> ResponseMessage<T> buildErrorResponseMessage(ApplicationException businessException) throws IOException;

    /**
     * 构造响应信息字符串
     *
     * @param responseMessage 响应信息
     * @param type            信息类型
     * @return 响应信息字符串
     * @throws IOException IO异常
     */
    String buildResponseMessage(ResponseMessage<?> responseMessage, MessageTypeEnum type) throws IOException;

    /**
     * 构造响应信息字节流
     *
     * @param responseMessage 响应信息
     * @param charset         编码格式
     * @return 响应信息字节流
     * @throws IOException IO异常
     */
    byte[] buildResponseMessage(ResponseMessage<?> responseMessage, Charset charset) throws IOException;

    /**
     * 构造响应信息字节流
     *
     * @param responseMessage 响应信息
     * @param charset         编码格式
     * @param type            信息类型
     * @return 响应信息字节流
     * @throws IOException IO异常
     */
    byte[] buildResponseMessage(ResponseMessage<?> responseMessage, Charset charset, MessageTypeEnum type) throws IOException;

}
