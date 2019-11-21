package com.ezcoding.common.web.resolver.parameter;

import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.web.resolver.JsonParam;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.core.MethodParameter;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-20 10:30
 */
public class ReqeustMessageResolver extends AbstractRequestMessageResolver {

    public ReqeustMessageResolver() {
        super(RequestMessage.class);
    }

    @Override
    public Object resolveReturnValue(RequestMessage<JsonNode> requestMessage, JsonParam parameterAnnotation, MethodParameter methodParameter) {
        return requestMessage;
    }

}
