package com.ezcoding.common.foundation.core.message;

import com.ezcoding.common.foundation.core.exception.ApplicationException;
import com.ezcoding.common.foundation.core.message.builder.MessageBuilder;
import com.ezcoding.common.foundation.core.message.head.ErrorAppHead;
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

    public static BodyBuilder status(HttpStatus status) {
        Assert.notNull(status, "HttpStatus must not be null");
        return new DefaultBuilder(status);
    }

    public static BodyBuilder ok() {
        return status(HttpStatus.OK);
    }

    public static <T> StandardResponseHttpEntity<T> ok(T body) {
        return ok().body(body);
    }

    public static BodyBuilder created(URI location) {
        return status(HttpStatus.CREATED).location(location);
    }

    public static BodyBuilder accepted() {
        return status(HttpStatus.ACCEPTED);
    }

    public static BodyBuilder noContent() {
        return status(HttpStatus.NO_CONTENT);
    }

    public static BodyBuilder badRequest() {
        return status(HttpStatus.BAD_REQUEST);
    }

    public static BodyBuilder notFound() {
        return status(HttpStatus.NOT_FOUND);
    }

    public static BodyBuilder unprocessableEntity() {
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
        return body(new ResponseSystemHead(), new ErrorAppHead(returnCode, returnMessage), payload);
    }

    public StandardResponseHttpEntity<T> build() {
        return new StandardResponseHttpEntity<>(this.body, this.headers, this.httpStatus);
    }

    public interface HeadersBuilder<B extends HeadersBuilder<B>> {

        B header(String headerName, String... headerValues);

        B headers(@Nullable HttpHeaders headers);

        B allow(HttpMethod... allowedMethods);

        B eTag(String etag);

        B lastModified(ZonedDateTime lastModified);

        B lastModified(Instant lastModified);

        B lastModified(long lastModified);

        B location(URI location);

        B cacheControl(CacheControl cacheControl);

        B varyBy(String... requestHeaders);

        <T> StandardResponseHttpEntity<T> build();

        StandardResponseHttpEntity<?> error();

        StandardResponseHttpEntity<?> error(ApplicationException exception);

    }

    public interface BodyBuilder extends HeadersBuilder<BodyBuilder> {

        BodyBuilder contentLength(long contentLength);

        BodyBuilder contentType(MediaType contentType);

        <T> StandardResponseHttpEntity<T> body(@Nullable T body);

    }

    private static class DefaultBuilder implements BodyBuilder {

        private final HttpStatus statusCode;
        private final HttpHeaders headers = new HttpHeaders();

        public DefaultBuilder(HttpStatus statusCode) {
            this.statusCode = statusCode;
        }

        @Override
        public BodyBuilder header(String headerName, String... headerValues) {
            for (String headerValue : headerValues) {
                this.headers.add(headerName, headerValue);
            }
            return this;
        }

        @Override
        public BodyBuilder headers(@Nullable HttpHeaders headers) {
            if (headers != null) {
                this.headers.putAll(headers);
            }
            return this;
        }

        @Override
        public BodyBuilder allow(HttpMethod... allowedMethods) {
            this.headers.setAllow(new LinkedHashSet<>(Arrays.asList(allowedMethods)));
            return this;
        }

        @Override
        public BodyBuilder contentLength(long contentLength) {
            this.headers.setContentLength(contentLength);
            return this;
        }

        @Override
        public BodyBuilder contentType(MediaType contentType) {
            this.headers.setContentType(contentType);
            return this;
        }

        @Override
        public BodyBuilder eTag(String etag) {
            if (!etag.startsWith("\"") && !etag.startsWith("W/\"")) {
                etag = "\"" + etag;
            }
            if (!etag.endsWith("\"")) {
                etag = etag + "\"";
            }
            this.headers.setETag(etag);
            return this;
        }

        @Override
        public BodyBuilder lastModified(ZonedDateTime date) {
            this.headers.setLastModified(date);
            return this;
        }

        @Override
        public BodyBuilder lastModified(Instant date) {
            this.headers.setLastModified(date);
            return this;
        }

        @Override
        public BodyBuilder lastModified(long date) {
            this.headers.setLastModified(date);
            return this;
        }

        @Override
        public BodyBuilder location(URI location) {
            this.headers.setLocation(location);
            return this;
        }

        @Override
        public BodyBuilder cacheControl(CacheControl cacheControl) {
            this.headers.setCacheControl(cacheControl);
            return this;
        }

        @Override
        public BodyBuilder varyBy(String... requestHeaders) {
            this.headers.setVary(Arrays.asList(requestHeaders));
            return this;
        }

        @Override
        public <T> StandardResponseHttpEntity<T> build() {
            return body(null);
        }

        @Override
        public <T> StandardResponseHttpEntity<T> body(@Nullable T body) {
            if (body instanceof ResponseMessage) {
                return new StandardResponseHttpEntity<T>((ResponseMessage<T>) body, this.headers, this.statusCode);
            } else {
                return new StandardResponseHttpEntity<T>(new ResponseMessage<>(body), this.headers, this.statusCode);
            }
        }

    }

}
