package com.ezcoding.common.foundation.core.exception;

import com.ezcoding.common.foundation.core.application.FunctionLayerModule;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-12-29 11:08
 */
public class ExceptionCodeGeneratorFactory {

    public static ModuleExceptionCodeGenerator moduleExceptionCodeGenerator(FunctionLayerModule functionLayerModule) {
        return new ModuleExceptionCodeGenerator(functionLayerModule);
    }

    public static TemplateExceptionCodeGenerator templateExceptionCodeGenerator(FunctionLayerModule functionLayerModule, String template) {
        return new TemplateExceptionCodeGenerator(functionLayerModule, template);
    }

}
