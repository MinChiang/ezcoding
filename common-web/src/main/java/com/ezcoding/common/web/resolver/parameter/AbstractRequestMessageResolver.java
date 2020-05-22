package com.ezcoding.common.web.resolver.parameter;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-20 9:09
 */
public abstract class AbstractRequestMessageResolver implements RequestMessageParameterResolvable {

    private Class<?> targetClass;

    public AbstractRequestMessageResolver(Class<?> targetClass) {
        if (targetClass == null) {
            throw new IllegalArgumentException("判断类型不能为空");
        }
        this.targetClass = targetClass;
    }

    @Override
    public boolean match(Class<?> targetClass) {
        return this.targetClass.isAssignableFrom(targetClass);
    }

}
