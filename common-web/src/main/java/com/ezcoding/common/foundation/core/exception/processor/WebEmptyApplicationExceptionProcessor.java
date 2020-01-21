package com.ezcoding.common.foundation.core.exception.processor;

import com.ezcoding.common.foundation.core.exception.ApplicationException;
import com.ezcoding.common.foundation.core.message.head.ErrorAppHead;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-21 17:03
 */
public class WebEmptyApplicationExceptionProcessor extends EmptyApplicationExceptionProcessor {

    @Override
    public ProcessContext process(ApplicationException applicationException, ProcessContext processContext) {
        HttpServletResponse response = ((WebProcessContext) processContext).getResponse();
        try {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ErrorAppHead.getDefaultErrorMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.process(applicationException, processContext);
    }

}
