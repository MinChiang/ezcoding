package com.ezcoding.common.web.resolver.result;

import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.common.foundation.core.message.ResponseSystemHead;
import com.ezcoding.common.foundation.core.message.SuccessAppHead;
import org.springframework.core.MethodParameter;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-18 20:46
 */
public class ResponseSystemHeadResolver extends AbstractResponseMessaageResolver {

    public ResponseSystemHeadResolver() {
        super(ResponseSystemHead.class);
    }

    @Override
    public ResponseMessage<Object> resolveReturnValue(Object returnValue, MethodParameter methodParameter) {
        return new ResponseMessage<>((ResponseSystemHead) returnValue, new SuccessAppHead(), null);
    }

}
