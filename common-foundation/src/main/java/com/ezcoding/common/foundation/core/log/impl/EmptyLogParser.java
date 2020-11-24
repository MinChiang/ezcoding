package com.ezcoding.common.foundation.core.log.impl;

import com.ezcoding.common.foundation.core.log.LogParser;
import com.ezcoding.common.foundation.core.log.ParamLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-19 19:38
 */
public class EmptyLogParser implements LogParser {

    @Override
    public List<Object> parse(ParamLogger paramLogger, Object target) {
        List<Object> result = new ArrayList<>(paramLogger.getParamLogMetadata().getExpressions().length);
        for (String expression : paramLogger.getParamLogMetadata().getExpressions()) {
            result.add(target);
        }
        return result;
    }

}
