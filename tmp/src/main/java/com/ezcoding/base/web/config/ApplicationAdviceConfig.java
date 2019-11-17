package com.ezcoding.base.web.config;

import com.ezcoding.common.foundation.core.exception.AbstractApplicationException;
import com.ezcoding.common.foundation.core.exception.CommonApplicationExceptionConstants;
import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.common.foundation.core.message.builder.IMessageBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
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

/**
 * 程序统一错误管理器
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-17 13:57
 */
@RestControllerAdvice
public class ApplicationAdviceConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationAdviceConfig.class);

    @Autowired
    private IMessageBuilder messageBuilder;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseMessage handleHttpRequestMethodNotSupportedExceptionException(HttpRequestMethodNotSupportedException e) throws IOException {
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("请求类型异常：", e);
        }
        return this.messageBuilder.buildErrorResponseMessage(CommonApplicationExceptionConstants.COMMON_REQUEST_TYPE_ERROR, e.getCause());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseMessage handleIllegalArgumentException(IllegalArgumentException e) throws IOException {
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("参数校验异常：", e);
        }
        return this.messageBuilder.buildErrorResponseMessage(CommonApplicationExceptionConstants.COMMON_PARAM_VALIDATE_ERROR.instance().cause(e).build(), null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BindException.class)
    public ResponseMessage handleBindException(BindException e) throws IOException {
        StringBuilder sb = new StringBuilder();
        e.getAllErrors().forEach(er -> sb.append(er.getDefaultMessage()));
        String result = sb.toString();
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("参数校验异常：{}", result);
        }
        return this.messageBuilder.buildErrorResponseMessage(CommonApplicationExceptionConstants.COMMON_PARAM_VALIDATE_ERROR.instance().param(result).build(), null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseMessage handleConstraintViolationException(ConstraintViolationException e) throws IOException {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        StringBuilder stringBuilder = new StringBuilder();
        constraintViolations.forEach(c -> stringBuilder.append(c.getMessage()));
        String result = stringBuilder.toString();
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("参数校验异常：{}", result);
        }
        return this.messageBuilder.buildErrorResponseMessage(CommonApplicationExceptionConstants.COMMON_PARAM_VALIDATE_ERROR.instance().param(result).build(), null);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public ResponseMessage handleNoHandlerFoundException(NoHandlerFoundException e) throws IOException {
        return this.messageBuilder.buildErrorResponseMessage(CommonApplicationExceptionConstants.COMMON_RESOURCE_NOT_FIND_ERROR, null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = AbstractApplicationException.class)
    public ResponseMessage handleBusinessException(AbstractApplicationException e) throws IOException {
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("业务异常：", e);
        }
        return this.messageBuilder.buildErrorResponseMessage(e, null);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseMessage handleAccessDeniedException(AccessDeniedException e) throws IOException {
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("权限不足异常：", e);
        }
        return this.messageBuilder.buildErrorResponseMessage(CommonApplicationExceptionConstants.COMMON_NO_PERMISSION_ERROR, null);
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(value = Exception.class)
//    public ResponseMessage handleException(Exception e) throws IOException {
//        if (log.isErrorEnabled()) {
//            log.error("未知异常：", e);
//        }
//        return this.messageBuilder.buildErrorResponseMessage();
//    }

}
