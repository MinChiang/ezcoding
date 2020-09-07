package com.ezcoding.common.security.advice;

import com.ezcoding.common.foundation.core.exception.processor.WebExceptionBuilderFactory;
import com.ezcoding.common.foundation.core.message.ResponseHttpEntity;
import com.ezcoding.common.foundation.core.message.ResponseMessageBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

import static com.ezcoding.common.foundation.core.exception.ExceptionCodeGeneratorConstants.GEN_COMMON_NO_PERMISSION_ERROR;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-18 14:16
 */
@Configuration
@RestControllerAdvice
public class SecurityAdviceConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityAdviceConfiguration.class);

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseHttpEntity<?> handleAccessDeniedException(AccessDeniedException e) throws IOException {
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("access deny:", e);
        }
        return ResponseMessageBuilder
                .ok()
                .error(WebExceptionBuilderFactory.webExceptionBuilder(GEN_COMMON_NO_PERMISSION_ERROR).build());
    }

}
