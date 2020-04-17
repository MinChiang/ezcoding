package com.ezcoding.common.foundation.core.message.handler;

import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.common.foundation.core.message.head.RequestAppHead;
import com.ezcoding.common.foundation.core.message.head.RequestSystemHead;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public class JsonMessageBuilderHandler implements IMessageBuilderHandler {

    private static final Charset CHARSET_READ_DEFAULT = StandardCharsets.UTF_8;
    private static final Charset CHARSET_WRITE_DEFAULT = StandardCharsets.UTF_8;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> RequestMessage<T> byte2Message(byte[] bytes, Class<T> cls) throws IOException {
        return this.byte2Message(bytes, CHARSET_READ_DEFAULT, cls);
    }

    @Override
    public <T> RequestMessage<T> string2Message(String string, Class<T> cls) throws IOException {
        RequestMessage<T> requestMessage = objectMapper.readValue(string, new TypeReference<RequestMessage<T>>() {
        });
        if (requestMessage.getSystemHead() == null) {
            requestMessage.setSystemHead(new RequestSystemHead());
        }
        if (requestMessage.getAppHead() == null) {
            requestMessage.setAppHead(new RequestAppHead());
        }
        return requestMessage;
    }

    @Override
    public <T> RequestMessage<T> byte2Message(byte[] bytes, Charset charset, Class<T> cls) throws IOException {
        return this.byte2Message(new ByteArrayInputStream(bytes), charset, cls);
    }

    @Override
    public <T> RequestMessage<T> byte2Message(InputStream is, Charset charset, Class<T> cls) throws IOException {
        EmptyBodyCheckingInputStreamHelper emptyBodyCheckingInputStreamHelper = new EmptyBodyCheckingInputStreamHelper(is);
        if (!emptyBodyCheckingInputStreamHelper.hasBody()) {
            return null;
        }

        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(RequestMessage.class, cls);
        RequestMessage<T> requestMessage = objectMapper.readValue(emptyBodyCheckingInputStreamHelper.getBody(), javaType);
        if (requestMessage.getSystemHead() == null) {
            requestMessage.setSystemHead(new RequestSystemHead());
        }
        if (requestMessage.getAppHead() == null) {
            requestMessage.setAppHead(new RequestAppHead());
        }
        return requestMessage;
    }

    @Override
    public byte[] message2Byte(ResponseMessage<?> message) throws IOException {
        return this.message2Byte(message, CHARSET_WRITE_DEFAULT);
    }

    @Override
    public String message2String(ResponseMessage<?> message) throws JsonProcessingException {
        return objectMapper.writeValueAsString(message);
    }

    @Override
    public byte[] message2Byte(ResponseMessage<?> message, Charset charset) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream, charset);
        objectMapper.writeValue(outputStreamWriter, message);
        return byteArrayOutputStream.toByteArray();
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private static class EmptyBodyCheckingInputStreamHelper {

        private final InputStream body;

        public EmptyBodyCheckingInputStreamHelper(InputStream inputStream) throws IOException {
            if (inputStream.markSupported()) {
                inputStream.mark(1);
                this.body = (inputStream.read() != -1 ? inputStream : null);
                inputStream.reset();
            } else {
                PushbackInputStream pushbackInputStream = new PushbackInputStream(inputStream);
                int b = pushbackInputStream.read();
                if (b == -1) {
                    this.body = null;
                } else {
                    this.body = pushbackInputStream;
                    pushbackInputStream.unread(b);
                }
            }
        }

        public InputStream getBody() {
            return this.hasBody() ? this.body : null;
        }

        public boolean hasBody() {
            return (this.body != null);
        }

    }

}
