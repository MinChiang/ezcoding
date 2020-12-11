package com.ezcoding.common.foundation.core.log;

import com.ezcoding.common.foundation.core.log.impl.EmptyLogFormatter;
import com.ezcoding.common.foundation.core.log.impl.EmptyLogParser;
import com.ezcoding.common.foundation.core.log.impl.Slf4jLogPrinter;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-19 14:45
 */
public class ServiceLoggerFactory {

    private LogConfig logConfig = new LogConfig();

    private ServiceLoggerFactory(LogConfig logConfig) {
        this.logConfig = logConfig;
    }

    /**
     * 构建对象
     *
     * @param method 调用方法
     * @return 构建的对象
     */
    public ServiceLogger create(Method method) {
        return new ServiceLogger(
                this.logConfig,
                method
        );
    }

    /**
     * 构建器
     *
     * @return 构建器
     */
    public static ServiceLoggerFactoryBuilder builder() {
        return new ServiceLoggerFactoryBuilder();
    }

    public static class ServiceLoggerFactoryBuilder {

        private final Map<Class<? extends LogPrinter>, LogPrinter> logPrinterMap = new HashMap<>();
        private final Map<Class<? extends LogParser>, LogParser> logParserMap = new HashMap<>();
        private final Map<Class<? extends LogFormatter>, LogFormatter> logFormatterMap = new HashMap<>();

        private LogPrinter defaultLogPrinter = new Slf4jLogPrinter();
        private LogParser defaultLogParser = new EmptyLogParser();
        private LogFormatter defaultLogFormatter = new EmptyLogFormatter();

        private Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new LogConfig.DefaultThreadFactory());

        public ServiceLoggerFactoryBuilder() {
        }

        public ServiceLoggerFactoryBuilder defaultLogPrinter(LogPrinter defaultLogPrinter) {
            this.defaultLogPrinter = defaultLogPrinter;
            return this;
        }

        public ServiceLoggerFactoryBuilder defaultLogParser(LogParser defaultLogParser) {
            this.defaultLogParser = defaultLogParser;
            return this;
        }

        public ServiceLoggerFactoryBuilder defaultLogFormatter(LogFormatter defaultLogFormatter) {
            this.defaultLogFormatter = defaultLogFormatter;
            return this;
        }

        public ServiceLoggerFactoryBuilder executor(Executor executor) {
            this.executor = executor;
            return this;
        }

        public ServiceLoggerFactoryBuilder logPrinter(LogPrinter logPrinter) {
            this.logPrinterMap.put(logPrinter.getClass(), logPrinter);
            return this;
        }

        public ServiceLoggerFactoryBuilder logParser(LogParser logParser) {
            this.logParserMap.put(logParser.getClass(), logParser);
            return this;
        }

        public ServiceLoggerFactoryBuilder logFormatter(LogFormatter logFormatter) {
            this.logFormatterMap.put(logFormatter.getClass(), logFormatter);
            return this;
        }

        public ServiceLoggerFactoryBuilder logPrinters(Collection<LogPrinter> logPrinters) {
            logPrinters.forEach(this::logPrinter);
            return this;
        }

        public ServiceLoggerFactoryBuilder logParsers(Collection<LogParser> logParsers) {
            logParsers.forEach(this::logParser);
            return this;
        }

        public ServiceLoggerFactoryBuilder logFormatters(Collection<LogFormatter> logFormatters) {
            logFormatters.forEach(this::logFormatter);
            return this;
        }

        public Map<Class<? extends LogPrinter>, LogPrinter> getLogPrinterMap() {
            return logPrinterMap;
        }

        public Map<Class<? extends LogParser>, LogParser> getLogParserMap() {
            return logParserMap;
        }

        public Map<Class<? extends LogFormatter>, LogFormatter> getLogFormatterMap() {
            return logFormatterMap;
        }

        public LogPrinter getDefaultLogPrinter() {
            return defaultLogPrinter;
        }

        public LogParser getDefaultLogParser() {
            return defaultLogParser;
        }

        public LogFormatter getDefaultLogFormatter() {
            return defaultLogFormatter;
        }

        public Executor getExecutor() {
            return executor;
        }

        /**
         * 构建对象
         *
         * @return 构建后的对象实例
         */
        public ServiceLoggerFactory build() {
            LogConfig logConfig = new LogConfig(
                    this.logPrinterMap,
                    this.logParserMap,
                    this.logFormatterMap,
                    this.defaultLogPrinter,
                    this.defaultLogParser,
                    this.defaultLogFormatter,
                    this.executor
            );
            return new ServiceLoggerFactory(logConfig);
        }

    }

}
