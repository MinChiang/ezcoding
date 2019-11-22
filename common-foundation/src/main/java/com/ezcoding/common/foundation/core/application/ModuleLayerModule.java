package com.ezcoding.common.foundation.core.application;

import org.apache.commons.lang3.StringUtils;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-10 13:52
 */
public class ModuleLayerModule extends ApplicationLayerModule {

    private static int moduleCodeLength = MODULE_CODE_LENGTH;
    protected final String moduleName;
    protected final String moduleCode;

    public ModuleLayerModule(String applicationName, String applicationCode, String moduleName, String moduleCode) {
        super(applicationName, applicationCode);
        this.moduleName = moduleName;
        this.moduleCode = moduleCode;
        this.validate();
    }

    public ModuleLayerModule(ApplicationLayerModule applicationLayerModule, String moduleName, String moduleCode) {
        this(applicationLayerModule.getApplicationName(), applicationLayerModule.getApplicationCode(), moduleName, moduleCode);
    }

    /**
     * 验证输入参数
     */
    private void validate() {
        if (StringUtils.isAnyEmpty(moduleName, moduleCode)) {
            throw new IllegalArgumentException("模块名称，模块码不能为空");
        }
        if (moduleCode.length() != moduleCodeLength) {
            throw new IllegalArgumentException("模块码长度必须为" + MODULE_CODE_LENGTH);
        }
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    @Override
    public String getPath(String pathSplitter) {
        return super.getPath(pathSplitter) + pathSplitter + this.moduleName;
    }

    @Override
    public String getCode() {
        return super.getCode() + this.moduleCode;
    }

    public static int getModuleCodeLength() {
        return moduleCodeLength;
    }

    public static void setModuleCodeLength(int moduleCodeLength) {
        ModuleLayerModule.moduleCodeLength = moduleCodeLength;
    }

}
