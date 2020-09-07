package com.ezcoding.common.foundation.core.message.io;

import com.ezcoding.common.foundation.core.message.MessageTypeEnum;
import com.ezcoding.common.foundation.core.message.RequestMessage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-09-07 19:29
 */
public interface MessageReadable {

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

}
