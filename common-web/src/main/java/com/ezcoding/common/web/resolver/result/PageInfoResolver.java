package com.ezcoding.common.web.resolver.result;

import com.ezcoding.common.foundation.core.message.MessageFactory;
import com.ezcoding.common.foundation.core.message.PageInfo;
import com.ezcoding.common.foundation.core.message.ResponseMessage;
import org.springframework.core.MethodParameter;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-02-04 14:42
 */
public class PageInfoResolver extends AbstractResponseMessaageResolver {

    public PageInfoResolver() {
        super(PageInfo.class);
    }

    @Override
    public ResponseMessage<Object> resolveReturnValue(Object returnValue, MethodParameter methodParameter) {
        return MessageFactory.buildSuccessResponseMessage();
    }

}
