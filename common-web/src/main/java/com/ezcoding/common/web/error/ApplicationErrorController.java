package com.ezcoding.common.web.error;

import com.ezcoding.common.foundation.core.exception.ApplicationException;
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
import java.util.List;
import java.util.Map;

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
        ApplicationException applicationException = (ApplicationException) body.get("applicationException");
        String returnCode = ErrorAppHead.getDefaultErrorCode();
        String returnMessage = ErrorAppHead.getDefaultErrorMessage();
        if (applicationException != null) {
            returnCode = applicationException.getIdentification();
            returnMessage = applicationException.getSummary();
        }
        ResponseMessage<Map<String, Object>> responseMessage = new ResponseMessage<>(new ResponseSystemHead(), new ErrorAppHead(returnCode, returnMessage), null);
        return new ResponseEntity<>(responseMessage.toMap(), status);
    }

}
