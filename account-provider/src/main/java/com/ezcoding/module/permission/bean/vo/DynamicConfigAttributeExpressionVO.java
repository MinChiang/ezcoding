package com.ezcoding.module.permission.bean.vo;

import com.ezcoding.common.security.configattribute.DynamicConfigAttribute;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-29 10:26
 */
public class DynamicConfigAttributeExpressionVO {

    private DynamicConfigAttribute dynamicConfigAttribute;

    private String expression;

    public DynamicConfigAttribute getDynamicConfigAttribute() {
        return dynamicConfigAttribute;
    }

    public void setDynamicConfigAttribute(DynamicConfigAttribute dynamicConfigAttribute) {
        this.dynamicConfigAttribute = dynamicConfigAttribute;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

}
