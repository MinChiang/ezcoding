package com.ezcoding.common.foundation.core.application;

import org.apache.commons.lang3.StringUtils;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-10 13:54
 */
public class FunctionLayer extends ModuleLayer {

    private static int functionCodeLength = 4;

    protected final String functionName;
    protected final String functionCode;

    public FunctionLayer(String applicationName, String applicationCode, String moduleName, String moduleCode, String functionName, String functionCode) {
        super(applicationName, moduleName, moduleName, moduleCode);
        this.functionName = functionName;
        this.functionCode = functionCode;
        this.validate();
    }

    public FunctionLayer(ModuleLayer moduleLayer, String functionName, String functionCode) {
        this(moduleLayer.getApplicationName(), moduleLayer.getApplicationCode(), moduleLayer.getModuleName(), moduleLayer.getModuleCode(), functionName, functionCode);
    }

    /**
     * 验证输入参数
     */
    private void validate() {
        if (StringUtils.isAnyEmpty(functionName, functionCode)) {
            throw new IllegalArgumentException("功能名称，功能码不能为空");
        }
        if (functionCode.length() != functionCodeLength) {
            throw new IllegalArgumentException("模块码长度必须为" + functionCodeLength);
        }
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

    public static int getFunctionCodeLength() {
        return functionCodeLength;
    }

    public static void setFunctionCodeLength(int functionCodeLength) {
        FunctionLayer.functionCodeLength = functionCodeLength;
    }

}
