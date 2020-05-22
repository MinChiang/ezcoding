package com.ezcoding.common.foundation.core.message.handler;

import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.foundation.core.message.ResponseMessage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public class FormMessageBuilderHandler implements MessageBuilderHandleable {

    @Override
    public <T> RequestMessage<T> byte2Message(byte[] bytes, Class<T> cls) throws IOException {
        return null;
    }

    @Override
    public <T> RequestMessage<T> string2Message(String string, Class<T> cls) throws IOException {
        return null;
    }

    @Override
    public <T> RequestMessage<T> byte2Message(byte[] bytes, Charset charset, Class<T> cls) throws IOException {
        return null;
    }

    @Override
    public <T> RequestMessage<T> byte2Message(InputStream is, Charset charset, Class<T> cls) throws IOException {
        return null;
    }

    @Override
    public byte[] message2Byte(ResponseMessage<?> message) throws IOException {
        return new byte[0];
    }

    @Override
    public String message2String(ResponseMessage<?> message) throws IOException {
        return null;
    }

    @Override
    public byte[] message2Byte(ResponseMessage<?> message, Charset charset) throws IOException {
        return new byte[0];
    }

}
