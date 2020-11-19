package com.ezcoding.common.foundation.core.log;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-04 14:30
 */
public class ParamLogInfo {

    private final String name;
    private final String expression;
    private final Object orginalTarget;
    private Object parseObject;

    public ParamLogInfo(String name, String expression, Object orginalTarget) {
        this.name = name;
        this.expression = expression;
        this.orginalTarget = orginalTarget;
    }

    public String getName() {
        return name;
    }

    public String getExpression() {
        return expression;
    }

    public Object getOrginalTarget() {
        return orginalTarget;
    }

    public Object getParseObject() {
        return parseObject;
    }

    public void setParseObject(Object parseObject) {
        this.parseObject = parseObject;
    }

}
