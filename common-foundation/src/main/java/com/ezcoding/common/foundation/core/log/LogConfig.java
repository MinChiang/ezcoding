package com.ezcoding.common.foundation.core.log;

import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-19 14:46
 */
public class LogConfig {

    private final Map<Class<? extends LogPrinter>, LogPrinter> logPrinterMap;
    private final Map<Class<? extends LogParser>, LogParser> logParserMap;
    private final Map<Class<? extends LogFormatter>, LogFormatter> logFormatterMap;
    private final LogPrinter defaultLogPrinter;
    private final LogParser defaultLogParser;
    private final LogFormatter defaultLogFormatter;

    LogConfig(Map<Class<? extends LogPrinter>, LogPrinter> logPrinterMap, Map<Class<? extends LogParser>, LogParser> logParserMap, Map<Class<? extends LogFormatter>, LogFormatter> logFormatterMap, LogPrinter defaultLogPrinter, LogParser defaultLogParser, LogFormatter defaultLogFormatter) {
        this.logPrinterMap = logPrinterMap;
        this.logParserMap = logParserMap;
        this.logFormatterMap = logFormatterMap;
        this.defaultLogPrinter = defaultLogPrinter;
        this.defaultLogParser = defaultLogParser;
        this.defaultLogFormatter = defaultLogFormatter;
    }

    public LogPrinter acquireLogPrinter(Class<? extends LogPrinter> cls) {
        return logPrinterMap.getOrDefault(cls, defaultLogPrinter);
    }

    public LogParser acquireLogParser(Class<? extends LogParser> cls) {
        return logParserMap.getOrDefault(cls, defaultLogParser);
    }

    public LogFormatter acquireLogFormatter(Class<? extends LogFormatter> cls) {
        return logFormatterMap.getOrDefault(cls, defaultLogFormatter);
    }

}
