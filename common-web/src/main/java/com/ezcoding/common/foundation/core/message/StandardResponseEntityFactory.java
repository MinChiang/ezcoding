package com.ezcoding.common.foundation.core.message;

import com.ezcoding.common.foundation.core.exception.ApplicationException;
import org.springframework.http.*;

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
public class StandardResponseEntityFactory<T> {

    public static BodyBuilder status(HttpStatus status) {
        return new DefaultBuilder(Optional.of(status).orElseThrow(() -> new IllegalArgumentException("status can't be empty")));
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

    public static HeadersBuilder<?> noContent() {
        return status(HttpStatus.NO_CONTENT);
    }

    public static BodyBuilder badRequest() {
        return status(HttpStatus.BAD_REQUEST);
    }

    public static HeadersBuilder<?> notFound() {
        return status(HttpStatus.NOT_FOUND);
    }

    public static BodyBuilder unprocessableEntity() {
        return status(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public interface HeadersBuilder<B extends HeadersBuilder<B>> {

        /**
         * 设置头
         *
         * @param headerName   头名称
         * @param headerValues 头的值
         * @return 构造器
         */
        B header(String headerName, String... headerValues);

        /**
         * 设置头
         *
         * @param headers 头
         * @return 构造器
         */
        B headers(HttpHeaders headers);

        /**
         * 设置请求方法
         *
         * @param allowedMethods 请求方法
         * @return 构造器
         */
        B allow(HttpMethod... allowedMethods);

        /**
         * 设置etag
         *
         * @param etag 待设置的值
         * @return 构造器
         */
        B eTag(String etag);

        /**
         * 设置上次修改时间
         *
         * @param lastModified 上次修改的时间
         * @return 构造器
         */
        B lastModified(ZonedDateTime lastModified);

        /**
         * 设置上次修改时间
         *
         * @param lastModified 上次修改的时间
         * @return 构造器
         */
        B lastModified(Instant lastModified);

        /**
         * 设置上次修改时间
         *
         * @param lastModified 上次修改的时间
         * @return 构造器
         */
        B lastModified(long lastModified);

        /**
         * 设置定位
         *
         * @param location 定位位置
         * @return 构造器
         */
        B location(URI location);

        /**
         * 设置缓存控制
         *
         * @param cacheControl 缓存控制对象
         * @return 构造器
         */
        B cacheControl(CacheControl cacheControl);

        /**
         * 设置vary
         *
         * @param requestHeaders 设置的头值
         * @return 构造器
         */
        B varyBy(String... requestHeaders);

        /**
         * 构造标准的响应对象
         *
         * @return 标准的响应对象
         */
        StandardResponseEntity<?> build();

    }

    public interface BodyBuilder extends HeadersBuilder<BodyBuilder> {

        /**
         * 设置内容长度
         *
         * @param contentLength 设置的内容长度
         * @return 响应体构造器
         */
        BodyBuilder contentLength(long contentLength);

        /**
         * 设置返回格式
         *
         * @param contentType 设置的返回格式
         * @return 响应体构造器
         */
        BodyBuilder contentType(MediaType contentType);

        /**
         * 返回成功标准响应消息
         *
         * @param totalItem 内容长度
         * @param body      响应体
         * @param <T>       响应内容泛型
         * @return 响应的成功标准消息
         */
        <T> StandardResponseEntity<T> success(Integer totalItem, T body);

        /**
         * 返回成功标准响应消息
         *
         * @param body 响应体
         * @param <T>  响应内容泛型
         * @return 响应的成功标准消息
         */
        <T> StandardResponseEntity<T> success(T body);

        /**
         * 返回成功标准响应消息
         *
         * @return 响应的成功标准消息
         */
        StandardResponseEntity<?> success();

        /**
         * 返回错误标准响应消息
         *
         * @param exception 错误
         * @param body      响应体
         * @param <T>       响应内容泛型
         * @return 响应的错误标准消息
         */
        <T> StandardResponseEntity<T> error(ApplicationException exception, T body);

        /**
         * 返回错误标准响应消息
         *
         * @param exception 错误
         * @return 响应的错误标准消息
         */
        StandardResponseEntity<?> error(ApplicationException exception);

//        /**
//         * 返回错误标准响应消息
//         *
//         * @param returnCode    响应错误码
//         * @param returnMessage 响应错误信息
//         * @param body          响应体
//         * @param <T>           响应内容泛型
//         * @return 响应的错误标准消息
//         */
//        <T> StandardResponseHttpEntity<T> error(String returnCode, String returnMessage, T body);
//
//        /**
//         * 返回错误标准响应消息
//         *
//         * @param returnCode    响应错误码
//         * @param returnMessage 响应错误信息
//         * @return 响应的错误标准消息
//         */
//        StandardResponseHttpEntity<?> error(String returnCode, String returnMessage);

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
        public BodyBuilder headers(HttpHeaders headers) {
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
        public StandardResponseEntity<?> build() {
            return success();
        }

//        @Override
//        public <T> StandardResponseEntity<T> message(ResponseSystemHead responseSystemHead, ResponseAppHead responseAppHead, T body) {
//            return new StandardResponseEntity<>(new ResponseMessage<>(responseSystemHead, responseAppHead, body), this.headers, this.statusCode);
//        }

        @Override
        public <T> StandardResponseEntity<T> success(Integer totalItem, T body) {
            return new StandardResponseEntity<>(MessageFactory.buildSuccessResponseMessage(totalItem, body), this.headers, this.statusCode);
        }

        @Override
        public <T> StandardResponseEntity<T> success(T body) {
            return success(null, body);
        }

        @Override
        public StandardResponseEntity<?> success() {
            return success(null);
        }

//        @Override
//        public <T> StandardResponseHttpEntity<T> error(String returnCode, String returnMessage, T body) {
//            return message(new ResponseSystemHead(), new ErrorAppHead(returnCode, returnMessage), body);
//        }
//
//        @Override
//        public StandardResponseHttpEntity<?> error(String returnCode, String returnMessage) {
//            return error(returnCode, returnMessage, null);
//        }

        @Override
        public <T> StandardResponseEntity<T> error(ApplicationException exception, T body) {
            return new StandardResponseEntity<>(MessageFactory.buildErrorResponseMessage(exception, body), this.headers, this.statusCode);
        }

        @Override
        public StandardResponseEntity<?> error(ApplicationException exception) {
            return error(exception, null);
        }

    }

}
