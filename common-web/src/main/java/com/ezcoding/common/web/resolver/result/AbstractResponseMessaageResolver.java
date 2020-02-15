package com.ezcoding.common.web.resolver.result;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-18 20:38
 */
public abstract class AbstractResponseMessaageResolver implements IResponseMessageReturnValueResolvable {

    private Class<?> targetClass;

    public AbstractResponseMessaageResolver(Class<?> targetClass) {
        if (targetClass == null) {
            throw new IllegalArgumentException("判断类型不能为空");
        }
        this.targetClass = targetClass;
    }

    @Override
    public boolean match(Class<?> cls) {
        return targetClass.isAssignableFrom(cls);
    }

}
