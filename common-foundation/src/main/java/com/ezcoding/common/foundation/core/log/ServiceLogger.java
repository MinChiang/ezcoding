package com.ezcoding.common.foundation.core.log;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-04 14:45
 */
public class ServiceLogger {

    private final String beforeExpression;
    private final String afterExpression;
    private final LogTypeEnum type;
    private final Class<? extends LogFormatter> formatClass;
    private final Class<? extends LogPrinter> printerClass;
    private final Object target;
    private final Method method;
    private final LogConfig logConfig;

    ServiceLogger(String beforeExpression,
                  String afterExpression,
                  LogTypeEnum type,
                  Class<? extends LogFormatter> formatClass,
                  Class<? extends LogPrinter> printerClass,
                  Object target,
                  Method method,
                  LogConfig logConfig) {
        this.beforeExpression = beforeExpression;
        this.afterExpression = afterExpression;
        this.type = type;
        this.formatClass = formatClass;
        this.printerClass = printerClass;
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
            String[] names = paramLog.names();
            if (names.length != expressions.length) {
                //自动填充对应的名字
                names = Arrays.copyOf(names, expressions.length);
//                Arrays.fill(names, names.length, expressions.length, parameter.getName());
            }
            LogParser parser = logConfig.acquireLogParser(paramLog.parseClass());
            for (int i = 0; i < expressions.length; i++) {
                ParamLogInfo paramLogInfo = new ParamLogInfo(names[i], expressions[i], arg);
                paramLogInfos.add(paramLogInfo);
                paramLogInfo.setParseObject(parser.parse(paramLogInfo));
            }
        }
        return paramLogInfos;
    }

    /**
     * 打印日志
     *
     * @param paramLogInfos 参数列表
     */
    private void doLog(List<ParamLogInfo> paramLogInfos) {
        String format = logConfig.acquireLogFormatter(this.formatClass).format(this.beforeExpression, paramLogInfos);
        logConfig.acquireLogPrinter(this.printerClass).print(format, this, paramLogInfos);
    }

    /**
     * 打印日志
     *
     * @param args 入参
     */
    public void logBefore(Object[] args) {
        //打印入参
        Parameter[] parameters = method.getParameters();
        List<ParamLogInfo> paramLogInfos = new ArrayList<>();
        for (int i = 0; i < parameters.length; i++) {
            paramLogInfos.addAll(this.acquireParamLogInfos(parameters[i], args[i]));
        }
        doLog(paramLogInfos);
    }

    /**
     * 打印日志
     *
     * @param result 出参
     */
    public void logAfter(Object result) {
        List<ParamLogInfo> resultLogInfos = this.acquireParamLogInfos(method, result);
        doLog(resultLogInfos);
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

}
