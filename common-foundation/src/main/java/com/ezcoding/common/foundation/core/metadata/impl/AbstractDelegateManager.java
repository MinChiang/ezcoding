package com.ezcoding.common.foundation.core.metadata.impl;

import com.ezcoding.common.foundation.core.metadata.Metadata;
import com.ezcoding.common.foundation.core.metadata.MetadataManageable;

import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-06-29 19:58
 */
public abstract class AbstractDelegateManager implements MetadataManageable {

    private final MetadataManageable target;

    public AbstractDelegateManager(MetadataManageable target) {
        this.target = target;
    }

    @Override
    public void load() {
        this.target.load();
    }

    @Override
    public Map<String, Metadata> getAll() {
        return this.target.getAll();
    }

    @Override
    public Metadata get(String key) {
        return this.target.get(key);
    }

    @Override
    public String getString(String key) {
        return this.target.getString(key);
    }

    @Override
    public Character getCharacter(String key) {
        return this.target.getCharacter(key);
    }

    @Override
    public Boolean getBoolean(String key) {
        return this.target.getBoolean(key);
    }

    @Override
    public Byte getByte(String key) {
        return this.target.getByte(key);
    }

    @Override
    public Short getShort(String key) {
        return this.target.getShort(key);
    }

    @Override
    public Integer getInteger(String key) {
        return this.target.getInteger(key);
    }

    @Override
    public Long getLong(String key) {
        return this.target.getLong(key);
    }

    @Override
    public Float getFloat(String key) {
        return this.target.getFloat(key);
    }

    @Override
    public Double getDouble(String key) {
        return this.target.getDouble(key);
    }

    @Override
    public Character getCharacter(String key, Character defaultResult) {
        return this.target.getCharacter(key, defaultResult);
    }

    @Override
    public Boolean getBoolean(String key, Boolean defaultResult) {
        return this.target.getBoolean(key, defaultResult);
    }

    @Override
    public Byte getByte(String key, Byte defaultResult) {
        return this.target.getByte(key, defaultResult);
    }

    @Override
    public Short getShort(String key, Short defaultResult) {
        return this.target.getShort(key, defaultResult);
    }

    @Override
    public Integer getInteger(String key, Integer defaultResult) {
        return this.target.getInteger(key, defaultResult);
    }

    @Override
    public Long getLong(String key, Long defaultResult) {
        return this.target.getLong(key, defaultResult);
    }

    @Override
    public Float getFloat(String key, Float defaultResult) {
        return this.target.getFloat(key, defaultResult);
    }

    @Override
    public Double getDouble(String key, Double defaultResult) {
        return this.target.getDouble(key, defaultResult);
    }

}
