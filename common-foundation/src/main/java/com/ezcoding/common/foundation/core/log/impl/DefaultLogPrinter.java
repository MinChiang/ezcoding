package com.ezcoding.common.foundation.core.log.impl;

import com.ezcoding.common.foundation.core.log.LogMetadata;
import com.ezcoding.common.foundation.core.log.LogPrinter;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-20 16:45
 */
public class DefaultLogPrinter implements LogPrinter {

    private LogPrinter logPrinter = new Slf4jLogPrinter();

    @Override
    public void print(String message, LogMetadata logMetadata, Object target, List<Object> objects) {
        logPrinter.print(message, logMetadata, target, objects);
    }

    public LogPrinter getLogPrinter() {
        return logPrinter;
    }

    public void setLogPrinter(LogPrinter logPrinter) {
        this.logPrinter = logPrinter;
    }

}
