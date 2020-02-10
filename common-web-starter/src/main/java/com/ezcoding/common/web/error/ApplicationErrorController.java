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

        String returnCode;
        String returnMessage;
        if (applicationException != null) {
            returnMessage = applicationException.getSummary();
            returnCode = applicationException.getIdentification();
        } else {
            returnMessage = ErrorAppHead.getDefaultErrorMessage();
            returnCode = ErrorAppHead.getDefaultErrorCode();
        }

        String errorMesssage = getErrorMesssage(request);
        ResponseMessage<Map<String, Object>> responseMessage = new ResponseMessage<>(
                new ResponseSystemHead(),
                new ErrorAppHead(returnCode, errorMesssage == null ? returnMessage : errorMesssage),
                null
        );
        return new ResponseEntity<>(responseMessage.toMap(), status);
    }

    /**
     * 获取错误信息
     *
     * @param request 请求实体
     * @return 请求中的错误内容
     */
    private String getErrorMesssage(HttpServletRequest request) {
        return (String) request.getAttribute("javax.servlet.error.message");
    }

}
