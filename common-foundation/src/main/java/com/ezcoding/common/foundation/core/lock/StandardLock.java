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
    String key() default "";

    /**
     * 锁前缀
     * 为了防止所有上锁入口都加同一个锁
     *
     * @return 锁前缀
     */
    String prefix() default "";

    /**
     * 锁过期时间
     *
     * @return 过期时间
     */
    long expireTime() default 5;

    /**
     * 获取锁等待时长
     * 若为0则马上判断获取是否成功，若不为0则阻塞直到成功获取锁或超过等待时长
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
    Class<? extends LockImplement> implementClass() default DefaultLockImplement.class;

    /**
     * 锁默认实现方式
     *
     * @return 锁默认实现方式
     */
    Class<? extends LockIdentification> identificationClass() default DefaultLockIdentification.class;

    /**
     * 获取锁失败错误信息
     *
     * @return 获取锁失败信息
     */
    String failMessage() default "acquire lock error!";

}
