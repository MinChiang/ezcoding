package com.ezcoding.common.foundation.core.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-04 14:45
 */
public class ServiceLogger {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceLogger.class);

    private final String beforeExpression;
    private final String afterExpression;
    private final LogTypeEnum type;
    private final Class<? extends LogFormatter> formatClass;
    private final Class<? extends LogPrinter> printerClass;
    private final Boolean fillParametersInReturn;
    private final Object target;
    private final Method method;
    private final LogConfig logConfig;
    private final List<ParamLogInfo> paramLogInfos = new ArrayList<>();

    ServiceLogger(String beforeExpression,
                  String afterExpression,
                  LogTypeEnum type,
                  Class<? extends LogFormatter> formatClass,
                  Class<? extends LogPrinter> printerClass,
                  Boolean fillParametersInReturn,
                  Object target,
                  Method method,
                  LogConfig logConfig) {
        this.beforeExpression = beforeExpression;
        this.afterExpression = afterExpression;
        this.type = type;
        this.formatClass = formatClass;
        this.printerClass = printerClass;
        this.fillParametersInReturn = fillParametersInReturn;
        this.target = target;
        this.method = method;
        this.logConfig = logConfig;
    }

    /**
     * 获取参数注解
     *
     * @param annotatedElement 被注释的元素
     * @param arg              参数对象
     * @return 注释元数据
     */
    private List<ParamLogInfo> acquireParamLogInfos(AnnotatedElement annotatedElement, Object arg) {
        List<ParamLogInfo> paramLogInfos = new ArrayList<>();
        if (annotatedElement.isAnnotationPresent(ParamLog.class)) {
            ParamLog paramLog = annotatedElement.getAnnotation(ParamLog.class);
            String[] expressions = paramLog.expressions();
            LogParser parser = logConfig.acquireLogParser(paramLog.parseClass());
            for (String expression : expressions) {
                ParamLogInfo paramLogInfo = new ParamLogInfo(expression, arg);
                paramLogInfos.add(paramLogInfo);
                paramLogInfo.setParseObject(parser.parse(paramLogInfo));
            }
        }
        return paramLogInfos;
    }

    /**
     * 打印日志
     *
     * @param args 入参
     */
    public void logBefore(Object[] args) {
        try {
            //打印入参
            Parameter[] parameters = method.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                this.paramLogInfos.addAll(this.acquireParamLogInfos(parameters[i], args[i]));
            }
            String format = logConfig.acquireLogFormatter(this.formatClass).format(this.beforeExpression, paramLogInfos);
            logConfig.acquireLogPrinter(this.printerClass).print(format, this, paramLogInfos);
        } catch (Exception e) {
            LOGGER.error("service log error!", e);
        }
    }

    /**
     * 打印日志
     *
     * @param result 出参
     */
    public void logAfter(Object result) {
        try {
            List<ParamLogInfo> resultLogInfos = this.acquireParamLogInfos(method, result);
            this.paramLogInfos.addAll(resultLogInfos);

            List<ParamLogInfo> useInfo = this.fillParametersInReturn ? this.paramLogInfos : resultLogInfos;
            String format = logConfig.acquireLogFormatter(this.formatClass).format(this.beforeExpression, useInfo);
            logConfig.acquireLogPrinter(this.printerClass).print(format, this, useInfo);
        } catch (Exception e) {
            LOGGER.error("service log error!", e);
        }
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

    public Class<? extends LogPrinter> getPrinterClass() {
        return printerClass;
    }

    public Object getTarget() {
        return target;
    }

    public Method getMethod() {
        return method;
    }

    public LogConfig getLogConfig() {
        return logConfig;
    }

    public Boolean getFillParametersInReturn() {
        return fillParametersInReturn;
    }

}
