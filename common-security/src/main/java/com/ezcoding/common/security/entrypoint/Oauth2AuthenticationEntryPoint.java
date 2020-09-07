package com.ezcoding.common.security.entrypoint;

import com.ezcoding.common.foundation.core.exception.processor.WebExceptionBuilderFactory;
import com.ezcoding.common.foundation.core.message.builder.MessageFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;

import static com.ezcoding.common.foundation.core.exception.ExceptionCodeGeneratorConstants.GEN_COMMON_TOKEN_PARSE_ERROR;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-10-09 17:06
 */
public class Oauth2AuthenticationEntryPoint extends OAuth2AuthenticationEntryPoint {

    @Override
    protected ResponseEntity<?> enhanceResponse(ResponseEntity<?> response, Exception exception) {
        ResponseEntity<?> responseEntity = super.enhanceResponse(response, exception);
        exception.printStackTrace();

        return new ResponseEntity<Object>(
                MessageFactory.error(WebExceptionBuilderFactory.webExceptionBuilder(GEN_COMMON_TOKEN_PARSE_ERROR).build()).build(),
                responseEntity.getHeaders(),
                responseEntity.getStatusCode()
        );
    }

}
