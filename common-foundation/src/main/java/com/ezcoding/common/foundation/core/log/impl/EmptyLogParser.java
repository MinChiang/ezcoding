package com.ezcoding.common.foundation.core.log.impl;

import com.ezcoding.common.foundation.core.log.LogParser;
import com.ezcoding.common.foundation.core.log.ParamLogMetadata;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-19 19:38
 */
public class EmptyLogParser implements LogParser {

    @Override
    public Object parse(String expression, ParamLogMetadata paramLogMetadata, Object target) {
        return target;
    }

}
