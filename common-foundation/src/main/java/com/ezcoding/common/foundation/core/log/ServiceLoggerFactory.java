package com.ezcoding.common.foundation.core.log;

import com.ezcoding.common.foundation.core.log.impl.DefaultLogFormatter;
import com.ezcoding.common.foundation.core.log.impl.DefaultLogParser;
import com.ezcoding.common.foundation.core.log.impl.DefaultLogPrinter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-19 14:45
 */
public class ServiceLoggerFactory {

    private final Map<Method, ServiceLogger> map = new WeakHashMap<>();

    private Map<Class<? extends LogPrinter>, LogPrinter> logPrinterMap = new HashMap<>();
    private Map<Class<? extends LogParser>, LogParser> logParserMap = new HashMap<>();
    private Map<Class<? extends LogFormatter>, LogFormatter> logFormatterMap = new HashMap<>();
    private Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new DefaultThreadFactory());

    private final DefaultLogPrinter defaultLogPrinter = new DefaultLogPrinter();
    private final DefaultLogParser defaultLogParser = new DefaultLogParser();
    private final DefaultLogFormatter defaultLogFormatter = new DefaultLogFormatter();

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
        return defaultLogPrinter.getLogPrinter();
    }

    public void setDefaultLogPrinter(LogPrinter defaultLogPrinter) {
        this.defaultLogPrinter.setLogPrinter(defaultLogPrinter);
    }

    public LogParser getDefaultLogParser() {
        return defaultLogParser.getLogParser();
    }

    public void setDefaultLogParser(LogParser defaultLogParser) {
        this.defaultLogParser.setLogParser(defaultLogParser);
    }

    public LogFormatter getDefaultLogFormatter() {
        return defaultLogFormatter.getLogFormatter();
    }

    public void setDefaultLogFormatter(LogFormatter defaultLogFormatter) {
        this.defaultLogFormatter.setLogFormatter(defaultLogFormatter);
    }

    public static ServiceLoggerFactory defaultFactory() {
        return new ServiceLoggerFactory();
    }

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    /**
     * 构建对象
     *
     * @param method 调用方法
     * @return 构建的对象
     */
    public ServiceLogger create(Method method) {
        LogConfig logConfig = new LogConfig(
                this.logPrinterMap,
                this.logParserMap,
                this.logFormatterMap,
                this.defaultLogPrinter,
                this.defaultLogParser,
                this.defaultLogFormatter,
                this.executor
        );
        return new ServiceLogger(
                logConfig,
                method
        );
    }

    /**
     * 构建对象
     *
     * @param method 调用方法
     * @return 构建的对象
     */
    public ServiceLogger getOrCreate(Method method) {
        ServiceLogger serviceLogger = map.get(method);
        if (serviceLogger == null) {
            synchronized (this.map) {
                serviceLogger = map.get(method);
                if (serviceLogger == null) {
                    serviceLogger = create(method);
                    map.put(method, serviceLogger);
                }
            }
        }
        return serviceLogger;
    }

    private static class DefaultThreadFactory implements ThreadFactory {

        private static final AtomicLong POOL_NUMBER = new AtomicLong(1);
        private final AtomicLong threadNumber = new AtomicLong(1);
        private final ThreadGroup group;
        private final String namePrefix;

        DefaultThreadFactory() {
            group = new ThreadGroup(ServiceLogger.class.getSimpleName());
            namePrefix = "serviceLogger-pool-" + POOL_NUMBER.getAndIncrement() + "-thread-";
        }

        @Override
        public Thread newThread(Runnable runnable) {
            Thread t = new Thread(group, runnable, namePrefix + threadNumber.getAndIncrement(), 0);
            t.setDaemon(false);
            t.setPriority(Thread.MIN_PRIORITY);
            return t;
        }

    }

}
