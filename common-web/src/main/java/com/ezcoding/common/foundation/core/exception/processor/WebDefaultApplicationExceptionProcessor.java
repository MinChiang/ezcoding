package com.ezcoding.common.foundation.core.exception.processor;

import com.ezcoding.common.foundation.core.exception.ApplicationException;
import org.springframework.http.HttpStatus;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-02-15 11:45
 */
public class WebDefaultApplicationExceptionProcessor extends AbstractWebFunctionLayerModuleProcessor {

    @Override
    public void doProcess(ApplicationException applicationException, WebProcessContext processContext) {
        processContext.setHttpStatus((HttpStatus) applicationException.getContext().get(WebExceptionBuilder.KEY_HTTP_STATUS));
        processContext.setReturnSummary(applicationException.getSummary());
    }

}
