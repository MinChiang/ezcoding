package com.ezcoding.common.foundation.starter;

import com.ezcoding.common.foundation.core.constant.AopConstants;
import com.ezcoding.common.foundation.core.log.ServiceLogger;
import com.ezcoding.common.foundation.core.log.ServiceLoggerFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-12-03 11:59
 */
@Aspect
@Order(AopConstants.Order.LOG_ORDER)
@Configuration
@ConditionalOnProperty(prefix = "ezcoding.foundation.log", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ServiceLogConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceLogConfiguration.class);

    @Autowired
    private EzcodingFoundationConfigBean ezcodingFoundationConfigBean;

    @Autowired
    private ServiceLoggerFactory serviceLoggerFactory;

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
        //获取参数
        Object[] args = proceedingJoinPoint.getArgs();

        //打印入参
        ServiceLogger serviceLogger = serviceLoggerFactory.create(method);
        serviceLogger.logBefore(target, args);

        //执行业务
        Object result = proceedingJoinPoint.proceed();

        //打印出参
        serviceLogger.logAfter(target, result);
        return result;
    }

    @Bean
    @ConditionalOnMissingBean
    public ServiceLoggerFactory serviceLoggerFactory() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        LogConfigBean logConfig = ezcodingFoundationConfigBean.getLog();

        ServiceLoggerFactory serviceLoggerFactory = ServiceLoggerFactory.defaultFactory();
        serviceLoggerFactory.setDefaultLogFormatter(getInstance(logConfig.getDefaultFormatterClass()));
        serviceLoggerFactory.setDefaultLogParser(getInstance(logConfig.getDefaultParserClass()));
        serviceLoggerFactory.setDefaultLogPrinter(getInstance(logConfig.getDefaultPrinterClass()));

        return serviceLoggerFactory;
    }

    /**
     * 实例化
     *
     * @param classString 列表
     * @param <T>         类型
     * @return 实例
     */
    private <T> T getInstance(String classString) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        try {
            Class<T> cls = (Class<T>) Class.forName(classString);
            return cls.newInstance();
        } catch (Exception e) {
            LOGGER.error("unable to find or instance class : {}", classString);
            throw e;
        }
    }

}
