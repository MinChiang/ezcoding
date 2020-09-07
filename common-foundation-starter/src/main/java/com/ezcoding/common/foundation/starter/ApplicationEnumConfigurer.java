package com.ezcoding.common.foundation.starter;

import com.ezcoding.common.foundation.core.enums.EnumMappableStrategy;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-09-07 11:01
 */
public interface ApplicationEnumConfigurer {

    /**
     * 注册额外的enum解析策略
     *
     * @param strategies 解析器列表
     */
    default void registerEnumStrategy(List<EnumMappableStrategy> strategies) {

    }

}
