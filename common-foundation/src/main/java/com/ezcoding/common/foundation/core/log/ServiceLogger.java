package com.ezcoding.common.foundation.core.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-04 14:45
 */
public class ServiceLogger {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceLogger.class);

    private final Method method;
    private final LogConfig logConfig;

    private final ServiceLogMetadata serviceLogMetadata;
    private final LogFormatter formatter;
    private final LogPrinter printer;
    private final List<ParamLogger> beforeParamLogger;
    private final ParamLogger afterParamLogger;

    ServiceLogger(LogConfig logConfig,
                  Method method) {
        this.method = method;
        this.logConfig = logConfig;

        //元数据初始化
        ServiceLog serviceLog = this.method.getAnnotation(ServiceLog.class);
        this.serviceLogMetadata = new ServiceLogMetadata(
                serviceLog.beforeExpression(),
                serviceLog.afterExpression(),
                serviceLog.type(),
                serviceLog.formatClass(),
                serviceLog.logClass(),
                serviceLog.fillParametersInReturn()
        );
        this.formatter = logConfig.acquireLogFormatter(this.serviceLogMetadata.formatClass);
        this.printer = logConfig.acquireLogPrinter(this.serviceLogMetadata.printerClass);

        //入参初始化
        Parameter[] parameters = method.getParameters();
        this.beforeParamLogger = new ArrayList<>();
        for (Parameter parameter : parameters) {
            this.beforeParamLogger.add(new ParamLogger(this.logConfig, parameter));
        }

        //出参初始化
        this.afterParamLogger = new ParamLogger(this.logConfig, this.method);
    }

    /**
     * 打印日志
     *
     * @param target 目标对象
     * @param args   入参
     */
    public void logBefore(Object target, Object[] args) {
        if (Objects.equals(this.serviceLogMetadata.type, LogTypeEnum.ASYNC)) {
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
            Object[] parsedObjects = new Object[this.beforeParamLogger.size()];
            for (int i = 0; i < this.beforeParamLogger.size(); i++) {
                parsedObjects[i] = this.beforeParamLogger.get(i).parse(args[i]);
            }
            String format = this.formatter.format(this.serviceLogMetadata.beforeExpression, this, target, parsedObjects);
            this.printer.print(format, this, target, parsedObjects);
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
        if (Objects.equals(this.serviceLogMetadata.type, LogTypeEnum.ASYNC)) {
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
            List<ParamLogger> paramLoggers;
            if (this.serviceLogMetadata.fillParametersInReturn) {
                paramLoggers = new ArrayList<>(this.beforeParamLogger);
            } else {
                paramLoggers = new ArrayList<>();
            }
            paramLoggers.add(this.afterParamLogger);

            Object[] parsedObjects = new Object[paramLoggers.size()];
            for (int i = 0; i < paramLoggers.size(); i++) {
                parsedObjects[i] = paramLoggers.get(i).parse(result);
            }
            String format = this.formatter.format(this.serviceLogMetadata.afterExpression, this, target, parsedObjects);
            this.printer.print(format, this, target, parsedObjects);
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

    public ServiceLogMetadata getServiceLogMetadata() {
        return serviceLogMetadata;
    }

    public LogFormatter getFormatter() {
        return formatter;
    }

    public LogPrinter getPrinter() {
        return printer;
    }

    public List<ParamLogger> getBeforeParamLogger() {
        return beforeParamLogger;
    }

    public ParamLogger getAfterParamLogger() {
        return afterParamLogger;
    }

}
