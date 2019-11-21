package com.ezcoding.common.web.resolver.returnValue;

import com.ezcoding.common.foundation.core.message.ResponseMessage;
import org.springframework.core.MethodParameter;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-18 20:45
 */
public class ResponseMessageResolver extends AbstractResponseMessaageResolver {

    public ResponseMessageResolver() {
        super(ResponseMessage.class);
    }

    @Override
    public ResponseMessage<Object> resolveReturnValue(Object returnValue, MethodParameter methodParameter) {
        return (ResponseMessage<Object>) returnValue;
    }

}
