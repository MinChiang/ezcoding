package com.ezcoding.common.foundation.core.lock;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-19 14:45
 */
public class LockProcessorFactory {

//    private final Map<Method, LockProcessor> map = Collections.synchronizedMap(new WeakHashMap<>());
    private final LockConfig lockConfig = new LockConfig();

    private LockProcessorFactory() {
    }

    public static LockProcessorFactory defaultFactory() {
        return new LockProcessorFactory();
    }

    /**
     * 构建对象
     *
     * @param method 调用方法
     * @return 构建的对象
     */
    public LockProcessor create(Method method) {
        return new LockProcessor(
                this.lockConfig,
                method
        );
    }

//    /**
//     * 构建对象
//     *
//     * @param method 调用方法
//     * @return 构建的对象
//     */
//    public LockProcessor getOrCreate(Method method) {
//        return this.map.computeIfAbsent(method, key -> create(method));
//    }

    public void setDefaultLockIdentification(LockIdentification defaultLockIdentification) {
        this.lockConfig.setDefaultLockIdentification(defaultLockIdentification);
    }

    public void setDefaultLockImplement(LockImplement defaultLockImplement) {
        this.lockConfig.setDefaultLockImplement(defaultLockImplement);
    }

}
