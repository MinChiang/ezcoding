package com.ezcoding.common.web.advice;

import com.ezcoding.common.foundation.core.message.builder.IMessageBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-18 14:16
 */
@RestControllerAdvice
public class SecurityAdviceConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityAdviceConfig.class);

    @Autowired
    private IMessageBuilder messageBuilder;

//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    @ExceptionHandler(value = AccessDeniedException.class)
//    public ResponseMessage handleAccessDeniedException(AccessDeniedException e) throws IOException {
//        if (LOGGER.isErrorEnabled()) {
//            LOGGER.error("权限不足异常：", e);
//        }
//        return this.messageBuilder.buildErrorResponseMessage(CommonApplicationExceptionConstants.COMMON_NO_PERMISSION_ERROR, null);
//    }

}
