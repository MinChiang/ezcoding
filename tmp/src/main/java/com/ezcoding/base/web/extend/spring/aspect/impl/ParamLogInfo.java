package com.ezcoding.base.web.extend.spring.aspect.impl;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-04 14:30
 */
public class ParamLogInfo {

    private String[] name;
    private String[] expression;
    private Object obj;

    public ParamLogInfo(String[] name, String[] expression, Object obj) {
        this.name = name;
        this.expression = expression;
        this.obj = obj;
    }

    public String[] getName() {
        return name;
    }

    public String[] getExpression() {
        return expression;
    }

    public Object getObj() {
        return obj;
    }
}
