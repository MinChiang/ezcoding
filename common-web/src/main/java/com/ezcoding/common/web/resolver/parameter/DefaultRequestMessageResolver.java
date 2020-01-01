package com.ezcoding.common.web.resolver.parameter;

import com.ezcoding.common.foundation.core.exception.CommonApplicationException;
import com.ezcoding.common.foundation.core.exception.BaseModuleExceptionBuilderFactory;
import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.foundation.util.ConvertUtils;
import com.ezcoding.common.web.resolver.JsonParam;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;

import static com.ezcoding.common.foundation.core.exception.CommonApplicationException.COMMON_PARAM_PARSE_ERROR;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-20 10:31
 */
public class DefaultRequestMessageResolver extends AbstractRequestMessageResolver {

    private static final String PATH_PREFIX = "/";

    private ObjectMapper objectMapper;

    public DefaultRequestMessageResolver(ObjectMapper objectMapper) {
        super(Object.class);
        this.objectMapper = objectMapper;
    }

    @Override
    public Object resolveReturnValue(RequestMessage<JsonNode> requestMessage, JsonParam parameterAnnotation, MethodParameter methodParameter) {
        JsonNode payload = requestMessage.getPayload();
        String value = parameterAnnotation.value();
        Object result = null;
        if (StringUtils.isNotBlank(value)) {
            if (!value.startsWith(PATH_PREFIX)) {
                value = (PATH_PREFIX + value);
            }
            try {
                payload = payload.at(value);
            } catch (Exception e) {
                throw BaseModuleExceptionBuilderFactory.lookupByAlias(CommonApplicationException.class, COMMON_PARAM_PARSE_ERROR).instance().param("未知的参数路径" + value).cause(e).build();
            }
        }
        if (payload == null || payload.isMissingNode()) {
            //查不到节点且参数必填，报错
            if (parameterAnnotation.required()) {
                throw new IllegalArgumentException("方法" + methodParameter.getMethod() + "参数" + methodParameter.getParameterName() + "为必输");
            }
            result = ConvertUtils.convert(parameterAnnotation.defaultValue(), methodParameter.getParameterType());
        } else {
            //解决参数类型中的泛型问题
            JavaType javaType = this.objectMapper.constructType(methodParameter.getGenericParameterType());
            try {
                result = this.objectMapper.convertValue(payload, javaType);
            } catch (Exception e) {
                throw BaseModuleExceptionBuilderFactory.lookupByAlias(CommonApplicationException.class, COMMON_PARAM_PARSE_ERROR).instance().param("无法将" + payload + "转化为类型" + javaType).cause(e).build();
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

}
