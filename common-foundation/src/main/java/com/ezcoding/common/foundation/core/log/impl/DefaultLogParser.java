package com.ezcoding.common.foundation.core.log.impl;

import com.ezcoding.common.foundation.core.log.LogParser;
import com.ezcoding.common.foundation.core.log.ParamLogMetadata;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-20 17:00
 */
public class DefaultLogParser implements LogParser {

    private LogParser logParser = new EmptyLogParser();

    @Override
    public Object parse(String expression, ParamLogMetadata paramLogMetadata, Object target) {
        return logParser.parse(expression, paramLogMetadata, target);
    }

    public LogParser getLogParser() {
        return logParser;
    }

    public void setLogParser(LogParser logParser) {
        this.logParser = logParser;
    }

}
