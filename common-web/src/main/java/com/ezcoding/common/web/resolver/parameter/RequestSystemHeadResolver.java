package com.ezcoding.common.web.resolver.parameter;

import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.foundation.core.message.head.RequestSystemHead;
import com.ezcoding.common.web.resolver.JsonParam;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.core.MethodParameter;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-20 9:30
 */
public class RequestSystemHeadResolver extends AbstractRequestMessageResolver {

    public RequestSystemHeadResolver() {
        super(RequestSystemHead.class);
    }

    @Override
    public Object resolveReturnValue(RequestMessage<JsonNode> requestMessage, JsonParam parameterAnnotation, MethodParameter methodParameter) {
        return requestMessage.getSystemHead();
    }

}
