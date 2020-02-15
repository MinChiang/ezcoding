package com.ezcoding.common.foundation.core.exception;

import com.ezcoding.common.foundation.core.application.ApplicationLayerModule;
import com.ezcoding.common.foundation.core.application.ModuleLayerModule;

import static com.ezcoding.common.foundation.core.application.IModuleNameable.FILL_CHAR;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-28 14:06
 */
public class ModuleConstants {

    /**
     * 默认的全局应用名称
     */
    public static final String DEFAULT_APPLICATION_NAME = "application";

    /**
     * 默认的全局模块名称
     */
    public static final String DEFAULT_MODULE_NAME = "module";

    /**
     * 默认的全局业业务名称
     */
    public static final String DEFAULT_FUNCTION_NAME = "common";

    public static final ApplicationLayerModule DEFAULT_APPLICATION_LAYER_MODULE = new ApplicationLayerModule(DEFAULT_APPLICATION_NAME, String.valueOf(FILL_CHAR));
    public static final ModuleLayerModule DEFAULT_MODULE_LAYER_MODULE = new ModuleLayerModule(DEFAULT_APPLICATION_LAYER_MODULE, DEFAULT_MODULE_NAME, String.valueOf(FILL_CHAR));

}
