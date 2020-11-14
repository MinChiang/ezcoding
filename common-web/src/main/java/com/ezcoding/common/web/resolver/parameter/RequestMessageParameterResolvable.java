package com.ezcoding.common.web.resolver.parameter;

import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.web.resolver.JsonParam;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.core.MethodParameter;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-18 20:31
 */
public interface RequestMessageParameterResolvable {

    /**
     * 判断本类是否可解析输入类型
     *
     * @param cls 输入的类型
     * @return 是否可以解析输入类型
     */
    boolean match(Class<?> cls);

    /**
     * 对象解析为输出报文
     *
     * @param requestMessage      请求内容
     * @param parameterAnnotation 方法级注解
     * @param methodParameter     方法参数
     * @return 解析后的报文
     */
    Object resolve(RequestMessage<JsonNode> requestMessage, JsonParam parameterAnnotation, MethodParameter methodParameter) throws Exception;

}
