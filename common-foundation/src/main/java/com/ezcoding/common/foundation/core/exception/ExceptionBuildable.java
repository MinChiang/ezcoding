package com.ezcoding.common.foundation.core.exception;

import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-12-28 21:12
 */
public interface ExceptionBuildable extends ApplicationIdentifiable {

    /**
     * 构建异常
     *
     * @return 异常实例
     */
    ApplicationException build();

    /**
     * 设置错误原因
     *
     * @param cause 异常内容
     */
    void setCause(Throwable cause);

    /**
     * 设置上下文内容
     *
     * @param key   键
     * @param value 值
     * @return 错误上下文
     */
    Map<String, Object> setObject(String key, Object value);

    /**
     * 获取上下文内容
     *
     * @param key 键
     * @return 值
     */
    Object getObject(String key);

}
