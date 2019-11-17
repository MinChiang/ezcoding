package com.ezcoding.common.foundation.core.context;

import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-10-24 10:50
 */
public interface IApplicationContext {

    /**
     * 设置对象到上下文中
     *
     * @param key   标志值
     * @param value 设置的对象
     */
    void put(String key, Object value);

    /**
     * 从上下文中获取对象
     *
     * @param key 标志值
     * @return 目标对象
     */
    Object get(String key);

    /**
     * 获取整个上下文
     *
     * @return 获取的上下文
     */
    Map<String, Object> getContext();

    /**
     * 设置上下文
     *
     * @param context 待设置的上下文
     */
    void setContext(Map<String, Object> context);

    /**
     * 清空上下文中的所有对象
     */
    void clear();

}
