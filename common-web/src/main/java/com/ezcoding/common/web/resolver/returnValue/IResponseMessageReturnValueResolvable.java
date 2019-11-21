package com.ezcoding.common.web.resolver.returnValue;

import com.ezcoding.common.foundation.core.message.ResponseMessage;
import org.springframework.core.MethodParameter;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-18 20:31
 */
public interface IResponseMessageReturnValueResolvable {

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
     * @param returnValue     待解析的对象
     * @param methodParameter 方法参数
     * @return 解析后的报文
     */
    ResponseMessage<Object> resolveReturnValue(Object returnValue, MethodParameter methodParameter);

}
