package com.ezcoding.common.foundation.core.metadata.impl;

import com.ezcoding.common.foundation.core.application.FunctionLayerModule;
import com.ezcoding.common.foundation.core.metadata.Metadata;
import com.ezcoding.common.foundation.core.metadata.MetadataIdentifiable;
import com.ezcoding.common.foundation.core.metadata.MetadataManageable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-06-29 19:55
 */
public class FunctionLayerModuleMetadataFetcher extends AbstractDelegateManager {

    private String pathSplitter;

    public FunctionLayerModuleMetadataFetcher(MetadataManageable target, String pathSplitter) {
        super(target);
        if (pathSplitter == null || pathSplitter.isEmpty()) {
            this.pathSplitter = MetadataIdentifiable.BUCKET_KEY_JOINER;
        }
        this.pathSplitter = pathSplitter;
    }

    public FunctionLayerModuleMetadataFetcher(MetadataManageable target) {
        this(target, null);
    }

    /**
     * 获取key
     *
     * @param functionLayerModule 功能模块
     * @return key
     */
    private String acquireKey(FunctionLayerModule functionLayerModule) {
        return functionLayerModule.getPath(this.pathSplitter);
    }

    /**
     * 获取单个元数据
     *
     * @param functionLayerModule 功能模块
     * @return 元数据
     */
    public Metadata get(FunctionLayerModule functionLayerModule) {
        return get(acquireKey(functionLayerModule));
    }

    /**
     * 获取元数据
     *
     * @param functionLayerModule 功能模块
     * @return 元数据
     */
    public String getString(FunctionLayerModule functionLayerModule) {
        return getString(acquireKey(functionLayerModule));
    }

    /**
     * 获取元数据
     *
     * @param functionLayerModule 功能模块
     * @return 元数据
     */
    Character getCharacter(FunctionLayerModule functionLayerModule) {
        return getCharacter(acquireKey(functionLayerModule));
    }

    /**
     * 获取元数据
     *
     * @param functionLayerModule 功能模块
     * @return 元数据
     */
    Boolean getBoolean(FunctionLayerModule functionLayerModule) {
        return getBoolean(acquireKey(functionLayerModule));
    }

    /**
     * 获取元数据
     *
     * @param functionLayerModule 功能模块
     * @return 元数据
     */
    Byte getByte(FunctionLayerModule functionLayerModule) {
        return getByte(acquireKey(functionLayerModule));
    }

    /**
     * 获取元数据
     *
     * @param functionLayerModule 功能模块
     * @return 元数据
     */
    Short getShort(FunctionLayerModule functionLayerModule) {
        return getShort(acquireKey(functionLayerModule));
    }

    /**
     * 获取元数据
     *
     * @param functionLayerModule 功能模块
     * @return 元数据
     */
    Integer getInteger(FunctionLayerModule functionLayerModule) {
        return getInteger(acquireKey(functionLayerModule));
    }

    /**
     * 获取元数据
     *
     * @param functionLayerModule 功能模块
     * @return 元数据
     */
    Long getLong(FunctionLayerModule functionLayerModule) {
        return getLong(acquireKey(functionLayerModule));
    }

    /**
     * 获取元数据
     *
     * @param functionLayerModule 功能模块
     * @return 元数据
     */
    Float getFloat(FunctionLayerModule functionLayerModule) {
        return getFloat(acquireKey(functionLayerModule));
    }

    /**
     * 获取元数据
     *
     * @param functionLayerModule 功能模块
     * @return 元数据
     */
    Double getDouble(FunctionLayerModule functionLayerModule) {
        return getDouble(acquireKey(functionLayerModule));
    }

    /**
     * 获取元数据
     *
     * @param functionLayerModule 功能模块
     * @param defaultResult       默认值
     * @return 元数据
     */
    Character getCharacter(FunctionLayerModule functionLayerModule, Character defaultResult) {
        return getCharacter(acquireKey(functionLayerModule), defaultResult);
    }

    /**
     * 获取元数据
     *
     * @param functionLayerModule 功能模块
     * @param defaultResult       默认值
     * @return 元数据
     */
    Boolean getBoolean(FunctionLayerModule functionLayerModule, Boolean defaultResult) {
        return getBoolean(acquireKey(functionLayerModule), defaultResult);
    }

    /**
     * 获取元数据
     *
     * @param functionLayerModule 功能模块
     * @param defaultResult       默认值
     * @return 元数据
     */
    Byte getByte(FunctionLayerModule functionLayerModule, Byte defaultResult) {
        return getByte(acquireKey(functionLayerModule), defaultResult);
    }

    /**
     * 获取元数据
     *
     * @param functionLayerModule 功能模块
     * @param defaultResult       默认值
     * @return 元数据
     */
    Short getShort(FunctionLayerModule functionLayerModule, Short defaultResult) {
        return getShort(acquireKey(functionLayerModule), defaultResult);
    }

    /**
     * 获取元数据
     *
     * @param functionLayerModule 功能模块
     * @param defaultResult       默认值
     * @return 元数据
     */
    Integer getInteger(FunctionLayerModule functionLayerModule, Integer defaultResult) {
        return getInteger(acquireKey(functionLayerModule), defaultResult);
    }

    /**
     * 获取元数据
     *
     * @param functionLayerModule 功能模块
     * @param defaultResult       默认值
     * @return 元数据
     */
    Long getLong(FunctionLayerModule functionLayerModule, Long defaultResult) {
        return getLong(acquireKey(functionLayerModule), defaultResult);
    }

    /**
     * 获取元数据
     *
     * @param functionLayerModule 功能模块
     * @param defaultResult       默认值
     * @return 元数据
     */
    Float getFloat(FunctionLayerModule functionLayerModule, Float defaultResult) {
        return getFloat(acquireKey(functionLayerModule), defaultResult);
    }

    /**
     * 获取元数据
     *
     * @param functionLayerModule 功能模块
     * @param defaultResult       默认值
     * @return 元数据
     */
    Double getDouble(FunctionLayerModule functionLayerModule, Double defaultResult) {
        return getDouble(acquireKey(functionLayerModule), defaultResult);
    }

}
