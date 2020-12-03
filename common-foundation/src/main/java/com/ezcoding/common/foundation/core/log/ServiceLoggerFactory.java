package com.ezcoding.common.foundation.core.log;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-19 14:45
 */
public class ServiceLoggerFactory {

    private final Map<Method, ServiceLogger> map = Collections.synchronizedMap(new WeakHashMap<>());
    private final LogConfig logConfig = new LogConfig();

    private ServiceLoggerFactory() {
    }

    public static ServiceLoggerFactory defaultFactory() {
        return new ServiceLoggerFactory();
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

    public void setDefaultLogPrinter(LogPrinter defaultLogPrinter) {
        this.logConfig.setDefaultLogPrinter(defaultLogPrinter);
    }

    public void setDefaultLogParser(LogParser defaultLogParser) {
        this.logConfig.setDefaultLogParser(defaultLogParser);
    }

    public void setDefaultLogFormatter(LogFormatter defaultLogFormatter) {
        this.logConfig.setDefaultLogFormatter(defaultLogFormatter);
    }

}
