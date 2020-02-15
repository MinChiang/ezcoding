package com.ezcoding.common.web.error;

import com.ezcoding.common.foundation.core.exception.ApplicationException;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.Optional;

import static com.ezcoding.common.foundation.core.exception.processor.ApplicationExceptionResolver.KEY_APPLICATION_EXPCETION;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-20 15:25
 */
public class ApplicationExceptionErrorAttributes extends DefaultErrorAttributes {

    public static final String KEY_APPLICATION_EXCEPTION_IDENTIFICATION = KEY_APPLICATION_EXPCETION + "@identification";
    public static final String KEY_APPLICATION_EXCEPTION_SUMMARY = KEY_APPLICATION_EXPCETION + "@summary";

    public ApplicationExceptionErrorAttributes() {
        this(false);
    }

    public ApplicationExceptionErrorAttributes(boolean includeException) {
        super(includeException);
    }

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
        this.addApplicationExceptionDetail(errorAttributes, webRequest);
        return errorAttributes;
    }

    private void addApplicationExceptionDetail(Map<String, Object> errorAttributes, RequestAttributes requestAttributes) {
        Optional
                .ofNullable((ApplicationException) requestAttributes.getAttribute(KEY_APPLICATION_EXPCETION, RequestAttributes.SCOPE_REQUEST))
                .ifPresent(e -> {
                    errorAttributes.put(KEY_APPLICATION_EXCEPTION_IDENTIFICATION, e.getIdentification());
                    errorAttributes.put(KEY_APPLICATION_EXCEPTION_SUMMARY, e.getSummary());
                });
    }

}
