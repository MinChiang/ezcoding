package com.ezcoding.common.foundation.core.log;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-16 17:36
 */
public class SpelLogParser implements LogParser {

    private static final ExpressionParser PARSER = new SpelExpressionParser();

    @Override
    public List<Object> parse(ParamLogger paramLogger, Object target) {
        List<Object> result = new ArrayList<>();
        ParamLogMetadata paramLogMetadata = paramLogger.getParamLogMetadata();
        for (String exp : paramLogMetadata.expressions) {
            if (exp == null || exp.isEmpty()) {
                result.add(target);
            } else {
                result.add(PARSER.parseExpression(exp).getValue(target));
            }
        }
        return result;
    }

}
