package com.ezcoding.common.foundation.starter;

import com.ezcoding.common.foundation.core.constant.AopConstants;
import com.ezcoding.common.foundation.core.lock.LockProcessor;
import com.ezcoding.common.foundation.core.lock.LockProcessorFactory;
import com.ezcoding.common.foundation.core.lock.LockResult;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-12-03 11:59
 */
@Aspect
@Order(AopConstants.Order.LOCK_ORDER)
@Configuration
@ConditionalOnProperty(prefix = "ezcoding.foundation.lock", name = "enabled", havingValue = "true", matchIfMissing = true)
public class LockConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(LockConfiguration.class);

    @Autowired
    private EzcodingFoundationConfigBean ezcodingFoundationConfigBean;

    @Autowired
    private LockProcessorFactory lockProcessorFactory;

    @Pointcut("@annotation(com.ezcoding.common.foundation.core.lock.StandardLock)")
    public void doLock() {

    }

    @Around(value = "doLock()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //实际调用的对象
        Object target = proceedingJoinPoint.getTarget();
        //获取参数
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        //获取方法
        Method method = signature.getMethod();
        //获取参数
        Object[] args = proceedingJoinPoint.getArgs();

        LockProcessor lockProcessor = lockProcessorFactory.create(method);
        LockResult lockResult = null;
        Object result = null;
        try {
            lockResult = lockProcessor.lock(target, args);
            if (lockResult.success()) {
                //执行业务
                result = proceedingJoinPoint.proceed();
            }
        } finally {
            if (lockResult != null && lockResult.success()) {
                lockProcessor.unlock(lockResult.acquireKey(), target, args);
            }
        }
        return result;
    }

    @Bean
    @ConditionalOnMissingBean
    public LockProcessorFactory lockProcessorFactory() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        LockConfigBean lockConfigBean = ezcodingFoundationConfigBean.getLock();

        return LockProcessorFactory
                .builder()
                .defaultLockImplement(getInstance(lockConfigBean.getDefaultLockImplementClass()))
                .defaultLockIdentification(getInstance(lockConfigBean.getDefaultLockIdentificationClass()))
                .lockImplements(getInstances(lockConfigBean.getLockImplementClass()))
                .lockIdentifications(getInstances(lockConfigBean.getIdentificationClass()))
                .build();
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

    /**
     * 实例化
     *
     * @param classStrings 列表
     * @param <T>          类型
     * @return 实例
     */
    private <T> List<T> getInstances(List<String> classStrings) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        List<T> result = new ArrayList<>();
        for (String classString : classStrings) {
            result.add(getInstance(classString));
        }
        return result;
    }

}
