package com.ezcoding.redis.serializer;

import com.ezcoding.common.foundation.core.application.FunctionLayerModule;

import java.util.Objects;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-22 17:04
 */
public class StandardRedisKeyBuilder {

    private final FunctionLayerModule functionLayerModule;

    private StandardRedisKeyBuilder(FunctionLayerModule functionLayerModule) {
        this.functionLayerModule = functionLayerModule;
    }

    /**
     * 创建新对象
     *
     * @param key 对应的Key
     * @return 新的对象
     */
    public StandardRedisKey build(String key) {
        if (key == null || key.isEmpty()) {
            throw new NullPointerException("key can not be null or empty");
        }
        return new StandardRedisKey(this.functionLayerModule, key);
    }

    /**
     * 构建对象
     *
     * @param functionLayerModule 入参
     * @return 出参
     */
    public static StandardRedisKeyBuilder create(FunctionLayerModule functionLayerModule) {
        Objects.requireNonNull(functionLayerModule, "functionLayerModule can not be null");
        return new StandardRedisKeyBuilder(functionLayerModule);
    }

}
