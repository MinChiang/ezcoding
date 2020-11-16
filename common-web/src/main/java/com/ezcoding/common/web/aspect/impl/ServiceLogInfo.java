package com.ezcoding.common.web.aspect.impl;

import com.ezcoding.common.web.aspect.LogFormatter;
import com.ezcoding.common.web.aspect.LogTypeEnum;
import com.ezcoding.common.web.aspect.ServiceLog;
import com.ezcoding.common.web.aspect.ServiceLogger;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-04 14:45
 */
public class ServiceLogInfo {

    private final String beforeExpression;
    private final String afterExpression;
    private final LogTypeEnum type;
    private final Class<? extends LogFormatter> formatClass;
    private final Class<? extends ServiceLogger> logClass;
    private final Object target;

    private ServiceLogInfo(String beforeExpression, String afterExpression, LogTypeEnum type, Class<? extends LogFormatter> formatClass, Class<? extends ServiceLogger> logClass, Object target) {
        this.beforeExpression = beforeExpression;
        this.afterExpression = afterExpression;
        this.type = type;
        this.formatClass = formatClass;
        this.logClass = logClass;
        this.target = target;
    }

    public static ServiceLogInfo create(ServiceLog serviceLog, Object target) {
        return new ServiceLogInfo(
                serviceLog.beforeExpression(),
                serviceLog.afterExpression(),
                serviceLog.type(),
                serviceLog.formatClass(),
                serviceLog.logClass(),
                target
        );
    }

    public String getBeforeExpression() {
        return beforeExpression;
    }

    public String getAfterExpression() {
        return afterExpression;
    }

    public LogTypeEnum getType() {
        return type;
    }

    public Class<? extends LogFormatter> getFormatClass() {
        return formatClass;
    }

    public Class<? extends ServiceLogger> getLogClass() {
        return logClass;
    }

    public Object getTarget() {
        return target;
    }

}
