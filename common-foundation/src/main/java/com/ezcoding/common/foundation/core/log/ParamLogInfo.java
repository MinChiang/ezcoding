package com.ezcoding.common.foundation.core.log;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-04 14:30
 */
public class ParamLogInfo {

    private final String expression;
    private final Object originalTarget;
    private Object parseObject;

    public ParamLogInfo(String expression, Object originalTarget) {
        this.expression = expression;
        this.originalTarget = originalTarget;
    }

    public String getExpression() {
        return expression;
    }

    public Object getOriginalTarget() {
        return originalTarget;
    }

    public Object getParseObject() {
        return parseObject;
    }

    public void setParseObject(Object parseObject) {
        this.parseObject = parseObject;
    }

}
