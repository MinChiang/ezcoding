package com.ezcoding.common.web.advice;

import com.ezcoding.common.foundation.core.exception.ApplicationException;
import com.ezcoding.common.foundation.core.exception.processor.WebExceptionBuilderFactory;
import com.ezcoding.common.foundation.core.message.StandardResponseHttpEntity;
import com.ezcoding.common.foundation.core.message.StandardResponseMessageBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

import static com.ezcoding.common.foundation.core.exception.ExceptionCodeGeneratorConstants.*;

/**
 * 程序统一错误管理器
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-17 13:57
 */
@RestControllerAdvice
public class WebApplicationAdviceConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebApplicationAdviceConfig.class);

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public StandardResponseHttpEntity<?> handleHttpRequestMethodNotSupportedExceptionException(HttpRequestMethodNotSupportedException e) {
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("请求类型异常：", e);
        }
        return StandardResponseMessageBuilder
                .status(HttpStatus.NOT_ACCEPTABLE)
                .error(WebExceptionBuilderFactory.webExceptionBuilder(GEN_COMMON_REQUEST_TYPE_ERROR).build());
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public StandardResponseHttpEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("参数校验异常：", e);
        }
        return StandardResponseMessageBuilder
                .status(HttpStatus.BAD_REQUEST)
                .error(WebExceptionBuilderFactory.webExceptionBuilder(GEN_COMMON_PARAM_VALIDATE_ERROR).build());
    }

    @ExceptionHandler(value = BindException.class)
    public StandardResponseHttpEntity<?> handleBindException(BindException e) {
        StringBuilder sb = new StringBuilder();
        e.getAllErrors().forEach(er -> sb.append(er.getDefaultMessage()));
        String result = sb.toString();
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("参数校验异常：{}", result);
        }
        return StandardResponseMessageBuilder
                .status(HttpStatus.BAD_REQUEST)
                .error(WebExceptionBuilderFactory.webExceptionBuilder(GEN_COMMON_PARAM_VALIDATE_ERROR).params(result).build());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public StandardResponseHttpEntity<?> handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        StringBuilder stringBuilder = new StringBuilder();
        constraintViolations.forEach(c -> {
            stringBuilder.append(c.getMessage());
        });
        String result = stringBuilder.toString();
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("参数校验异常：{}", result);
        }
        return StandardResponseMessageBuilder
                .status(HttpStatus.BAD_REQUEST)
                .error(WebExceptionBuilderFactory.webExceptionBuilder(GEN_COMMON_PARAM_VALIDATE_ERROR).params(result).build());
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
    public StandardResponseHttpEntity<?> handleNoHandlerFoundException(NoHandlerFoundException e) {
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("未找到对应的处理器", e);
        }
        return StandardResponseMessageBuilder
                .status(HttpStatus.NOT_FOUND)
                .error(WebExceptionBuilderFactory.webExceptionBuilder(GEN_COMMON_RESOURCE_NOT_FIND_ERROR).build());
    }

    @ExceptionHandler(value = ApplicationException.class)
    public StandardResponseHttpEntity<?> handleBusinessException(ApplicationException e) {
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error(e.toString());
        }
        return StandardResponseMessageBuilder
                .status(HttpStatus.BAD_REQUEST)
                .error(e);
    }

}
