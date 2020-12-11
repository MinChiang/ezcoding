package com.ezcoding.common.foundation.starter;

import com.ezcoding.common.foundation.core.constant.AopConstants;
import com.ezcoding.common.foundation.core.log.ServiceLogger;
import com.ezcoding.common.foundation.core.log.ServiceLoggerFactory;
import com.ezcoding.common.foundation.util.BeanUtils;
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

        return ServiceLoggerFactory
                .builder()
                .defaultLogFormatter(BeanUtils.getInstance(logConfig.getDefaultFormatterClass()))
                .defaultLogParser(BeanUtils.getInstance(logConfig.getDefaultParserClass()))
                .defaultLogPrinter(BeanUtils.getInstance(logConfig.getDefaultPrinterClass()))
                .logFormatters(BeanUtils.getInstances(logConfig.getFormatterClass()))
                .logParsers(BeanUtils.getInstances(logConfig.getParserClass()))
                .logPrinters(BeanUtils.getInstances(logConfig.getPrinterClass()))
                .build();
    }

}
