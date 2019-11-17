package com.ezcoding.base.web.extend.spring.aspect;

import com.ezcoding.base.web.extend.spring.aspect.impl.ParamLogInfo;
import com.ezcoding.base.web.extend.spring.aspect.impl.ServiceLogInfo;
import com.ezcoding.base.web.extend.spring.aspect.impl.Slf4jLogger;
import com.ezcoding.base.web.extend.spring.aspect.impl.SystemOutputLogger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-03 20:37
 */
@Aspect
public class ServiceLoggerAspect {

    private static Map<Class, IServiceLogger> map = new HashMap<>();
    private static IServiceLogger defaultLogger;

    public ServiceLoggerAspect() {
        map.put(SystemOutputLogger.class, new SystemOutputLogger());
        map.put(Slf4jLogger.class, new Slf4jLogger());

        defaultLogger = map.get(Slf4jLogger.class);
    }

    @Pointcut("@annotation(ServiceLog)")
    public void doLog() {

    }

    @Around(value = "doLog()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //获取调用参数
        Object[] args = proceedingJoinPoint.getArgs();
        //实际调用的对象
        Object target = proceedingJoinPoint.getTarget();
        Class[] parameterTypes = ((MethodSignature) proceedingJoinPoint.getSignature()).getParameterTypes();
        Method method = target.getClass().getMethod(proceedingJoinPoint.getSignature().getName(), parameterTypes);

        Parameter[] parameters = method.getParameters();
        ArrayList<ParamLogInfo> ags = new ArrayList<>();
        //获取调用的参数
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            if (parameter.isAnnotationPresent(ParamLog.class)) {
                ParamLog paramLog = parameter.getAnnotation(ParamLog.class);
                ags.add(new ParamLogInfo(paramLog.name(), paramLog.expression(), args[i]));
            }
        }

        //开始执行
        ServiceLog serviceLog = method.getAnnotation(ServiceLog.class);
        Class<?> aClass = serviceLog.implmentClass();
        IServiceLogger serviceLogger = map.getOrDefault(aClass, defaultLogger);
        Arrays.stream(serviceLog.beforeExpression()).forEach(exp -> {
            serviceLogger.logBefore(target, new ServiceLogInfo(serviceLog.implmentClass(), exp, method), ags);
        });

        Object o = proceedingJoinPoint.proceed();

        //填充返回参数
        ParamLogInfo rl = null;
        if (method.isAnnotationPresent(ParamLog.class)) {
            ParamLog paramLog = method.getAnnotation(ParamLog.class);
            rl = new ParamLogInfo(paramLog.name(), paramLog.expression(), o);
        }

        ParamLogInfo finalRl = rl;
        Arrays.stream(serviceLog.afterExpression()).forEach(exp -> {
            serviceLogger.logAfter(target, new ServiceLogInfo(serviceLog.implmentClass(), exp, method), ags, finalRl);
        });

        return o;
    }
}
