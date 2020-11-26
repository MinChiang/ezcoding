package com.ezcoding.common.foundation.core.log.impl;

import com.ezcoding.common.foundation.core.log.LogPrinter;
import com.ezcoding.common.foundation.core.log.ServiceLogger;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-03 20:50
 */
public class SystemLogPrinter implements LogPrinter {

    @Override
    public void print(String message, ServiceLogger serviceLogger, Object target, Object[] objects) {
        System.out.println(message);
    }

}
