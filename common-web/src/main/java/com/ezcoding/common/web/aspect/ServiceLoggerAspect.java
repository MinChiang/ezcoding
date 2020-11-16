package com.ezcoding.common.web.aspect;

import com.ezcoding.common.web.aspect.impl.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-03 20:37
 */
@Aspect
public class ServiceLoggerAspect {

    private static final Map<Class<? extends ServiceLogger>, ServiceLogger> SERVICE_LOGGER_MAP = new HashMap<>();
    private static final Map<Class<? extends LogParser>, LogParser> PARAMETER_PARSER_MAP = new HashMap<>();
    private static final Map<Class<? extends LogFormatter>, LogFormatter> LOG_FORMATTER_MAP = new HashMap<>();
    private static ServiceLogger defaultServiceLogger;
    private static LogParser defaultLogParser;
    private static LogFormatter defaultLogFormatter;

    public ServiceLoggerAspect() {
        defaultServiceLogger = new Slf4jLogger();
        SERVICE_LOGGER_MAP.put(Slf4jLogger.class, defaultServiceLogger);
        SERVICE_LOGGER_MAP.put(SystemOutputLogger.class, new SystemOutputLogger());

        defaultLogParser = new SpelParameterParser();
        PARAMETER_PARSER_MAP.put(LogParser.class, defaultLogParser);

        defaultLogFormatter = new StringLogFormatter();
        LOG_FORMATTER_MAP.put(StringLogFormatter.class, defaultLogFormatter);
    }

    @Pointcut("@annotation(ServiceLog)")
    public void doLog() {

    }

    @Around(value = "doLog()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //实际调用的对象
        Object target = proceedingJoinPoint.getTarget();
        //获取参数
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Class<?>[] parameterTypes = signature.getParameterTypes();
        Object[] args = proceedingJoinPoint.getArgs();
        //获取方法
        Method method = target.getClass().getMethod(proceedingJoinPoint.getSignature().getName(), parameterTypes);

        ServiceLog serviceLog = method.getAnnotation(ServiceLog.class);
        ServiceLogInfo serviceLogInfo = ServiceLogInfo.create(serviceLog, target);
        LogFormatter formatter = LOG_FORMATTER_MAP.getOrDefault(serviceLogInfo.getFormatClass(), defaultLogFormatter);
        ServiceLogger serviceLogger = SERVICE_LOGGER_MAP.getOrDefault(serviceLogInfo.getLogClass(), defaultServiceLogger);

        //打印入参
        Parameter[] parameters = method.getParameters();
        List<ParamLogInfo> paramLogInfos = new ArrayList<>();
        for (int i = 0; i < parameters.length; i++) {
            doAcquireParamLogInfos(paramLogInfos, parameters[i], args[i]);
        }
        String beforeMessage = formatter.format(serviceLogInfo.getBeforeExpression(), paramLogInfos);
        serviceLogger.log(beforeMessage, serviceLogInfo, paramLogInfos);

        //执行业务
        Object result = proceedingJoinPoint.proceed();

        //打印出参
        List<ParamLogInfo> resultLogInfos = new ArrayList<>();
        doAcquireParamLogInfos(resultLogInfos, method, result);
        String afterMessage = formatter.format(serviceLogInfo.getAfterExpression(), resultLogInfos);
        serviceLogger.log(afterMessage, serviceLogInfo, resultLogInfos);

        return result;
    }

    private void doAcquireParamLogInfos(List<ParamLogInfo> paramLogInfos, AnnotatedElement annotatedElement, Object arg) {
        if (annotatedElement.isAnnotationPresent(ParamLog.class)) {
            ParamLog paramLog = annotatedElement.getAnnotation(ParamLog.class);
            String[] expressions = paramLog.expressions();
            String[] names = paramLog.names();
            if (names.length != expressions.length) {
                //自动填充对应的名字
                names = Arrays.copyOf(names, expressions.length);
//                Arrays.fill(names, names.length, expressions.length, parameter.getName());
            }
            LogParser parser = PARAMETER_PARSER_MAP.getOrDefault(paramLog.parseClass(), defaultLogParser);
            for (int j = 0; j < expressions.length; j++) {
                ParamLogInfo paramLogInfo = new ParamLogInfo(names[j], expressions[j], arg);
                paramLogInfos.add(paramLogInfo);
                parser.parse(paramLogInfo);
            }
        }
    }

}
