package com.ezcoding.common.web.error;

import com.ezcoding.common.foundation.core.exception.ApplicationException;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-20 15:25
 */
public class ApplicationExceptionErrorAttributes extends DefaultErrorAttributes {

    public static final String KEY_APPLICATION_EXPCETION = "com.ezcoding.common.foundation.core.exception.ApplicationException";

    public ApplicationExceptionErrorAttributes() {
        this(false);
    }

    public ApplicationExceptionErrorAttributes(boolean includeException) {
        super(includeException);
    }

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
        this.addStatus(errorAttributes, webRequest);
        return errorAttributes;
    }

    private void addStatus(Map<String, Object> errorAttributes, RequestAttributes requestAttributes) {
        ApplicationException applicationException = (ApplicationException) requestAttributes.getAttribute(KEY_APPLICATION_EXPCETION, RequestAttributes.SCOPE_REQUEST);
        errorAttributes.put("applicationException", applicationException);
    }

}
