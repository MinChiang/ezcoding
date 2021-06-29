package com.ezcoding.common.web.resolver.parameter;

import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.foundation.util.ConvertUtils;
import com.ezcoding.common.web.resolver.StandardParam;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-20 10:31
 */
public class DefaultRequestMessageResolver extends AbstractRequestMessageResolver {

    private static final String PATH_PREFIX = "/";

    private ObjectMapper objectMapper = new ObjectMapper();

    public DefaultRequestMessageResolver(ObjectMapper objectMapper) {
        super(Object.class);
        this.objectMapper = objectMapper;
    }

    public DefaultRequestMessageResolver() {
        super(Object.class);
    }

    @Override
    public boolean match(Class<?> targetClass) {
        return true;
    }

    @Override
    public Object resolve(RequestMessage<JsonNode> requestMessage, StandardParam parameterAnnotation, MethodParameter methodParameter) {
        JsonNode body = requestMessage.getBody();
        String value = parameterAnnotation.value();
        Object result;
        if (value.isEmpty()) {
            //默认取对应的参数节点
            String parameterName = methodParameter.getParameterName();
            body = body.at(PATH_PREFIX + parameterName);
        } else {
            if (!value.startsWith(PATH_PREFIX)) {
                value = (PATH_PREFIX + value);
            }
            try {
                body = body.at(value);
            } catch (Exception e) {
                throw new RuntimeException("unknown path : [" + value + "]");
            }
        }
        if (body == null || body.isMissingNode()) {
            //查不到节点且参数必填，报错
            if (parameterAnnotation.required()) {
                throw new IllegalArgumentException("method [" + methodParameter.getMethod() + "] parameter [" + methodParameter.getParameterName() + "] must be provided");
            }
            String defaultValue = parameterAnnotation.defaultValue();
            result = defaultValue.length() > 0 ? convertPrimitive(methodParameter.getParameterType(), defaultValue) : null;
        } else {
            //解决参数类型中的泛型问题
            JavaType javaType = this.objectMapper.constructType(methodParameter.getGenericParameterType());
            try {
                result = this.objectMapper.convertValue(body, javaType);
            } catch (Exception e) {
                throw new IllegalArgumentException("can not convert [" + body + "] to [" + javaType + "]");
            }
        }
        return result;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private static Object convertPrimitive(Class<?> type, String value) {
        if (type == String.class) {
            return value;
        } else {
            return ConvertUtils.isPrimitive(type) ? ConvertUtils.convertToPrimitive(type, value) : null;
        }
    }

}
