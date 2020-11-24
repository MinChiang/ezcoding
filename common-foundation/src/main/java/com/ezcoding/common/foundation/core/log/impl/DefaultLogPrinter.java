package com.ezcoding.common.foundation.core.log.impl;

import com.ezcoding.common.foundation.core.log.LogPrinter;
import com.ezcoding.common.foundation.core.log.ServiceLogger;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-20 16:45
 */
public class DefaultLogPrinter implements LogPrinter {

    private LogPrinter logPrinter = new Slf4jLogPrinter();

    @Override
    public void print(String message, ServiceLogger serviceLogger, List<Object> objects) {
        logPrinter.print(message, serviceLogger, objects);
    }

    public LogPrinter getLogPrinter() {
        return logPrinter;
    }

    public void setLogPrinter(LogPrinter logPrinter) {
        this.logPrinter = logPrinter;
    }

}
