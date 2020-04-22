package com.ezcoding.redis.serializer;

import com.ezcoding.common.foundation.core.application.FunctionLayerModule;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-22 17:04
 */
public class StandardRedisKeyBuilder {

    private FunctionLayerModule functionLayerModule;

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
        return new StandardRedisKey(this.functionLayerModule, key);
    }

    /**
     * 构建对象
     *
     * @param functionLayerModule 入参
     * @return 出参
     */
    public static StandardRedisKeyBuilder create(FunctionLayerModule functionLayerModule) {
        return new StandardRedisKeyBuilder(functionLayerModule);
    }

}
