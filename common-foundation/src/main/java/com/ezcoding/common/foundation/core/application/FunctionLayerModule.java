package com.ezcoding.common.foundation.core.application;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 功能级别模块
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-10 13:54
 */
public class FunctionLayerModule extends ModuleLayerModule {

    protected static int functionCodeLength = FUNCTION_CODE_LENGTH;
    protected static char functionFillChar = FILL_CHAR;

    protected final String functionName;
    protected final String functionCode;

    public FunctionLayerModule(String applicationName, String applicationCode, String moduleName, String moduleCode, String functionName, String functionCode) {
        super(applicationName, applicationCode, moduleName, moduleCode);
        if (StringUtils.isAnyEmpty(functionName, functionCode)) {
            throw new IllegalArgumentException("功能名称，功能码不能为空");
        }
        if (functionCode.length() > functionCodeLength) {
            throw new IllegalArgumentException("模块码长度必须小于等于" + functionCodeLength);
        }
        this.functionName = functionName;
        this.functionCode = StringUtils.leftPad(functionCode, functionCodeLength, functionFillChar);
    }

    public FunctionLayerModule(ModuleLayerModule moduleLayerModule, String functionName, String functionCode) {
        this(moduleLayerModule.getApplicationName(), moduleLayerModule.getApplicationCode(), moduleLayerModule.getModuleName(), moduleLayerModule.getModuleCode(), functionName, functionCode);
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public String getFunctionName() {
        return functionName;
    }

    @Override
    public String getPath(String pathSplitter) {
        return super.getPath(pathSplitter) + pathSplitter + this.functionName;
    }

    @Override
    public String getCode() {
        return super.getCode() + this.functionCode;
    }

    @Override
    public String getName() {
        return super.getName() + DOT_SPLITTER + functionName;
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
        FunctionLayerModule that = (FunctionLayerModule) o;
        return functionCode.equals(that.functionCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), functionCode);
    }

    public static int getFunctionCodeLength() {
        return functionCodeLength;
    }

    public static void setFunctionCodeLength(int functionCodeLength) {
        FunctionLayerModule.functionCodeLength = functionCodeLength;
    }

    public static char getFunctionFillChar() {
        return functionFillChar;
    }

    public static void setFunctionFillChar(char functionFillChar) {
        FunctionLayerModule.functionFillChar = functionFillChar;
    }

}
