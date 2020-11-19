package com.ezcoding.common.foundation.core.log;

import com.ezcoding.common.foundation.core.log.impl.EmptyLogParser;
import com.ezcoding.common.foundation.core.log.impl.Slf4jLogPrinter;
import com.ezcoding.common.foundation.core.log.impl.StringLogFormatter;
import com.ezcoding.common.foundation.core.log.impl.SystemLogPrinter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-19 14:45
 */
public class ServiceLoggerFactory {

    private Map<Class<? extends LogPrinter>, LogPrinter> logPrinterMap = new HashMap<>();
    private Map<Class<? extends LogParser>, LogParser> logParserMap = new HashMap<>();
    private Map<Class<? extends LogFormatter>, LogFormatter> logFormatterMap = new HashMap<>();
    private LogPrinter defaultLogPrinter;
    private LogParser defaultLogParser;
    private LogFormatter defaultLogFormatter;

    private ServiceLoggerFactory() {
    }

    public void addLogPrinter(LogPrinter logPrinter) {
        logPrinterMap.put(logPrinter.getClass(), logPrinter);
    }

    public void addLogParser(LogParser logParser) {
        logParserMap.put(logParser.getClass(), logParser);
    }

    public void addLogFormatter(LogFormatter logFormatter) {
        logFormatterMap.put(logFormatter.getClass(), logFormatter);
    }

    public void addLogPrinters(List<LogPrinter> logPrinters) {
        if (logPrinters == null || logPrinters.isEmpty()) {
            return;
        }
        logPrinters.forEach(logPrinter -> this.logPrinterMap.put(logPrinter.getClass(), logPrinter));
    }

    public void addLogParsers(List<LogParser> logParsers) {
        if (logParsers == null || logParsers.isEmpty()) {
            return;
        }
        logParsers.forEach(logParser -> this.logParserMap.put(logParser.getClass(), logParser));
    }

    public void addLogFormatters(List<LogFormatter> logFormatters) {
        if (logFormatters == null || logFormatters.isEmpty()) {
            return;
        }
        logFormatters.forEach(logFormatter -> this.logFormatterMap.put(logFormatter.getClass(), logFormatter));
    }

    public Map<Class<? extends LogPrinter>, LogPrinter> getLogPrinterMap() {
        return logPrinterMap;
    }

    public void setLogPrinterMap(Map<Class<? extends LogPrinter>, LogPrinter> logPrinterMap) {
        this.logPrinterMap = logPrinterMap;
    }

    public Map<Class<? extends LogParser>, LogParser> getLogParserMap() {
        return logParserMap;
    }

    public void setLogParserMap(Map<Class<? extends LogParser>, LogParser> logParserMap) {
        this.logParserMap = logParserMap;
    }

    public Map<Class<? extends LogFormatter>, LogFormatter> getLogFormatterMap() {
        return logFormatterMap;
    }

    public void setLogFormatterMap(Map<Class<? extends LogFormatter>, LogFormatter> logFormatterMap) {
        this.logFormatterMap = logFormatterMap;
    }

    public LogPrinter getDefaultLogPrinter() {
        return defaultLogPrinter;
    }

    public void setDefaultLogPrinter(LogPrinter defaultLogPrinter) {
        this.defaultLogPrinter = defaultLogPrinter;
    }

    public LogParser getDefaultLogParser() {
        return defaultLogParser;
    }

    public void setDefaultLogParser(LogParser defaultLogParser) {
        this.defaultLogParser = defaultLogParser;
    }

    public LogFormatter getDefaultLogFormatter() {
        return defaultLogFormatter;
    }

    public void setDefaultLogFormatter(LogFormatter defaultLogFormatter) {
        this.defaultLogFormatter = defaultLogFormatter;
    }

    public static ServiceLoggerFactory defaultFactory() {
        ServiceLoggerFactory serviceLoggerFactory = new ServiceLoggerFactory();

        Slf4jLogPrinter slf4jLogPrinter = new Slf4jLogPrinter();
        SystemLogPrinter systemLogPrinter = new SystemLogPrinter();
        serviceLoggerFactory.addLogPrinter(slf4jLogPrinter);
        serviceLoggerFactory.addLogPrinter(systemLogPrinter);
        serviceLoggerFactory.setDefaultLogPrinter(slf4jLogPrinter);

        EmptyLogParser emptyLogParser = new EmptyLogParser();
        serviceLoggerFactory.addLogParser(emptyLogParser);
        serviceLoggerFactory.setDefaultLogParser(emptyLogParser);

        StringLogFormatter stringLogFormatter = new StringLogFormatter();
        serviceLoggerFactory.addLogFormatter(stringLogFormatter);
        serviceLoggerFactory.setDefaultLogFormatter(stringLogFormatter);

        return serviceLoggerFactory;
    }

    /**
     * 构建对象
     *
     * @param serviceLog 注解
     * @param target     调用对象
     * @param method     调用方法
     * @return 构建的对象
     */
    public ServiceLogger create(ServiceLog serviceLog, Object target, Method method) {
        LogConfig logConfig = new LogConfig(
                this.logPrinterMap,
                this.logParserMap,
                this.logFormatterMap,
                this.defaultLogPrinter,
                this.defaultLogParser,
                this.defaultLogFormatter
        );
        return new ServiceLogger(
                serviceLog.beforeExpression(),
                serviceLog.afterExpression(),
                serviceLog.type(),
                serviceLog.formatClass(),
                serviceLog.logClass(),
                target,
                method,
                logConfig
        );
    }

}
