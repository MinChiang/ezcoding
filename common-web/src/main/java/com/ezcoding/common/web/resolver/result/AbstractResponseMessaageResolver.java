package com.ezcoding.common.web.resolver.result;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-18 20:38
 */
public abstract class AbstractResponseMessaageResolver implements ResponseMessageReturnValueResolvable {

    private final Class<?> targetClass;

    public AbstractResponseMessaageResolver(Class<?> targetClass) {
        if (targetClass == null) {
            throw new IllegalArgumentException("target class can not be null");
        }
        this.targetClass = targetClass;
    }

    @Override
    public boolean match(Class<?> cls) {
        return targetClass.isAssignableFrom(cls);
    }

}
