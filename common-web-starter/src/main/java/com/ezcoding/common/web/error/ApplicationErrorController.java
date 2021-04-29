package com.ezcoding.common.web.error;

import com.ezcoding.common.foundation.core.message.ErrorAppHead;
import com.ezcoding.common.foundation.core.message.MessageFactory;
import com.ezcoding.common.foundation.core.message.ResponseMessage;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ezcoding.common.web.error.ApplicationExceptionErrorAttributes.KEY_APPLICATION_EXCEPTION_IDENTIFICATION;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-26 23:19
 */
public class ApplicationErrorController extends BasicErrorController {

    public static final String MESSAGE_KEY = "message";
    public static final String SYSTEM_HEAD = "systemHead";
    public static final String APP_HEAD = "appHead";

    public ApplicationErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties, List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorProperties, errorViewResolvers);
    }

    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
        HttpStatus status = getStatus(request);

        ResponseMessage<?> errorResponseMessage = MessageFactory.buildErrorResponseMessage(
                (String) body.getOrDefault(KEY_APPLICATION_EXCEPTION_IDENTIFICATION, ErrorAppHead.DEFAULT_ERROR_CODE),
                (String) body.getOrDefault(MESSAGE_KEY, ErrorAppHead.DEFAULT_ERROR_MESSAGE)
        );

        Map<String, Object> map = new HashMap<>(2);
        map.put(SYSTEM_HEAD, errorResponseMessage.getSystemHead());
        map.put(APP_HEAD, errorResponseMessage.getAppHead());
        return new ResponseEntity<>(map, status);
    }

}
