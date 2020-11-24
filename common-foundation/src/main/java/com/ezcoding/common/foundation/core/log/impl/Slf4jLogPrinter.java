package com.ezcoding.common.foundation.core.log.impl;

import com.ezcoding.common.foundation.core.log.LogPrinter;
import com.ezcoding.common.foundation.core.log.ServiceLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-03 20:49
 */
public class Slf4jLogPrinter implements LogPrinter {

    @Override
    public void print(String message, ServiceLogger serviceLogger, List<Object> objects) {
        Logger logger = LoggerFactory.getLogger(serviceLogger.getTarget().getClass());
        if (logger.isInfoEnabled()) {
            logger.info(message, objects.toArray());
        }
    }

}
