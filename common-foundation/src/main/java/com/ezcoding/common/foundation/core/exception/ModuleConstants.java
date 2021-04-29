package com.ezcoding.common.foundation.core.exception;

import com.ezcoding.common.foundation.core.application.ApplicationLayerModule;
import com.ezcoding.common.foundation.core.application.FunctionLayerModule;
import com.ezcoding.common.foundation.core.application.LayerModuleFactory;
import com.ezcoding.common.foundation.core.application.ModuleLayerModule;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-28 14:06
 */
public interface ModuleConstants {

    /**
     * 默认的填充字符
     */
    int BASE_NUMBER = 0;

    /**
     * 默认的全局应用名称
     */
    String BASE_NAME = "common";

    ApplicationLayerModule DEFAULT_APPLICATION_LAYER_MODULE = LayerModuleFactory.applicationLayerModule(BASE_NAME, BASE_NUMBER);
    ModuleLayerModule DEFAULT_MODULE_LAYER_MODULE = LayerModuleFactory.moduleLayerModule(DEFAULT_APPLICATION_LAYER_MODULE, BASE_NAME, BASE_NUMBER);
    FunctionLayerModule DEFAULT_FUNCTION_LAYER_MODULE = LayerModuleFactory.functionLayerModule(DEFAULT_MODULE_LAYER_MODULE, BASE_NAME, BASE_NUMBER);

}
