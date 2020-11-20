package com.ezcoding.common.foundation.core.log.impl;

import com.ezcoding.common.foundation.core.log.LogPrinter;
import com.ezcoding.common.foundation.core.log.ParamLogInfo;
import com.ezcoding.common.foundation.core.log.ServiceLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-03 20:49
 */
public class Slf4jLogPrinter implements LogPrinter {

    @Override
    public void print(String message, ServiceLogger serviceLogger, List<ParamLogInfo> paramLogInfos) {
        Logger logger = LoggerFactory.getLogger(serviceLogger.getTarget().getClass());
        if (logger.isInfoEnabled()) {
            List<Object> objects = paramLogInfos.stream().map(ParamLogInfo::getParseObject).collect(Collectors.toList());
            logger.info(message, objects);
        }
    }

}
