package com.ezcoding.common.foundation.core.log.impl;

import com.ezcoding.common.foundation.core.log.LogFormatter;
import com.ezcoding.common.foundation.core.log.ServiceLogger;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-20 16:57
 */
public class DefaultLogFormatter implements LogFormatter {

    private LogFormatter logFormatter = new EmptyLogFormatter();

    @Override
    public String format(String expression, ServiceLogger serviceLogger, Object target, Object[] objects) {
        return this.logFormatter.format(expression, serviceLogger, target, objects);
    }

    public LogFormatter getLogFormatter() {
        return logFormatter;
    }

    public void setLogFormatter(LogFormatter logFormatter) {
        this.logFormatter = logFormatter;
    }

}
