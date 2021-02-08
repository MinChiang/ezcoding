package com.ezcoding.common.foundation.core.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-04 14:45
 */
public class LogProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogProcessor.class);

    private final Method method;
    private final LogConfig logConfig;

    private final LogMetadata logMetadata;
    private final LogFormatter formatter;
    private final LogPrinter printer;

    private final boolean unnecessaryToLogBefore;
    private final boolean unnecessaryToLogAfter;

    private List<LogParamProcessor> beforeLogParamProcessors;
    private LogParamProcessor afterLogParamProcessor;

    LogProcessor(LogConfig logConfig,
                 Method method) {
        this.method = method;
        this.logConfig = logConfig;

        //元数据初始化
        StandardLog standardLog = this.method.getAnnotation(StandardLog.class);
        this.logMetadata = new LogMetadata(
                standardLog.beforeExpression(),
                standardLog.afterExpression(),
                standardLog.type(),
                standardLog.formatClass(),
                standardLog.logClass(),
                standardLog.fillParametersInReturn()
        );
        this.formatter = logConfig.acquireLogFormatter(this.logMetadata.formatClass);
        this.printer = logConfig.acquireLogPrinter(this.logMetadata.printerClass);

        //入参初始化
        Parameter[] parameters = method.getParameters();
        this.beforeLogParamProcessors = new ArrayList<>();
        boolean hasParameterAnnotation = false;
        for (Parameter parameter : parameters) {
            if (parameter.isAnnotationPresent(StandardLogParam.class)) {
                this.beforeLogParamProcessors.add(new LogParamProcessor(this.logConfig, parameter));
                hasParameterAnnotation = true;
            }
        }

        //出参初始化
        boolean hasMethodAnnotation = false;
        if (this.method.isAnnotationPresent(StandardLogParam.class)) {
            this.afterLogParamProcessor = new LogParamProcessor(this.logConfig, this.method);
            hasMethodAnnotation = true;
        }

        //控制打印
        this.unnecessaryToLogBefore = !hasParameterAnnotation;
        this.unnecessaryToLogAfter = !hasMethodAnnotation;
    }

    /**
     * 打印日志
     *
     * @param target 目标对象
     * @param args   入参
     */
    public void logBefore(Object target, Object[] args) {
        if (this.unnecessaryToLogBefore) {
            return;
        }
        if (Objects.equals(this.logMetadata.type, LogTypeEnum.ASYNC)) {
            CompletableFuture.runAsync(() -> logBeforeSync(target, args), logConfig.acquireExecutor());
        } else {
            logBeforeSync(target, args);
        }
    }

    /**
     * 同步方式打印日志
     *
     * @param target 目标对象
     * @param args   入参
     */
    private void logBeforeSync(Object target, Object[] args) {
        try {
            List<Object> parsedObjects = new LinkedList<>();
            for (int i = 0; i < this.beforeLogParamProcessors.size(); i++) {
                parsedObjects.addAll(this.beforeLogParamProcessors.get(i).parse(args[i]));
            }
            String format = this.formatter.format(this.logMetadata.beforeExpression, this.logMetadata, target, parsedObjects);
            this.printer.print(format, this.logMetadata, target, parsedObjects);
        } catch (Exception e) {
            LOGGER.error("service log error!", e);
        }
    }

    /**
     * 打印日志
     *
     * @param target 目标对象
     * @param result 出参
     */
    public void logAfter(Object target, Object result) {
        if (unnecessaryToLogAfter) {
            return;
        }
        if (Objects.equals(this.logMetadata.type, LogTypeEnum.ASYNC)) {
            CompletableFuture.runAsync(() -> logAfterSync(target, result), logConfig.acquireExecutor());
        } else {
            logAfterSync(target, result);
        }
    }

    /**
     * 同步方式打印日志
     *
     * @param target 目标对象
     * @param result 出参
     */
    public void logAfterSync(Object target, Object result) {
        try {
            List<LogParamProcessor> logParamProcessors;
            if (this.logMetadata.fillParametersInReturn) {
                logParamProcessors = new ArrayList<>(this.beforeLogParamProcessors);
            } else {
                logParamProcessors = new ArrayList<>();
            }
            logParamProcessors.add(this.afterLogParamProcessor);

            List<Object> parsedObjects = new LinkedList<>();
            for (LogParamProcessor logParamProcessor : logParamProcessors) {
                parsedObjects.addAll(logParamProcessor.parse(result));
            }
            String format = this.formatter.format(this.logMetadata.afterExpression, this.logMetadata, target, parsedObjects);
            this.printer.print(format, this.logMetadata, target, parsedObjects);
        } catch (Exception e) {
            LOGGER.error("service log error!", e);
        }
    }

    public Method getMethod() {
        return method;
    }

    public LogConfig getLogConfig() {
        return logConfig;
    }

    public LogMetadata getServiceLogMetadata() {
        return logMetadata;
    }

    public LogFormatter getFormatter() {
        return formatter;
    }

    public LogPrinter getPrinter() {
        return printer;
    }

    public List<LogParamProcessor> getBeforeParamLogger() {
        return beforeLogParamProcessors;
    }

    public LogParamProcessor getAfterParamLogger() {
        return afterLogParamProcessor;
    }

}
