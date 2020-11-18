package com.ezcoding.common.web.aspect.impl;

import com.ezcoding.common.web.aspect.LogParser;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-16 17:36
 */
public class SpelParameterParser implements LogParser {

    private static final ExpressionParser PARSER = new SpelExpressionParser();

    @Override
    public Object parse(ParamLogInfo paramLogInfo) {
        String exp = paramLogInfo.getExpression();
        String name = paramLogInfo.getName();
        Object orginalTarget;
        if (exp == null || exp.isEmpty()) {
            orginalTarget = paramLogInfo.getOrginalTarget();
        } else {
            orginalTarget = PARSER.parseExpression(exp).getValue(paramLogInfo.getOrginalTarget());
        }
        return orginalTarget;
    }

}
