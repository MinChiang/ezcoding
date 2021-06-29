package com.ezcoding.common.foundation.core.metadata.impl;

import com.ezcoding.common.foundation.core.metadata.BaseMetadataFetchable;
import com.ezcoding.common.foundation.core.metadata.Metadata;
import com.ezcoding.common.foundation.core.metadata.MetadataManageable;
import com.ezcoding.common.foundation.util.ConvertUtils;

import java.util.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-06-29 10:42
 */
public class AggregateMetadataManager implements MetadataManageable {

    private final List<BaseMetadataFetchable> fetchers = new ArrayList<>();
    private Map<String, Metadata> metadatas;

    public AggregateMetadataManager(List<BaseMetadataFetchable> fetchers) {
        this.register(fetchers);
    }

    public AggregateMetadataManager() {
    }

    /**
     * 注册加载器
     *
     * @param fetchers 需要注册的加载器
     */
    public void register(List<BaseMetadataFetchable> fetchers) {
        if (fetchers == null || fetchers.isEmpty()) {
            return;
        }
        this.fetchers.addAll(fetchers);
        this.fetchers.sort(Comparator.comparingInt(BaseMetadataFetchable::order).reversed());
    }

    /**
     * 注册加载器
     *
     * @param fetcher 需要注册的加载器
     */
    public void register(BaseMetadataFetchable fetcher) {
        Optional.ofNullable(fetcher).ifPresent(f -> this.register(Collections.singletonList(f)));
    }

    /**
     * 获取并聚合数据
     *
     * @return 获取到的元数据
     */
    private Map<String, Metadata> fetch() {
        Map<String, Metadata> result = new HashMap<>();
        for (BaseMetadataFetchable fetcher : fetchers) {
            Map<String, Metadata> fetch = fetcher.fetch();
            result.putAll(fetch);
        }
        return result;
    }

    @Override
    public void load() {
        this.metadatas = Collections.unmodifiableMap(this.fetch());
    }

    @Override
    public Map<String, Metadata> getAll() {
        return this.metadatas;
    }

    @Override
    public Metadata get(String key) {
        return this.metadatas.get(key);
    }

    @Override
    public String getString(String key) {
        return getOptional(key).orElse(null);
    }

    @Override
    public Character getCharacter(String key) {
        return getOptional(key).map(c -> (Character) ConvertUtils.convertToPrimitive(Character.class, c)).orElse(null);
    }

    @Override
    public Boolean getBoolean(String key) {
        return getOptional(key).map(c -> (Boolean) ConvertUtils.convertToPrimitive(Boolean.class, c)).orElse(null);
    }

    @Override
    public Byte getByte(String key) {
        return getOptional(key).map(c -> (Byte) ConvertUtils.convertToPrimitive(Byte.class, c)).orElse(null);
    }

    @Override
    public Short getShort(String key) {
        return getOptional(key).map(c -> (Short) ConvertUtils.convertToPrimitive(Short.class, c)).orElse(null);
    }

    @Override
    public Integer getInteger(String key) {
        return getOptional(key).map(c -> (Integer) ConvertUtils.convertToPrimitive(Integer.class, c)).orElse(null);
    }

    @Override
    public Long getLong(String key) {
        return getOptional(key).map(c -> (Long) ConvertUtils.convertToPrimitive(Long.class, c)).orElse(null);
    }

    @Override
    public Float getFloat(String key) {
        return getOptional(key).map(c -> (Float) ConvertUtils.convertToPrimitive(Float.class, c)).orElse(null);
    }

    @Override
    public Double getDouble(String key) {
        return getOptional(key).map(c -> (Double) ConvertUtils.convertToPrimitive(Double.class, c)).orElse(null);
    }

    @Override
    public Character getCharacter(String key, Character defaultResult) {
        return getOptional(key).map(c -> (Character) ConvertUtils.convertToPrimitive(Character.class, c)).orElse(defaultResult);
    }

    @Override
    public Boolean getBoolean(String key, Boolean defaultResult) {
        return getOptional(key).map(c -> (Boolean) ConvertUtils.convertToPrimitive(Boolean.class, c)).orElse(defaultResult);
    }

    @Override
    public Byte getByte(String key, Byte defaultResult) {
        return getOptional(key).map(c -> (Byte) ConvertUtils.convertToPrimitive(Byte.class, c)).orElse(defaultResult);
    }

    @Override
    public Short getShort(String key, Short defaultResult) {
        return getOptional(key).map(c -> (Short) ConvertUtils.convertToPrimitive(Short.class, c)).orElse(defaultResult);
    }

    @Override
    public Integer getInteger(String key, Integer defaultResult) {
        return getOptional(key).map(c -> (Integer) ConvertUtils.convertToPrimitive(Integer.class, c)).orElse(defaultResult);
    }

    @Override
    public Long getLong(String key, Long defaultResult) {
        return getOptional(key).map(c -> (Long) ConvertUtils.convertToPrimitive(Long.class, c)).orElse(defaultResult);
    }

    @Override
    public Float getFloat(String key, Float defaultResult) {
        return getOptional(key).map(c -> (Float) ConvertUtils.convertToPrimitive(Float.class, c)).orElse(defaultResult);
    }

    @Override
    public Double getDouble(String key, Double defaultResult) {
        return getOptional(key).map(c -> (Double) ConvertUtils.convertToPrimitive(Double.class, c)).orElse(defaultResult);
    }

    /**
     * 获取对应的内容
     *
     * @param key 字典
     * @return optional内容
     */
    private Optional<String> getOptional(String key) {
        return Optional.ofNullable(get(key)).map(Metadata::getContent);
    }

}
