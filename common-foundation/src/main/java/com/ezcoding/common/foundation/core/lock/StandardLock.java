package com.ezcoding.common.foundation.core.lock;

import com.ezcoding.common.foundation.core.lock.impl.DefaultLockIdentification;
import com.ezcoding.common.foundation.core.lock.impl.DefaultLockImplement;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-28 17:47
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface StandardLock {

    /**
     * 锁取值
     *
     * @return 锁表达式
     */
    String key();

    /**
     * 锁前缀
     *
     * @return 锁前缀
     */
    String prefix() default "";

    /**
     * 过期时间
     *
     * @return 过期时间
     */
    long expireTime() default 5;

    /**
     * 获取锁等待时长，若为0则马上判断获取是否成功
     *
     * @return 等待时长
     */
    long waitTime() default 0;

    /**
     * 时间单位
     *
     * @return 时间单位
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 锁默认实现方式
     *
     * @return 锁默认实现方式
     */
    Class<? extends LockImplement> processorClass() default DefaultLockImplement.class;

    /**
     * 锁默认实现方式
     *
     * @return 锁默认实现方式
     */
    Class<? extends LockIdentification> identificationClass() default DefaultLockIdentification.class;

}
