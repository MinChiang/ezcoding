package com.ezcoding.common.foundation.core.log.impl;

import com.ezcoding.common.foundation.core.log.LogFormatter;
import com.ezcoding.common.foundation.core.log.LogMetadata;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-20 16:57
 */
public class DefaultLogFormatter implements LogFormatter {

    private LogFormatter logFormatter = new EmptyLogFormatter();

    @Override
    public String format(String expression, LogMetadata logMetadata, Object target, List<Object> args) {
        return this.logFormatter.format(expression, logMetadata, target, args);
    }

    public LogFormatter getLogFormatter() {
        return logFormatter;
    }

    public void setLogFormatter(LogFormatter logFormatter) {
        this.logFormatter = logFormatter;
    }

}
