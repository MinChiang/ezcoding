package com.ezcoding.common.foundation.core.log;

import com.ezcoding.common.foundation.core.log.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-19 14:46
 */
public class LogConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogConfig.class);

    private Map<Class<? extends LogPrinter>, LogPrinter> logPrinterMap = new HashMap<>();
    private Map<Class<? extends LogParser>, LogParser> logParserMap = new HashMap<>();
    private Map<Class<? extends LogFormatter>, LogFormatter> logFormatterMap = new HashMap<>();

    private LogPrinter defaultLogPrinter = new Slf4jLogPrinter();
    private LogParser defaultLogParser = new EmptyLogParser();
    private LogFormatter defaultLogFormatter = new EmptyLogFormatter();

    private Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new DefaultThreadFactory());

    public LogConfig(Map<Class<? extends LogPrinter>, LogPrinter> logPrinterMap, Map<Class<? extends LogParser>, LogParser> logParserMap, Map<Class<? extends LogFormatter>, LogFormatter> logFormatterMap, LogPrinter defaultLogPrinter, LogParser defaultLogParser, LogFormatter defaultLogFormatter, Executor executor) {
        this.logPrinterMap = logPrinterMap;
        this.logParserMap = logParserMap;
        this.logFormatterMap = logFormatterMap;
        this.defaultLogPrinter = defaultLogPrinter;
        this.defaultLogParser = defaultLogParser;
        this.defaultLogFormatter = defaultLogFormatter;
        this.executor = executor;
    }

    public LogConfig(LogPrinter defaultLogPrinter, LogParser defaultLogParser, LogFormatter defaultLogFormatter, Executor executor) {
        this.defaultLogPrinter = defaultLogPrinter;
        this.defaultLogParser = defaultLogParser;
        this.defaultLogFormatter = defaultLogFormatter;
        this.executor = executor;
    }

    public LogConfig() {
    }

    /**
     * 获取日志打印器
     *
     * @param cls 日志打印类
     * @return 日志打印器实例
     */
    public LogPrinter acquireLogPrinter(Class<? extends LogPrinter> cls) {
        LogPrinter logPrinter = logPrinterMap.getOrDefault(cls, defaultLogPrinter);
        if (logPrinter instanceof DefaultLogPrinter) {
            return ((DefaultLogPrinter) logPrinter).getLogPrinter();
        }
        return logPrinter;
    }

    /**
     * 获取日志参数解析器
     *
     * @param cls 日志参数解析器类
     * @return 日志参数解析实例
     */
    public LogParser acquireLogParser(Class<? extends LogParser> cls) {
        LogParser logParser = logParserMap.getOrDefault(cls, defaultLogParser);
        if (logParser instanceof DefaultLogParser) {
            return ((DefaultLogParser) logParser).getLogParser();
        }
        return logParser;
    }

    /**
     * 获取日志打印格式化器
     *
     * @param cls 日志打印格式化器类
     * @return 日志打印格式化实例
     */
    public LogFormatter acquireLogFormatter(Class<? extends LogFormatter> cls) {
        LogFormatter logFormatter = logFormatterMap.getOrDefault(cls, defaultLogFormatter);
        if (logFormatter instanceof DefaultLogFormatter) {
            return ((DefaultLogFormatter) logFormatter).getLogFormatter();
        }
        return logFormatter;
    }

    /**
     * 获取线程执行器
     *
     * @return 线程执行器
     */
    public Executor acquireExecutor() {
        return this.executor;
    }

    /**
     * 实例化
     *
     * @param cls             需要实例化类
     * @param <T>             类型
     * @param defaultInstance 默认取值
     * @return 实例
     */
    private <T> T getInstance(Class<? extends T> cls, T defaultInstance) {
        try {
            return cls.newInstance();
        } catch (Exception e) {
            LOGGER.error("unable to find or instance class : {}", cls.getName());
        }
        return defaultInstance;
    }

    protected static class DefaultThreadFactory implements ThreadFactory {

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
