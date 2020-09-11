package com.ezcoding.common.foundation.util;

import com.ezcoding.common.foundation.core.message.ResponseAppHead;
import com.ezcoding.common.foundation.core.message.ResponseMessage;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-28 9:02
 */
public class ResponseUtils {

    /**
     * 校验返回信息是否正确，若不正确则抛出异常
     *
     * @param responseMessage 返回信息
     * @param function        错误提供器
     * @param <E>             错误类型
     */
    public static <E extends Throwable> void check(ResponseMessage<?> responseMessage, Function<ResponseMessage<?>, E> function) throws E {
        if (!Optional
                .ofNullable(responseMessage)
                .map(message -> message.valid() && message.success())
                .orElse(false)) {
            throw Objects
                    .requireNonNull(function, "function can not be null")
                    .apply(responseMessage);
        }
    }

    /**
     * 校验返回信息是否正确，若不正确则抛出异常
     *
     * @param responseMessage 返回信息
     * @param function        错误提供器
     * @param <E>             错误类型
     */
    public static <E extends Throwable> void checkThrowWithContext(ResponseMessage<?> responseMessage, Function<ResponseAppHead, E> function) throws E {
        if (!Optional
                .ofNullable(responseMessage)
                .map(message -> message.valid() && message.success())
                .orElse(false)) {
            throw Objects
                    .requireNonNull(function, "function can not be null")
                    .apply(responseMessage.getAppHead());
        }
    }

    /**
     * 校验返回信息是否正确，若不正确则抛出异常
     *
     * @param responseMessage 返回信息
     */
    public static void check(ResponseMessage<?> responseMessage) {
        checkThrowWithContext(responseMessage, head -> new RuntimeException("response error with code [" + head.getReturnCode() + "] and message [" + head.getReturnMessage() + "]"));
    }

    /**
     * 校验返回信息是否正确，若不正确则抛出异常
     *
     * @param responseMessage 返回信息
     * @param function        错误提供器
     * @param <T>             信息载体内容类型
     * @param <E>             错误类型
     * @return 信息载体内容
     */
    public static <T, E extends Throwable> T checkAndGet(ResponseMessage<T> responseMessage, Function<ResponseMessage<?>, E> function) throws E {
        check(responseMessage, function);
        return responseMessage.getBody();
    }

    /**
     * 校验返回信息是否正确，若不正确则抛出异常
     *
     * @param responseMessage 返回信息
     * @param function        错误提供器
     * @param <T>             信息载体内容类型
     * @param <E>             错误类型
     * @return 信息载体内容
     */
    public static <T, E extends Throwable> T checkAndGetThrowWithContext(ResponseMessage<T> responseMessage, Function<ResponseAppHead, E> function) throws E {
        checkThrowWithContext(responseMessage, function);
        return responseMessage.getBody();
    }

    /**
     * 校验返回信息是否正确，若不正确则抛出异常
     *
     * @param responseMessage 返回信息
     * @param <T>             信息载体内容类型
     * @return 信息载体内容
     */
    public static <T> T checkAndGet(ResponseMessage<T> responseMessage) {
        check(responseMessage);
        return responseMessage.getBody();
    }

}
