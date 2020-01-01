package com.ezcoding.common.foundation.core.application;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-10 13:52
 */
public class ModuleLayerModule extends ApplicationLayerModule {

    protected final String moduleName;
    protected final String moduleCode;

    public ModuleLayerModule(String applicationName, String applicationCode, String moduleName, String moduleCode) {
        super(applicationName, applicationCode);
        if (StringUtils.isAnyEmpty(moduleName, moduleCode)) {
            throw new IllegalArgumentException("模块名称，模块码不能为空");
        }
        if (moduleCode.length() > MODULE_CODE_LENGTH) {
            throw new IllegalArgumentException("模块码长度必须小于等于" + MODULE_CODE_LENGTH);
        }
        this.moduleName = moduleName;
        this.moduleCode = StringUtils.leftPad(moduleCode, MODULE_CODE_LENGTH, FILL_CHAR);
    }

    public ModuleLayerModule(ApplicationLayerModule applicationLayerModule, String moduleName, String moduleCode) {
        this(applicationLayerModule.getApplicationName(), applicationLayerModule.getApplicationCode(), moduleName, moduleCode);
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

    @Override
    public String getName() {
        return super.getName() + DOT_SPLITTER + moduleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        ModuleLayerModule that = (ModuleLayerModule) o;
        return moduleCode.equals(that.moduleCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), moduleCode);
    }

}
