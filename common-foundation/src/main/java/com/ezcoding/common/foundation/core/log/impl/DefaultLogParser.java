package com.ezcoding.common.foundation.core.log.impl;

import com.ezcoding.common.foundation.core.log.LogParser;
import com.ezcoding.common.foundation.core.log.ParamLogger;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-20 17:00
 */
public class DefaultLogParser implements LogParser {

    private LogParser logParser = new EmptyLogParser();

    @Override
    public List<Object> parse(ParamLogger paramLogger, Object target) {
        return logParser.parse(paramLogger, target);
    }

    public LogParser getLogParser() {
        return logParser;
    }

    public void setLogParser(LogParser logParser) {
        this.logParser = logParser;
    }

}
