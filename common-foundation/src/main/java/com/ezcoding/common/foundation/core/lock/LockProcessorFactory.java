package com.ezcoding.common.foundation.core.lock;

import com.ezcoding.common.foundation.core.lock.impl.SimpleLockIdentification;
import com.ezcoding.common.foundation.core.lock.impl.SimpleLockImplement;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-19 14:45
 */
public class LockProcessorFactory {

    private final LockConfig lockConfig;

    private LockProcessorFactory(LockConfig lockConfig) {
        this.lockConfig = lockConfig;
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

    /**
     * 构建器
     *
     * @return 构建器
     */
    public static LockProcessorFactoryBuilder builder() {
        return new LockProcessorFactoryBuilder();
    }

    /**
     * 此处使用建造者模式，为了：
     * 在运行前确定lockConfig中所有的确定性配置
     * 特别是lockConfig中保存的各种map，为了运行时无需加锁
     */
    public static class LockProcessorFactoryBuilder {

        private final Map<Class<? extends LockImplement>, LockImplement> lockImplementMap = new HashMap<>();
        private final Map<Class<? extends LockIdentification>, LockIdentification> lockIdentificationMap = new HashMap<>();

        private LockImplement defaultLockImplement = new SimpleLockImplement();
        private LockIdentification defaultLockIdentification = new SimpleLockIdentification();

        private LockProcessorFactoryBuilder() {
        }

        public LockProcessorFactoryBuilder defaultLockImplement(LockImplement defaultLockImplement) {
            this.defaultLockImplement = defaultLockImplement;
            return this;
        }

        public LockProcessorFactoryBuilder defaultLockIdentification(LockIdentification defaultLockIdentification) {
            this.defaultLockIdentification = defaultLockIdentification;
            return this;
        }

        public LockProcessorFactoryBuilder lockImplement(LockImplement lockImplement) {
            this.lockImplementMap.put(lockImplement.getClass(), lockImplement);
            return this;
        }

        public LockProcessorFactoryBuilder lockIdentification(LockIdentification lockIdentification) {
            this.lockIdentificationMap.put(lockIdentification.getClass(), lockIdentification);
            return this;
        }

        public LockProcessorFactoryBuilder lockImplements(List<LockImplement> lockImplements) {
            lockImplements.forEach(this::lockImplement);
            return this;
        }

        public LockProcessorFactoryBuilder lockIdentifications(List<LockIdentification> lockIdentifications) {
            lockIdentifications.forEach(this::lockIdentification);
            return this;
        }

        public Map<Class<? extends LockImplement>, LockImplement> getLockImplementMap() {
            return lockImplementMap;
        }

        public Map<Class<? extends LockIdentification>, LockIdentification> getLockIdentificationMap() {
            return lockIdentificationMap;
        }

        public LockImplement getDefaultLockImplement() {
            return defaultLockImplement;
        }

        public LockIdentification getDefaultLockIdentification() {
            return defaultLockIdentification;
        }

        /**
         * 构建对象
         *
         * @return 构建后的对象实例
         */
        public LockProcessorFactory build() {
            LockConfig lockConfig = new LockConfig(
                    this.lockImplementMap,
                    this.lockIdentificationMap,
                    this.defaultLockImplement,
                    this.defaultLockIdentification
            );
            return new LockProcessorFactory(lockConfig);
        }

    }

}
