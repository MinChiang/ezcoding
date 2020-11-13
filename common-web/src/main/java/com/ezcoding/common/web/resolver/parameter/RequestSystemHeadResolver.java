package com.ezcoding.common.web.resolver.parameter;

import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.foundation.core.message.RequestSystemHead;
import com.ezcoding.common.web.resolver.JsonParam;
import org.springframework.core.MethodParameter;

import java.util.Map;

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
    public Object resolve(RequestMessage<Map<String, ?>> requestMessage, JsonParam parameterAnnotation, MethodParameter methodParameter) {
        return requestMessage.getSystemHead();
    }

}
