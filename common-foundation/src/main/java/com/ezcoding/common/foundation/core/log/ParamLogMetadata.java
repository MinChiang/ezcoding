package com.ezcoding.common.foundation.core.log;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-24 17:42
 */
public class ParamLogMetadata {

    final String[] expressions;
    final Class<? extends LogParser> parseClass;

    public ParamLogMetadata(String[] expressions, Class<? extends LogParser> parseClass) {
        this.expressions = expressions;
        this.parseClass = parseClass;
    }

    public String[] getExpressions() {
        return expressions;
    }

    public Class<? extends LogParser> getParseClass() {
        return parseClass;
    }

}
