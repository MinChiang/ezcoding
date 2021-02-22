package com.ezcoding.common.foundation.core.log.impl;

import com.ezcoding.common.foundation.core.log.LogMetadata;
import com.ezcoding.common.foundation.core.log.LogPrinter;
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
    public void print(String message, LogMetadata logMetadata, Object target, List<Object> objects) {
        Logger logger = LoggerFactory.getLogger(target.getClass());
        if (logger.isInfoEnabled()) {
            logger.info(message, objects.toArray());
        }
    }

}
