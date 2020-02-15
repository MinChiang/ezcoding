package com.ezcoding.common.foundation.core.exception.processor;

import com.ezcoding.common.foundation.core.exception.ApplicationException;
import org.springframework.http.HttpStatus;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-02-15 11:45
 */
public class WebDefaultApplicationExceptionProcessor extends WebFunctionLayerModuleProcessor {

    private HttpStatus defaultStatus;

    @Override
    public void doProcess(ApplicationException applicationException, WebProcessContext processContext) {
        processContext.setHttpStatus(defaultStatus == null ? HttpStatus.INTERNAL_SERVER_ERROR : defaultStatus);
        processContext.setReturnSummary(applicationException.getSummary());
    }

    public WebDefaultApplicationExceptionProcessor(HttpStatus defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    public HttpStatus getDefaultStatus() {
        return defaultStatus;
    }

    public void setDefaultStatus(HttpStatus defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

}
