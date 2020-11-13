package com.ezcoding.common.web.resolver.parameter;

import com.ezcoding.common.foundation.core.message.RequestAppHead;
import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.web.resolver.JsonParam;
import org.springframework.core.MethodParameter;

import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-20 10:28
 */
public class ResquestAppHeadResolver extends AbstractRequestMessageResolver {

    public ResquestAppHeadResolver() {
        super(RequestAppHead.class);
    }

    @Override
    public Object resolve(RequestMessage<Map<String, ?>> requestMessage, JsonParam parameterAnnotation, MethodParameter methodParameter) {
        return requestMessage.getAppHead();
    }

}
