package com.ezcoding.common.web.aspect.impl;

import java.lang.reflect.Method;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-04 14:45
 */
public class ServiceLogInfo {

    private final Class<?> implementClass;
    private final String expression;
    private final Method target;

    public ServiceLogInfo(Class<?> implementClass, String expression, Method target) {
        this.implementClass = implementClass;
        this.expression = expression;
        this.target = target;
    }

    public Class<?> getImplementClass() {
        return implementClass;
    }

    public String getExpression() {
        return expression;
    }

    public Method getTarget() {
        return target;
    }
}
