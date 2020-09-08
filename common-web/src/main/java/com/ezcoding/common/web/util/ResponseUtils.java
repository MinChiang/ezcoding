package com.ezcoding.common.web.util;

import com.ezcoding.common.foundation.core.exception.ApplicationException;
import com.ezcoding.common.foundation.core.exception.processor.WebExceptionBuilderFactory;
import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.common.foundation.core.message.ResponseAppHead;
import com.ezcoding.common.foundation.util.AssertUtils;

import java.util.Optional;
import java.util.function.Supplier;

import static com.ezcoding.common.foundation.core.exception.ExceptionCodeGeneratorConstants.GEN_COMMON_REMOTE_REQUEST_ERROR;

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
     * @param supplier        错误提供器
     * @param <T>             信息载体内容类型
     * @return 信息载体内容
     */
    public static <T> T checkAndGetResult(ResponseMessage<T> responseMessage, Supplier<? extends ApplicationException> supplier) {
        AssertUtils.mustTrue(responseMessage != null && responseMessage.success(), Optional.of(supplier).get());
        return responseMessage.getBody();
    }

    /**
     * 校验返回信息是否正确，若不正确则抛出异常
     *
     * @param responseMessage 返回信息
     * @param <T>             信息载体内容类型
     * @return 信息载体内容
     */
    public static <T> T checkAndGetResult(ResponseMessage<T> responseMessage) {
        return checkAndGetResult(responseMessage, () -> {
            String error = Optional.of(responseMessage).map(ResponseMessage::getAppHead).map(ResponseAppHead::getReturnMessage).orElse(null);
            return WebExceptionBuilderFactory.webExceptionBuilder(GEN_COMMON_REMOTE_REQUEST_ERROR).addOriginalParams(error).build();
        });
    }

}
