package com.ezcoding.common.foundation.core.log.impl;

import com.ezcoding.common.foundation.core.log.LogPrinter;
import com.ezcoding.common.foundation.core.log.ParamLogInfo;
import com.ezcoding.common.foundation.core.log.ServiceLogger;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-03 20:50
 */
public class SystemLogPrinter implements LogPrinter {

    @Override
    public void print(String message, ServiceLogger serviceLogger, List<ParamLogInfo> paramLogInfos) {
        System.out.println(message);
    }

}
