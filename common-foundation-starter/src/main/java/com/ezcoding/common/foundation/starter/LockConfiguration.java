package com.ezcoding.common.foundation.starter;

import com.ezcoding.common.foundation.core.constant.AopConstants;
import com.ezcoding.common.foundation.core.lock.*;
import com.ezcoding.common.foundation.core.lock.impl.SimpleLockIdentification;
import com.ezcoding.common.foundation.core.lock.impl.SimpleLockImplement;
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
public class LockConfiguration implements FoundationConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(LockConfiguration.class);

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
        //获取参数名称
        String[] parameterNames = signature.getParameterNames();
        //获取方法
        Method method = signature.getMethod();
        //获取参数
        Object[] args = proceedingJoinPoint.getArgs();

        //锁结果
        LockResult lockResult = null;
        //业务执行结果
        Object result;
        LockContext lockContext = new LockContext();
        LockRuntime lockRuntime = new LockRuntime(target, args, method, parameterNames, lockContext);
        LockProcessor lockProcessor = lockProcessorFactory.create(lockRuntime);
        try {
            lockResult = lockProcessor.lock();
            if (!lockResult.success()) {
                throw new LockFailException(lockProcessor.lockMetadata.failMessage);
            }
            //执行业务
            result = proceedingJoinPoint.proceed();
        } catch (Exception e) {
            throw new LockFailException(lockProcessor.lockMetadata.failMessage, e);
        } finally {
            if (lockResult != null && lockResult.success()) {
                try {
                    lockProcessor.unlock(lockResult.acquireKey());
                } catch (Exception e) {
                    LOGGER.debug("lockProcessor unlock error!", e);
                }
            }
            if (!lockContext.isEmpty()) {
                lockContext.clear();
            }
        }
        return result;
    }

    @Bean
    @ConditionalOnMissingBean
    public LockProcessorFactory lockProcessorFactory(@Autowired(required = false) List<FoundationConfigurer> configurers,
                                                     @Autowired LockImplement defaultLockImplement,
                                                     @Autowired LockIdentification defaultLockIdentification) {
        List<LockImplement> lockImplements = new ArrayList<>();
        List<LockIdentification> lockIdentifications = new ArrayList<>();
        if (configurers != null && !configurers.isEmpty()) {
            for (FoundationConfigurer configurer : configurers) {
                configurer.registerLockImplement(lockImplements);
                configurer.registerLockIdentification(lockIdentifications);
            }
        }

        return LockProcessorFactory
                .builder()
                .defaultLockImplement(defaultLockImplement)
                .defaultLockIdentification(defaultLockIdentification)
                .lockImplements(lockImplements)
                .lockIdentifications(lockIdentifications)
                .build();
    }

    @Bean("defaultLockImplement")
    @ConditionalOnMissingBean
    public LockImplement defaultLockImplement() {
        return new SimpleLockImplement();
    }

    @Bean("defaultLockIdentification")
    @ConditionalOnMissingBean
    public LockIdentification defaultLockIdentification() {
        return new SimpleLockIdentification();
    }

    @Override
    public void registerLockImplement(List<LockImplement> lockImplements) {
        lockImplements.add(new SimpleLockImplement());
    }

    @Override
    public void registerLockIdentification(List<LockIdentification> lockIdentifications) {
        lockIdentifications.add(new SimpleLockIdentification());
    }

}
