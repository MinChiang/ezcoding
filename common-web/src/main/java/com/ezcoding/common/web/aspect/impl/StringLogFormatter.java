package com.ezcoding.common.web.aspect.impl;

import com.ezcoding.common.web.aspect.LogFormatter;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-16 16:09
 */
public class StringLogFormatter implements LogFormatter {

    @Override
    public String format(String expression, List<ParamLogInfo> paramLogInfos) {
        Object[] objects = paramLogInfos.stream().map(ParamLogInfo::getParseObject).toArray();
        return String.format(expression, objects);
    }

}
