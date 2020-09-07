package com.ezcoding.common.foundation.core.message.io;

import com.ezcoding.common.foundation.core.message.MessageTypeEnum;
import com.ezcoding.common.foundation.core.message.ResponseMessage;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-09-07 19:32
 */
public interface MessageWritable {

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

    /**
     * 构造响应信息字符串
     *
     * @param responseMessage 响应信息
     * @param type            信息类型
     * @return 响应信息字符串
     * @throws IOException IO异常
     */
    String buildResponseMessage(ResponseMessage<?> responseMessage, MessageTypeEnum type) throws IOException;

}
