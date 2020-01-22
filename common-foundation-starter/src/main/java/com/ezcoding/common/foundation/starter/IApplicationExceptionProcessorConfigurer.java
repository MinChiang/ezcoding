package com.ezcoding.common.foundation.starter;

import com.ezcoding.common.foundation.core.exception.processor.AbstractLayerModuleProcessor;
import com.ezcoding.common.foundation.core.exception.processor.ModuleApplicationExceptionManager;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-20 14:02
 */
public interface IApplicationExceptionProcessorConfigurer {

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

}
