package com.ezcoding.common.foundation.core.log.impl;

import com.ezcoding.common.foundation.core.log.LogPrinter;
import com.ezcoding.common.foundation.core.log.ServiceLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-03 20:49
 */
public class Slf4jLogPrinter implements LogPrinter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Slf4jLogPrinter.class);

    @Override
    public void print(String message, ServiceLogger serviceLogger, Object target, Object[] objects) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(message, objects);
        }
    }

}
