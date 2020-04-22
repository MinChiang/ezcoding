package com.ezcoding.redis.serializer;

import com.ezcoding.common.foundation.core.application.FunctionLayerModule;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-22 16:09
 */
public class StandardRedisKey {

    private FunctionLayerModule functionLayerModule;

    private String key;

    public StandardRedisKey(FunctionLayerModule functionLayerModule, String key) {
        if (functionLayerModule == null || key == null) {
            throw new IllegalArgumentException("redis服务号或key不能为空");
        }
        this.functionLayerModule = functionLayerModule;
        this.key = key;
    }

    StandardRedisKey(String key) {
        this.key = key;
    }

    public FunctionLayerModule getFunctionLayerModule() {
        return functionLayerModule;
    }

    public void setFunctionLayerModule(FunctionLayerModule functionLayerModule) {
        this.functionLayerModule = functionLayerModule;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    static StandardRedisKey createWithoutCheck(String key) {
        return new StandardRedisKey(key);
    }

}
