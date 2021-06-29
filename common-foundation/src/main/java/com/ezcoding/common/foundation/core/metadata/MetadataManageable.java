package com.ezcoding.common.foundation.core.metadata;

import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-06-29 10:40
 */
public interface MetadataManageable {

    /**
     * 加载元数据
     */
    void load();

    /**
     * 获取全部元数据
     *
     * @return 全部元数据
     */
    Map<String, Metadata> getAll();

    /**
     * 获取单个元数据
     *
     * @param key 字典
     * @return 元数据
     */
    Metadata get(String key);

    /**
     * 获取元数据
     *
     * @param key 字典
     * @return 元数据
     */
    String getString(String key);

    /**
     * 获取元数据
     *
     * @param key 字典
     * @return 元数据
     */
    Character getCharacter(String key);

    /**
     * 获取元数据
     *
     * @param key 字典
     * @return 元数据
     */
    Boolean getBoolean(String key);

    /**
     * 获取元数据
     *
     * @param key 字典
     * @return 元数据
     */
    Byte getByte(String key);

    /**
     * 获取元数据
     *
     * @param key 字典
     * @return 元数据
     */
    Short getShort(String key);

    /**
     * 获取元数据
     *
     * @param key 字典
     * @return 元数据
     */
    Integer getInteger(String key);

    /**
     * 获取元数据
     *
     * @param key 字典
     * @return 元数据
     */
    Long getLong(String key);

    /**
     * 获取元数据
     *
     * @param key 字典
     * @return 元数据
     */
    Float getFloat(String key);

    /**
     * 获取元数据
     *
     * @param key 字典
     * @return 元数据
     */
    Double getDouble(String key);

    /**
     * 获取元数据
     *
     * @param key           字典
     * @param defaultResult 默认值
     * @return 元数据
     */
    Character getCharacter(String key, Character defaultResult);

    /**
     * 获取元数据
     *
     * @param key           字典
     * @param defaultResult 默认值
     * @return 元数据
     */
    Boolean getBoolean(String key, Boolean defaultResult);

    /**
     * 获取元数据
     *
     * @param key           字典
     * @param defaultResult 默认值
     * @return 元数据
     */
    Byte getByte(String key, Byte defaultResult);

    /**
     * 获取元数据
     *
     * @param key           字典
     * @param defaultResult 默认值
     * @return 元数据
     */
    Short getShort(String key, Short defaultResult);

    /**
     * 获取元数据
     *
     * @param key           字典
     * @param defaultResult 默认值
     * @return 元数据
     */
    Integer getInteger(String key, Integer defaultResult);

    /**
     * 获取元数据
     *
     * @param key           字典
     * @param defaultResult 默认值
     * @return 元数据
     */
    Long getLong(String key, Long defaultResult);

    /**
     * 获取元数据
     *
     * @param key           字典
     * @param defaultResult 默认值
     * @return 元数据
     */
    Float getFloat(String key, Float defaultResult);

    /**
     * 获取元数据
     *
     * @param key           字典
     * @param defaultResult 默认值
     * @return 元数据
     */
    Double getDouble(String key, Double defaultResult);

}
