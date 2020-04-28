package com.ezcoding.common.foundation.core.message;

import com.ezcoding.common.foundation.core.exception.ApplicationException;
import com.ezcoding.common.foundation.core.message.builder.MessageBuilder;
import com.ezcoding.common.foundation.core.message.head.ResponseAppHead;
import com.ezcoding.common.foundation.core.message.head.ResponseSystemHead;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.net.URI;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.LinkedHashSet;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-13 15:18
 */
public class StandardResponseMessageBuilder<T> {

    private HttpStatus httpStatus;
    private ResponseMessage<T> body;
    private HttpHeaders headers = new HttpHeaders();

    private StandardResponseMessageBuilder(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public static <T> StandardResponseMessageBuilder<T> status(HttpStatus status) {
        Assert.notNull(status, "HttpStatus must not be null");
        return new StandardResponseMessageBuilder<>(status);
    }

    public static <T> StandardResponseMessageBuilder<T> ok() {
        return status(HttpStatus.OK);
    }

    public static <T> StandardResponseMessageBuilder<T> created(URI location) {
        StandardResponseMessageBuilder<T> result = status(HttpStatus.CREATED);
        result.headers.setLocation(location);
        return result;
    }

    public static <T> StandardResponseMessageBuilder<T> accepted() {
        return status(HttpStatus.ACCEPTED);
    }

    public static <T> StandardResponseMessageBuilder<T> noContent() {
        return status(HttpStatus.NO_CONTENT);
    }

    public static <T> StandardResponseMessageBuilder<T> badRequest() {
        return status(HttpStatus.BAD_REQUEST);
    }

    public static <T> StandardResponseMessageBuilder<T> notFound() {
        return status(HttpStatus.NOT_FOUND);
    }

    public static <T> StandardResponseMessageBuilder<T> unprocessableEntity() {
        return status(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public StandardResponseMessageBuilder<T> header(String headerName, String... headerValues) {
        for (String headerValue : headerValues) {
            this.headers.add(headerName, headerValue);
        }
        return this;
    }

    public StandardResponseMessageBuilder<T> headers(@Nullable HttpHeaders headers) {
        if (headers != null) {
            this.headers.putAll(headers);
        }
        return this;
    }

    public StandardResponseMessageBuilder<T> allow(HttpMethod... allowedMethods) {
        this.headers.setAllow(new LinkedHashSet<>(Arrays.asList(allowedMethods)));
        return this;
    }

    public StandardResponseMessageBuilder<T> contentLength(long contentLength) {
        this.headers.setContentLength(contentLength);
        return this;
    }

    public StandardResponseMessageBuilder<T> contentType(MediaType contentType) {
        this.headers.setContentType(contentType);
        return this;
    }

    public StandardResponseMessageBuilder<T> eTag(String etag) {
        if (!etag.startsWith("\"") && !etag.startsWith("W/\"")) {
            etag = "\"" + etag;
        }
        if (!etag.endsWith("\"")) {
            etag = etag + "\"";
        }
        this.headers.setETag(etag);
        return this;
    }

    public StandardResponseMessageBuilder<T> lastModified(ZonedDateTime date) {
        this.headers.setLastModified(date);
        return this;
    }

    public StandardResponseMessageBuilder<T> lastModified(Instant date) {
        this.headers.setLastModified(date);
        return this;
    }

    public StandardResponseMessageBuilder<T> lastModified(long date) {
        this.headers.setLastModified(date);
        return this;
    }

    public StandardResponseMessageBuilder<T> location(URI location) {
        this.headers.setLocation(location);
        return this;
    }

    public StandardResponseMessageBuilder<T> cacheControl(CacheControl cacheControl) {
        this.headers.setCacheControl(cacheControl);
        return this;
    }

    public StandardResponseMessageBuilder<T> varyBy(String... requestHeaders) {
        this.headers.setVary(Arrays.asList(requestHeaders));
        return this;
    }

    public StandardResponseMessageBuilder<T> body(ResponseMessage<T> responseMessage) {
        this.body = responseMessage;
        return this;
    }

    public StandardResponseMessageBuilder<T> body(ResponseSystemHead responseSystemHead, ResponseAppHead responseAppHead, T payload) {
        this.body = MessageBuilder.getInstance().buildResponseMessage(responseSystemHead, responseAppHead, payload);
        return this;
    }

    public StandardResponseMessageBuilder<T> success() {
        return body(MessageBuilder.getInstance().buildSuccessResponseMessage(null));
    }

    public StandardResponseMessageBuilder<T> success(T body) {
        return body(MessageBuilder.getInstance().buildSuccessResponseMessage(body));
    }

    public StandardResponseMessageBuilder<T> success(long totalItem, T body) {
        return body(MessageBuilder.getInstance().buildSuccessResponseMessage(totalItem, body));
    }

    public StandardResponseMessageBuilder<T> error() {
        return body(MessageBuilder.getInstance().buildErrorResponseMessage());
    }

    public StandardResponseMessageBuilder<T> error(ApplicationException businessException, T payload) {
        return error(businessException.getIdentification(), businessException.getMessage(), payload);
    }

    public StandardResponseMessageBuilder<T> error(ApplicationException businessException) {
        return error(businessException.getIdentification(), businessException.getMessage(), null);
    }

    public StandardResponseMessageBuilder<T> error(String returnCode, String returnMessage) {
        return error(returnCode, returnMessage, null);
    }

    public StandardResponseMessageBuilder<T> error(String returnCode, String returnMessage, T payload) {
        return error(returnCode, returnMessage, payload);
    }

    public StandardResponseHttpEntity<T> build() {
        return new StandardResponseHttpEntity<>(this.body, this.headers, this.httpStatus);
    }

}
