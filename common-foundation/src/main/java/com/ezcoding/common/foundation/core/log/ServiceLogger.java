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

    private final Object target;
    private final Method method;
    private final LogConfig logConfig;

    private ServiceLogMetadata serviceLogMetadata;
    private LogFormatter formatter;
    private LogPrinter printer;
    private List<ParamLogger> beforeParamLogger;
    private ParamLogger afterParamLogger;

    ServiceLogger(LogConfig logConfig,
                  Object target,
                  Method method) {
        this.target = target;
        this.method = method;
        this.logConfig = logConfig;
        this.init();
    }

    /**
     * 初始化
     */
    private void init() {
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
     * @param args 入参
     */
    public void logBefore(Object[] args) {
        if (Objects.equals(this.serviceLogMetadata.type, LogTypeEnum.ASYNC)) {
            CompletableFuture.runAsync(() -> logBeforeSync(args));
        } else {
            logBeforeSync(args);
        }
    }

    /**
     * 同步方式打印日志
     *
     * @param args 入参
     */
    private void logBeforeSync(Object[] args) {
        try {
            List<Object> parsedObjects = new ArrayList<>();
            for (int i = 0; i < this.beforeParamLogger.size(); i++) {
                parsedObjects.addAll(this.beforeParamLogger.get(i).parse(args[i]));
            }
            String format = this.formatter.format(this.serviceLogMetadata.beforeExpression, this, parsedObjects);
            this.printer.print(format, this, parsedObjects);
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
        if (Objects.equals(this.serviceLogMetadata.type, LogTypeEnum.ASYNC)) {
            CompletableFuture.runAsync(() -> logAfterSync(result));
        } else {
            logAfterSync(result);
        }
    }

    /**
     * 同步方式打印日志
     *
     * @param result 出参
     */
    public void logAfterSync(Object result) {
        try {
            List<ParamLogger> paramLoggers;
            if (this.serviceLogMetadata.fillParametersInReturn) {
                paramLoggers = new ArrayList<>(this.beforeParamLogger);
            } else {
                paramLoggers = new ArrayList<>();
            }
            paramLoggers.add(this.afterParamLogger);

            List<Object> parsedObjects = new ArrayList<>();
            for (ParamLogger paramLogger : paramLoggers) {
                parsedObjects.addAll(paramLogger.parse(result));
            }
            String format = this.formatter.format(this.serviceLogMetadata.afterExpression, this, parsedObjects);
            this.printer.print(format, this, parsedObjects);
        } catch (Exception e) {
            LOGGER.error("service log error!", e);
        }
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
