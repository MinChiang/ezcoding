package com.ezcoding.common.foundation.core.lock;

import com.ezcoding.common.foundation.core.lock.impl.DefaultLockIdentification;
import com.ezcoding.common.foundation.core.lock.impl.DefaultLockImplement;
import com.ezcoding.common.foundation.core.lock.impl.SimpleLockIdentification;
import com.ezcoding.common.foundation.core.lock.impl.SimpleLockImplement;

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
     * @param lockRuntime 锁运行时内容
     * @return 构建的对象
     */
    public LockProcessor create(LockRuntime lockRuntime) {
        return new LockProcessor(
                this.lockConfig,
                lockRuntime
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

        /**
         * 构建对象
         * 需要设置默认的锁实现以及处理器
         *
         * @return 构建后的对象实例
         */
        public LockProcessorFactory build() {
            if (!lockImplementMap.containsKey(defaultLockImplement.getClass())) {
                lockImplementMap.put(defaultLockImplement.getClass(), defaultLockImplement);
            }
            // 处理默认的锁实现
            if (defaultLockImplement != null && !lockImplementMap.containsKey(DefaultLockImplement.class)) {
                if (defaultLockImplement instanceof DefaultLockImplement) {
                    lockImplementMap.put(DefaultLockImplement.class, defaultLockImplement);
                } else {
                    DefaultLockImplement lockImplement = new DefaultLockImplement();
                    lockImplement.setLockImplement(defaultLockImplement);
                    lockImplementMap.put(DefaultLockImplement.class, lockImplement);
                }
            }

            if (!lockIdentificationMap.containsKey(defaultLockIdentification.getClass())) {
                lockIdentificationMap.put(defaultLockIdentification.getClass(), defaultLockIdentification);
            }
            // 处理默认的锁处理
            if (defaultLockIdentification != null && !lockIdentificationMap.containsKey(DefaultLockIdentification.class)) {
                if (defaultLockIdentification instanceof DefaultLockIdentification) {
                    lockIdentificationMap.put(DefaultLockIdentification.class, defaultLockIdentification);
                } else {
                    DefaultLockIdentification lockIdentification = new DefaultLockIdentification();
                    lockIdentification.setDefaultLockIdentification(defaultLockIdentification);
                    lockIdentificationMap.put(DefaultLockIdentification.class, lockIdentification);
                }
            }

            LockConfig lockConfig = new LockConfig(
                    this.lockImplementMap,
                    this.lockIdentificationMap
            );
            return new LockProcessorFactory(lockConfig);
        }

    }

}
