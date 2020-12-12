package com.ezcoding.common.foundation.core.log.impl;

import com.ezcoding.common.foundation.core.log.LogMetadata;
import com.ezcoding.common.foundation.core.log.LogPrinter;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-03 20:50
 */
public class SystemLogPrinter implements LogPrinter {

    @Override
    public void print(String message, LogMetadata logMetadata, Object target, List<Object> objects) {
        System.out.println(message);
    }

}
