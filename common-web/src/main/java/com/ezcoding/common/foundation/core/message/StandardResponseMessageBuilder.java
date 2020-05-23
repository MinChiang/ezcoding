package com.ezcoding.common.foundation.core.message;

import com.ezcoding.common.foundation.core.exception.ApplicationException;
import com.ezcoding.common.foundation.core.message.head.*;
import org.springframework.http.*;
import org.springframework.lang.Nullable;

import java.net.URI;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-13 15:18
 */
public class StandardResponseMessageBuilder<T> {

    public static BodyBuilder status(HttpStatus status) {
        return new DefaultBuilder(Optional.of(status).orElseThrow(() -> new IllegalArgumentException("状态码不能为空")));
    }

    public static BodyBuilder ok() {
        return status(HttpStatus.OK);
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

    }

    public interface BodyBuilder extends HeadersBuilder<BodyBuilder> {

        BodyBuilder contentLength(long contentLength);

        BodyBuilder contentType(MediaType contentType);

        <T> StandardResponseHttpEntity<T> message(ResponseSystemHead responseSystemHead, ResponseAppHead responseAppHead, T body);

        <T> StandardResponseHttpEntity<T> success(Long totalItem, T body);

        <T> StandardResponseHttpEntity<T> success(T body);

        StandardResponseHttpEntity<?> success();

        <T> StandardResponseHttpEntity<T> error(ApplicationException exception, T body);

        StandardResponseHttpEntity<?> error(ApplicationException exception);

        <T> StandardResponseHttpEntity<T> error(String returnCode, String returnMessage, T body);

        StandardResponseHttpEntity<?> error(String returnCode, String returnMessage);

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
        public <T> StandardResponseHttpEntity<T> message(ResponseSystemHead responseSystemHead, ResponseAppHead responseAppHead, T body) {
            return new StandardResponseHttpEntity<>(new ResponseMessage<>(responseSystemHead, responseAppHead, body), this.headers, this.statusCode);
        }

        @Override
        public <T> StandardResponseHttpEntity<T> success(Long totalItem, T body) {
            return message(new ResponseSystemHead(), new SuccessAppHead(new PageInfo(totalItem)), body);
        }

        @Override
        public <T> StandardResponseHttpEntity<T> success(T body) {
            return success(null, body);
        }

        @Override
        public StandardResponseHttpEntity<?> success() {
            return success(null);
        }

        @Override
        public <T> StandardResponseHttpEntity<T> error(String returnCode, String returnMessage, T body) {
            return message(new ResponseSystemHead(), new ErrorAppHead(returnCode, returnMessage), body);
        }

        @Override
        public StandardResponseHttpEntity<?> error(String returnCode, String returnMessage) {
            return error(returnCode, returnMessage, null);
        }

        @Override
        public <T> StandardResponseHttpEntity<T> error(ApplicationException exception, T body) {
            return error(exception.getIdentification(), exception.getMessage(), body);
        }

        @Override
        public StandardResponseHttpEntity<?> error(ApplicationException exception) {
            return error(exception, null);
        }

    }

}
