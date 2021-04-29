package com.ezcoding.common.foundation.core.application;

import java.util.Objects;

/**
 * 业务级别模块
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-10 13:52
 */
public class ModuleLayerModule extends ApplicationLayerModule {

    /**
     * 业务名称
     */
    protected final String moduleName;

    /**
     * 业务号
     */
    protected final String moduleCode;

    ModuleLayerModule(String applicationName, String applicationCode, String moduleName, String moduleCode) {
        super(applicationName, applicationCode);
        this.moduleName = moduleName;
        this.moduleCode = moduleCode;
    }

    ModuleLayerModule(ApplicationLayerModule applicationLayerModule, String moduleName, String moduleCode) {
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

    @Override
    public String toString() {
        return "ModuleLayerModule{" +
                "moduleName='" + moduleName + '\'' +
                ", moduleCode='" + moduleCode + '\'' +
                ", applicationName='" + applicationName + '\'' +
                ", applicationCode='" + applicationCode + '\'' +
                '}';
    }

}
