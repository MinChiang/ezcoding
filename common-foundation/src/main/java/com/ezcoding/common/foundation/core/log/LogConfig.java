package com.ezcoding.common.foundation.core.log;

import com.ezcoding.common.foundation.core.log.impl.DefaultLogFormatter;
import com.ezcoding.common.foundation.core.log.impl.DefaultLogParser;
import com.ezcoding.common.foundation.core.log.impl.DefaultLogPrinter;

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
        LogPrinter logPrinter = logPrinterMap.getOrDefault(cls, defaultLogPrinter);
        if (logPrinter instanceof DefaultLogPrinter) {
            return ((DefaultLogPrinter) logPrinter).getLogPrinter();
        }
        return logPrinter;
    }

    public LogParser acquireLogParser(Class<? extends LogParser> cls) {
        LogParser logParser = logParserMap.getOrDefault(cls, defaultLogParser);
        if (logParser instanceof DefaultLogParser) {
            return ((DefaultLogParser) logParser).getLogParser();
        }
        return logParser;
    }

    public LogFormatter acquireLogFormatter(Class<? extends LogFormatter> cls) {
        LogFormatter logFormatter = logFormatterMap.getOrDefault(cls, defaultLogFormatter);
        if (logFormatter instanceof DefaultLogFormatter) {
            return ((DefaultLogFormatter) logFormatter).getLogFormatter();
        }
        return logFormatter;
    }

}
