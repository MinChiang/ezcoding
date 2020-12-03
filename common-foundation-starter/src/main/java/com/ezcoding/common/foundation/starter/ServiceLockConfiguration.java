package com.ezcoding.common.foundation.starter;

import com.ezcoding.common.foundation.core.lock.LockRuntime;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-12-03 11:59
 */
@Aspect
@Configuration
@ConditionalOnProperty(prefix = "ezcoding.foundation.lock", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ServiceLockConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceLockConfiguration.class);

    @Autowired
    private EzcodingFoundationConfigBean ezcodingFoundationConfigBean;

    @Pointcut("@annotation(com.ezcoding.common.foundation.core.log.ServiceLog)")
    public void doLog() {

    }

    @Around(value = "doLog()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //实际调用的对象
        Object target = proceedingJoinPoint.getTarget();
        //获取参数
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        //获取方法
        Method method = signature.getMethod();

        LockRuntime lockRuntime = new LockRuntime(target, method, proceedingJoinPoint.getArgs());


        //执行业务
        Object result = proceedingJoinPoint.proceed();

        return result;
    }

}
