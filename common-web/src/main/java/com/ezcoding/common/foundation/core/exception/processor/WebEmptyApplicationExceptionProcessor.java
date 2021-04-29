package com.ezcoding.common.foundation.core.exception.processor;

import com.ezcoding.common.foundation.core.exception.ApplicationException;
import com.ezcoding.common.foundation.core.message.ErrorAppHead;
import org.springframework.http.HttpStatus;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-21 17:03
 */
public class WebEmptyApplicationExceptionProcessor extends AbstractWebFunctionLayerModuleProcessor {

    private HttpStatus defaultStatus;
    private String defaultReturnSummary;

    public WebEmptyApplicationExceptionProcessor() {
    }

    public WebEmptyApplicationExceptionProcessor(HttpStatus defaultStatus, String defaultReturnSummary) {
        this.defaultStatus = defaultStatus;
        this.defaultReturnSummary = defaultReturnSummary;
    }

    @Override
    public void doProcess(ApplicationException applicationException, WebProcessContext processContext) {
        processContext.setHttpStatus(defaultStatus == null ? HttpStatus.INTERNAL_SERVER_ERROR : defaultStatus);
        processContext.setReturnSummary(defaultReturnSummary == null ? ErrorAppHead.DEFAULT_ERROR_MESSAGE : defaultReturnSummary);
    }

}
