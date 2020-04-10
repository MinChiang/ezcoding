package com.ezcoding.common.web.advice;

import com.ezcoding.common.foundation.core.exception.ApplicationException;
import com.ezcoding.common.foundation.core.exception.processor.WebExceptionBuilderFactory;
import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.common.foundation.core.message.builder.IMessageBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Set;

import static com.ezcoding.common.foundation.core.exception.ExceptionCodeGeneratorConstants.*;

/**
 * 程序统一错误管理器
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-17 13:57
 */
@Configuration
@RestControllerAdvice
public class WebApplicationAdviceConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebApplicationAdviceConfiguration.class);

    @Autowired
    private IMessageBuilder messageBuilder;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseMessage<?> handleHttpRequestMethodNotSupportedExceptionException(HttpRequestMethodNotSupportedException e) throws IOException {
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("请求类型异常：", e);
        }
        return this.messageBuilder.buildErrorResponseMessage(
                WebExceptionBuilderFactory.webExceptionBuilder(GEN_COMMON_REQUEST_TYPE_ERROR).build()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseMessage<?> handleIllegalArgumentException(IllegalArgumentException e) throws IOException {
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("参数校验异常：", e);
        }
        return this.messageBuilder.buildErrorResponseMessage(
                WebExceptionBuilderFactory.webExceptionBuilder(GEN_COMMON_PARAM_VALIDATE_ERROR).build()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BindException.class)
    public ResponseMessage<?> handleBindException(BindException e) throws IOException {
        StringBuilder sb = new StringBuilder();
        e.getAllErrors().forEach(er -> sb.append(er.getDefaultMessage()));
        String result = sb.toString();
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("参数校验异常：{}", result);
        }
        return this.messageBuilder.buildErrorResponseMessage(
                WebExceptionBuilderFactory.webExceptionBuilder(GEN_COMMON_PARAM_VALIDATE_ERROR).params(result).build()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseMessage<?> handleConstraintViolationException(ConstraintViolationException e) throws IOException {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        StringBuilder stringBuilder = new StringBuilder();
        constraintViolations.forEach(c -> stringBuilder.append(c.getMessage()));
        String result = stringBuilder.toString();
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("参数校验异常：{}", result);
        }
        return this.messageBuilder.buildErrorResponseMessage(
                WebExceptionBuilderFactory.webExceptionBuilder(GEN_COMMON_PARAM_VALIDATE_ERROR).params(result).build()
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public ResponseMessage<?> handleNoHandlerFoundException(NoHandlerFoundException e) throws IOException {
        return this.messageBuilder.buildErrorResponseMessage(
                WebExceptionBuilderFactory.webExceptionBuilder(GEN_COMMON_RESOURCE_NOT_FIND_ERROR).build()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ApplicationException.class)
    public ResponseMessage<?> handleBusinessException(ApplicationException e) throws IOException {
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error(e.toString());
        }
        return this.messageBuilder.buildErrorResponseMessage(e, null);
    }

}
