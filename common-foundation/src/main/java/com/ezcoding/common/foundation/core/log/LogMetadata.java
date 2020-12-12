package com.ezcoding.common.foundation.core.log;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-24 16:28
 */
public class LogMetadata {

    public final String beforeExpression;
    public final String afterExpression;
    public final LogTypeEnum type;
    public final Class<? extends LogFormatter> formatClass;
    public final Class<? extends LogPrinter> printerClass;
    public final Boolean fillParametersInReturn;

    public LogMetadata(String beforeExpression, String afterExpression, LogTypeEnum type, Class<? extends LogFormatter> formatClass, Class<? extends LogPrinter> printerClass, Boolean fillParametersInReturn) {
        this.beforeExpression = beforeExpression;
        this.afterExpression = afterExpression;
        this.type = type;
        this.formatClass = formatClass;
        this.printerClass = printerClass;
        this.fillParametersInReturn = fillParametersInReturn;
    }

}
