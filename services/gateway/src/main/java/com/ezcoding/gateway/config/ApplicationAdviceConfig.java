package com.ezcoding.gateway.config;

import com.ezcoding.common.foundation.core.exception.CommonApplicationExceptionConstants;
import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.common.foundation.core.message.builder.MessageBuilder;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 程序统一错误管理器
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-17 13:57
 */
@RestControllerAdvice
public class ApplicationAdviceConfig {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public ResponseMessage handleNoHandlerFoundException(NoHandlerFoundException e) {
        return MessageBuilder.getInstance().buildErrorResponseMessage(CommonApplicationExceptionConstants.COMMON_RESOURCE_NOT_FIND_ERROR, null);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = JwtException.class)
    public ResponseMessage handleJwtException(JwtException e) {
        return MessageBuilder.getInstance().buildErrorResponseMessage(CommonApplicationExceptionConstants.COMMON_TOKEN_PARSE_ERROR, null);
    }

}
