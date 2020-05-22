package com.ezcoding.gateway.core.error;

import com.ezcoding.common.foundation.core.exception.AbstractApplicationException;
import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.common.foundation.core.message.builder.MessageBuilder;
import com.ezcoding.common.foundation.core.message.type.MessageTypeEnum;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * 回退功能
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-11-23 14:22
 */
public class ApplicationFallbackProvider implements FallbackProvider {

    private String code;
    private String message;

    public ApplicationFallbackProvider(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApplicationFallbackProvider(AbstractApplicationException exception) {
        this.code = exception.getCode();
        this.message = exception.getMessage();
    }

    @Override
    public String getRoute() {
        return null;
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {

            private final HttpStatus RETURN_HTTP_STATUS = HttpStatus.SERVICE_UNAVAILABLE;

            @Override
            public HttpStatus getStatusCode() {
                return RETURN_HTTP_STATUS;
            }

            @Override
            public int getRawStatusCode() {
                return RETURN_HTTP_STATUS.value();
            }

            @Override
            public String getStatusText() {
                return RETURN_HTTP_STATUS.getReasonPhrase();
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                ResponseMessage responseMessage = MessageBuilder.getInstance().buildErrorResponseMessage(code, message, null);
                byte[] outputMessage = MessageBuilder.getInstance().buildResponseMessage(responseMessage, StandardCharsets.UTF_8, MessageTypeEnum.JSON);
                return new ByteArrayInputStream(outputMessage);
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                return headers;
            }
        };
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
