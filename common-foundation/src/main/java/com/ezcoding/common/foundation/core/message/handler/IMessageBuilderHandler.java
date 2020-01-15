package com.ezcoding.common.foundation.core.message.handler;

import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.foundation.core.message.ResponseMessage;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public interface IMessageBuilderHandler {

//    static final Charset CHARSET_DEFAULT = Charset.forName("UTF-8");
//    static final int MESSAGE_TYPE_JSON = 0;
//    static final int MESSAGE_TYPE_XML = 1;
//    static final int MESSAGE_TYPE_DEFAULT = MESSAGE_TYPE_JSON;

    /**
     * 字节流转请求信息
     *
     * @param bytes 待转换的字节流
     * @param cls   载体的类型
     * @return 请求信息
     * @throws IOException IO异常
     */
    <T> RequestMessage<T> byte2Message(byte[] bytes, Class<T> cls) throws IOException;

    /**
     * 字符串转请求信息
     *
     * @param string 待转换的字符串
     * @param cls    载体的类型
     * @return 请求信息
     * @throws IOException IO异常
     */
    <T> RequestMessage<T> string2Message(String string, Class<T> cls) throws IOException;

    /**
     * 字节流转请求信息
     *
     * @param bytes   待转换的字节流
     * @param charset 字节流编码格式
     * @param cls     载体类型
     * @return 请求信息
     * @throws IOException IO异常
     */
    <T> RequestMessage<T> byte2Message(byte[] bytes, Charset charset, Class<T> cls) throws IOException;

    /**
     * 响应信息转字节流
     *
     * @param message 响应信息
     * @return 字节流
     * @throws IOException IO异常
     */
    byte[] message2Byte(ResponseMessage<?> message) throws IOException;

    /**
     * 响应信息转字符串
     *
     * @param message 响应信息
     * @return 字符串
     * @throws IOException IO异常
     */
    String message2String(ResponseMessage<?> message) throws IOException;

    /**
     * 响应信息转字节流
     *
     * @param message 响应信息转字节流
     * @param charset 字节流编码格式
     * @return 字节流
     * @throws IOException IO异常
     */
    byte[] message2Byte(ResponseMessage<?> message, Charset charset) throws IOException;

}
