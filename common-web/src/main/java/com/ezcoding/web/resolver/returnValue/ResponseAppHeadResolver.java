package com.ezcoding.web.resolver.returnValue;

import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.common.foundation.core.message.head.ResponseAppHead;
import com.ezcoding.common.foundation.core.message.head.ResponseSystemHead;
import org.springframework.core.MethodParameter;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-18 20:48
 */
public class ResponseAppHeadResolver extends AbstractMessaageResolver {

    public ResponseAppHeadResolver(Class<?> targetClass) {
        super(targetClass);
    }

    @Override
    public ResponseMessage resolveReturnValue(Object returnValue, MethodParameter methodParameter) {
        return new ResponseMessage<>(new ResponseSystemHead(), (ResponseAppHead) returnValue, null);
    }

}
