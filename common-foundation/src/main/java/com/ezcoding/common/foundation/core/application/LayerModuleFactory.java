package com.ezcoding.common.foundation.core.application;

import static com.ezcoding.common.foundation.core.application.ModuleNameable.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-04-29 14:32
 */
public class LayerModuleFactory {

    /**
     * 创建应用级别标志
     *
     * @param applicationName 应用名称
     * @param applicationCode 应用号
     * @return 应用级别模块
     */
    public static ApplicationLayerModule applicationLayerModule(String applicationName, String applicationCode) {
        if (applicationName == null || applicationName.isEmpty() || applicationCode == null || applicationCode.isEmpty()) {
            throw new IllegalArgumentException("application name and application code can not be empty");
        }
        if (applicationCode.length() > APPLICATION_CODE_LENGTH) {
            throw new IllegalArgumentException("application code length must less than [" + APPLICATION_CODE_LENGTH + "]");
        }
        return new ApplicationLayerModule(applicationName, ModuleNameable.leftPad(applicationCode, APPLICATION_CODE_LENGTH, FILL_CHAR));
    }

    /**
     * 创建应用级别标志
     *
     * @param applicationName 应用名称
     * @param applicationCode 应用号
     * @return 应用级别模块
     */
    public static ApplicationLayerModule applicationLayerModule(String applicationName, int applicationCode) {
        return applicationLayerModule(applicationName, String.valueOf(applicationCode));
    }

    /**
     * 创建应用级别标志
     *
     * @param moduleName 业务名称
     * @param moduleCode 业务号
     * @return 业务级别模块
     */
    public static ModuleLayerModule moduleLayerModule(ApplicationLayerModule applicationLayerModule, String moduleName, String moduleCode) {
        if (moduleName == null || moduleName.isEmpty() || moduleCode == null || moduleCode.isEmpty()) {
            throw new IllegalArgumentException("module name, module code can not be empty");
        }
        if (moduleCode.length() > MODULE_CODE_LENGTH) {
            throw new IllegalArgumentException("module code length must less than [" + MODULE_CODE_LENGTH + "]");
        }
        return new ModuleLayerModule(applicationLayerModule, moduleName, ModuleNameable.leftPad(moduleCode, MODULE_CODE_LENGTH, FILL_CHAR));
    }

    /**
     * 创建应用级别标志
     *
     * @param moduleName 业务名称
     * @param moduleCode 业务号
     * @return 业务级别模块
     */
    public static ModuleLayerModule moduleLayerModule(ApplicationLayerModule applicationLayerModule, String moduleName, int moduleCode) {
        return moduleLayerModule(applicationLayerModule, moduleName, String.valueOf(moduleCode));
    }

    /**
     * 创建应用级别标志
     *
     * @param functionName 功能名称
     * @param functionCode 功能号
     * @return 功能级别模块
     */
    public static FunctionLayerModule functionLayerModule(ModuleLayerModule moduleLayerModule, String functionName, String functionCode) {
        if (functionName == null || functionName.isEmpty() || functionCode == null || functionCode.isEmpty()) {
            throw new IllegalArgumentException("function name, function code can not be empty");
        }
        if (functionCode.length() > FUNCTION_CODE_LENGTH) {
            throw new IllegalArgumentException("function code length must less than [" + FUNCTION_CODE_LENGTH + "]");
        }
        return new FunctionLayerModule(moduleLayerModule, functionName, ModuleNameable.leftPad(functionCode, FUNCTION_CODE_LENGTH, FILL_CHAR));
    }

    /**
     * 创建应用级别标志
     *
     * @param functionName 功能名称
     * @param functionCode 功能号
     * @return 功能级别模块
     */
    public static FunctionLayerModule functionLayerModule(ModuleLayerModule moduleLayerModule, String functionName, int functionCode) {
        return functionLayerModule(moduleLayerModule, functionName, String.valueOf(functionCode));
    }

}
