package com.ezcoding.common.foundation.starter;

import com.ezcoding.common.foundation.core.exception.processor.ModuleApplicationExceptionManager;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-20 14:02
 */
public interface IApplicationExceptionProcessorRegister {

    /**
     * 注册服务级别错误处理器
     *
     * @param moduleApplicationExceptionManager 被注册的错误管理器
     */
    default void registerApplicationProcessor(ModuleApplicationExceptionManager moduleApplicationExceptionManager) {
    }

    /**
     * 注册模块级别错误处理器
     *
     * @param moduleApplicationExceptionManager 被注册的错误管理器
     */
    default void registerModuleProcessor(ModuleApplicationExceptionManager moduleApplicationExceptionManager) {
    }

    /**
     * 注册服功能别错误处理器
     *
     * @param moduleApplicationExceptionManager 被注册的错误管理器
     */
    default void registerFunctionProcessor(ModuleApplicationExceptionManager moduleApplicationExceptionManager) {
    }

}
