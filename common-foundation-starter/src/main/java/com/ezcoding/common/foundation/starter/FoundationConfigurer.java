package com.ezcoding.common.foundation.starter;

import com.ezcoding.common.foundation.core.enums.EnumMappableStrategy;
import com.ezcoding.common.foundation.core.exception.processor.AbstractLayerModuleProcessor;
import com.ezcoding.common.foundation.core.exception.processor.ModuleApplicationExceptionManager;
import com.ezcoding.common.foundation.core.lock.LockIdentification;
import com.ezcoding.common.foundation.core.lock.LockImplement;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-20 14:02
 */
public interface FoundationConfigurer {

    /**
     * 注册服务级别错误处理器
     *
     * @param moduleApplicationExceptionManager 被注册的错误管理器
     * @param defaultProcessor                  默认处理器
     */
    default void registerApplicationProcessor(ModuleApplicationExceptionManager moduleApplicationExceptionManager, AbstractLayerModuleProcessor defaultProcessor) {
    }

    /**
     * 注册模块级别错误处理器
     *
     * @param moduleApplicationExceptionManager 被注册的错误管理器
     * @param defaultProcessor                  默认处理器
     */
    default void registerModuleProcessor(ModuleApplicationExceptionManager moduleApplicationExceptionManager, AbstractLayerModuleProcessor defaultProcessor) {
    }

    /**
     * 注册服功能别错误处理器
     *
     * @param moduleApplicationExceptionManager 被注册的错误管理器
     * @param defaultProcessor                  默认处理器
     */
    default void registerFunctionProcessor(ModuleApplicationExceptionManager moduleApplicationExceptionManager, AbstractLayerModuleProcessor defaultProcessor) {
    }

    /**
     * 注册额外的enum解析策略
     *
     * @param strategies 解析器列表
     */
    default void registerEnumStrategy(List<EnumMappableStrategy> strategies) {
    }

    /**
     * 注册锁实现
     *
     * @param lockImplements 锁实现列表
     */
    default void registerLockImplement(List<LockImplement> lockImplements) {
    }

    /**
     * 注册锁处理对象
     *
     * @param lockIdentifications 锁处理对象列表
     */
    default void registerLockIdentification(List<LockIdentification> lockIdentifications) {
    }

}
