package com.ezcoding.common.foundation.core.log;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-24 16:28
 */
public class ServiceLogMetadata {

    final String beforeExpression;
    final String afterExpression;
    final LogTypeEnum type;
    final Class<? extends LogFormatter> formatClass;
    final Class<? extends LogPrinter> printerClass;
    final Boolean fillParametersInReturn;

    public ServiceLogMetadata(String beforeExpression, String afterExpression, LogTypeEnum type, Class<? extends LogFormatter> formatClass, Class<? extends LogPrinter> printerClass, Boolean fillParametersInReturn) {
        this.beforeExpression = beforeExpression;
        this.afterExpression = afterExpression;
        this.type = type;
        this.formatClass = formatClass;
        this.printerClass = printerClass;
        this.fillParametersInReturn = fillParametersInReturn;
    }

}
