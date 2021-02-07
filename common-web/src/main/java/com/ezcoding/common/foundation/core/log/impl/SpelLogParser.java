package com.ezcoding.common.foundation.core.log.impl;

import com.ezcoding.common.foundation.core.log.LogParser;
import com.ezcoding.common.foundation.core.log.ParamLogMetadata;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-16 17:36
 */
public class SpelLogParser implements LogParser {

    private static final ExpressionParser PARSER = new SpelExpressionParser();

    @Override
    public Object parse(String expression, ParamLogMetadata paramLogMetadata, Object target) {
        if (expression == null || expression.isEmpty()) {
            return target;
        } else {
            return PARSER.parseExpression(expression).getValue(target);
        }
    }

}
