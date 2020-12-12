package com.ezcoding.common.foundation.core.log.impl;

import com.ezcoding.common.foundation.core.log.LogFormatter;
import com.ezcoding.common.foundation.core.log.LogMetadata;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-16 16:09
 */
public class StringLogFormatter implements LogFormatter {

    @Override
    public String format(String expression, LogMetadata logMetadata, Object target, List<Object> args) {
        return String.format(expression, args);
    }

}
