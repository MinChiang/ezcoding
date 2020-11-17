package com.ezcoding.common.web.aspect.impl;

import com.ezcoding.common.web.aspect.*;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-04 14:45
 */
public class ServiceLogInfo {

    private static final Map<Class<? extends ServiceLogger>, ServiceLogger> SERVICE_LOGGER_MAP = new HashMap<>();
    private static final Map<Class<? extends LogParser>, LogParser> PARAMETER_PARSER_MAP = new HashMap<>();
    private static final Map<Class<? extends LogFormatter>, LogFormatter> LOG_FORMATTER_MAP = new HashMap<>();
    private static final ServiceLogger DEFAULT_SERVICE_LOGGER;
    private static final LogParser DEFAULT_LOG_PARSER;
    private static final LogFormatter DEFAULT_LOG_FORMATTER;

    static {
        DEFAULT_SERVICE_LOGGER = new Slf4jLogger();
        SERVICE_LOGGER_MAP.put(Slf4jLogger.class, DEFAULT_SERVICE_LOGGER);
        SERVICE_LOGGER_MAP.put(SystemOutputLogger.class, new SystemOutputLogger());

        DEFAULT_LOG_PARSER = new SpelParameterParser();
        PARAMETER_PARSER_MAP.put(LogParser.class, DEFAULT_LOG_PARSER);

        DEFAULT_LOG_FORMATTER = new StringLogFormatter();
        LOG_FORMATTER_MAP.put(StringLogFormatter.class, DEFAULT_LOG_FORMATTER);
    }

    private final String beforeExpression;
    private final String afterExpression;
    private final LogTypeEnum type;
    private final Class<? extends LogFormatter> formatClass;
    private final Class<? extends ServiceLogger> logClass;
    private final Object target;
    private final LogFormatter formatter;
    private final ServiceLogger serviceLogger;
    private final Method method;

    private ServiceLogInfo(String beforeExpression, String afterExpression, LogTypeEnum type, Class<? extends LogFormatter> formatClass, Class<? extends ServiceLogger> logClass, Object target, Method method) {
        this.beforeExpression = beforeExpression;
        this.afterExpression = afterExpression;
        this.type = type;
        this.formatClass = formatClass;
        this.logClass = logClass;
        this.target = target;
        this.method = method;

        this.formatter = LOG_FORMATTER_MAP.getOrDefault(this.formatClass, DEFAULT_LOG_FORMATTER);
        this.serviceLogger = SERVICE_LOGGER_MAP.getOrDefault(this.logClass, DEFAULT_SERVICE_LOGGER);
    }

    /**
     * 构建对象
     *
     * @param serviceLog 注解
     * @param target     调用对象
     * @param method     调用方法
     * @return 构建的对象
     */
    public static ServiceLogInfo create(ServiceLog serviceLog, Object target, Method method) {
        return new ServiceLogInfo(
                serviceLog.beforeExpression(),
                serviceLog.afterExpression(),
                serviceLog.type(),
                serviceLog.formatClass(),
                serviceLog.logClass(),
                target,
                method
        );
    }

    private List<ParamLogInfo> acquireParamLogInfos(AnnotatedElement annotatedElement, Object arg) {
        List<ParamLogInfo> paramLogInfos = new ArrayList<>();
        if (annotatedElement.isAnnotationPresent(ParamLog.class)) {
            ParamLog paramLog = annotatedElement.getAnnotation(ParamLog.class);
            String[] expressions = paramLog.expressions();
            String[] names = paramLog.names();
            if (names.length != expressions.length) {
                //自动填充对应的名字
                names = Arrays.copyOf(names, expressions.length);
//                Arrays.fill(names, names.length, expressions.length, parameter.getName());
            }
            LogParser parser = PARAMETER_PARSER_MAP.getOrDefault(paramLog.parseClass(), DEFAULT_LOG_PARSER);
            for (int i = 0; i < expressions.length; i++) {
                ParamLogInfo paramLogInfo = new ParamLogInfo(names[i], expressions[i], arg);
                paramLogInfos.add(paramLogInfo);
                parser.parse(paramLogInfo);
            }
        }
        return paramLogInfos;
    }

    /**
     * 打印日志
     *
     * @param args 入参
     */
    public void logBefore(Object[] args) {
        //打印入参
        Parameter[] parameters = method.getParameters();
        List<ParamLogInfo> paramLogInfos = new ArrayList<>();
        for (int i = 0; i < parameters.length; i++) {
            paramLogInfos.addAll(this.acquireParamLogInfos(parameters[i], args[i]));
        }
        String beforeMessage = formatter.format(this.getBeforeExpression(), paramLogInfos);
        serviceLogger.log(beforeMessage, this, paramLogInfos);
    }

    /**
     * 打印日志
     *
     * @param result 出参
     */
    public void logAfter(Object result) {
        List<ParamLogInfo> resultLogInfos = this.acquireParamLogInfos(method, result);
        String afterMessage = formatter.format(this.getAfterExpression(), resultLogInfos);
        serviceLogger.log(afterMessage, this, resultLogInfos);
    }

    public String getBeforeExpression() {
        return beforeExpression;
    }

    public String getAfterExpression() {
        return afterExpression;
    }

    public LogTypeEnum getType() {
        return type;
    }

    public Class<? extends LogFormatter> getFormatClass() {
        return formatClass;
    }

    public Class<? extends ServiceLogger> getLogClass() {
        return logClass;
    }

    public Object getTarget() {
        return target;
    }

    public LogFormatter getFormatter() {
        return formatter;
    }

    public ServiceLogger getServiceLogger() {
        return serviceLogger;
    }

}
