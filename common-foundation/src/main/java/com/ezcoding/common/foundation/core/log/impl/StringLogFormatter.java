package com.ezcoding.common.foundation.core.log.impl;

import com.ezcoding.common.foundation.core.log.LogFormatter;
import com.ezcoding.common.foundation.core.log.ServiceLogger;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-16 16:09
 */
public class StringLogFormatter implements LogFormatter {

    @Override
    public String format(String expression, ServiceLogger serviceLogger, List<Object> objects) {
        return String.format(expression, objects.toArray());
    }

}
