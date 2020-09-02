package com.ezcoding.common.web.error;

import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.common.foundation.core.message.head.ErrorAppHead;
import com.ezcoding.common.foundation.core.message.head.ResponseSystemHead;
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

    public ApplicationErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties, List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorProperties, errorViewResolvers);
    }

    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
        HttpStatus status = getStatus(request);

        Map<String, Object> map = new HashMap<>(2);
        map.put(ResponseMessage.SYS_HEAD, new ResponseSystemHead());
        map.put(ResponseMessage.APP_HEAD, new ErrorAppHead(
                (String) body.getOrDefault(KEY_APPLICATION_EXCEPTION_IDENTIFICATION, ErrorAppHead.getDefaultErrorCode()),
                (String) body.getOrDefault("message", ErrorAppHead.getDefaultErrorMessage())
        ));
        return new ResponseEntity<>(map, status);
    }

}
