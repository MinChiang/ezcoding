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

    protected static int moduleCodeLength = MODULE_CODE_LENGTH;
    protected static char moduleFillChar = FILL_CHAR;

    protected final String moduleName;
    protected final String moduleCode;

    public ModuleLayerModule(String applicationName, String applicationCode, String moduleName, String moduleCode) {
        super(applicationName, applicationCode);
        if (moduleName == null || moduleName.isEmpty() || moduleCode == null || moduleCode.isEmpty()) {
            throw new IllegalArgumentException("模块名称，模块码不能为空");
        }
        if (moduleCode.length() > moduleCodeLength) {
            throw new IllegalArgumentException("模块码长度必须小于等于" + moduleCodeLength);
        }
        this.moduleName = moduleName;
        this.moduleCode = ModuleNameable.leftPad(moduleCode, moduleCodeLength, moduleFillChar);
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

    public static int getModuleCodeLength() {
        return moduleCodeLength;
    }

    public static void setModuleCodeLength(int moduleCodeLength) {
        ModuleLayerModule.moduleCodeLength = moduleCodeLength;
    }

    public static char getModuleFillChar() {
        return moduleFillChar;
    }

    public static void setModuleFillChar(char moduleFillChar) {
        ModuleLayerModule.moduleFillChar = moduleFillChar;
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
