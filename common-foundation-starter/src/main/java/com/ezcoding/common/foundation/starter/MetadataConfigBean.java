package com.ezcoding.common.foundation.starter;

import com.ezcoding.common.foundation.core.application.ApplicationLayerModule;
import com.ezcoding.common.foundation.core.application.FunctionLayerModule;
import com.ezcoding.common.foundation.core.application.ModuleLayerModule;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-11 11:41
 */
public class MetadataConfigBean {

    private Long dataCenterNo = 0L;
    private Long categoryNo = 0L;

    private Integer applicationCodeLength = ApplicationLayerModule.getApplicationCodeLength();
    private Character applicationFillChar = ApplicationLayerModule.getApplicationFillChar();
    private Integer moduleCodeLength = ModuleLayerModule.getModuleCodeLength();
    private Character moduleFillChar = ModuleLayerModule.getModuleFillChar();
    private Integer functionCodeLength = FunctionLayerModule.getFunctionCodeLength();
    private Character functionFillChar = FunctionLayerModule.getFunctionFillChar();

    public Long getDataCenterNo() {
        return dataCenterNo;
    }

    public void setDataCenterNo(Long dataCenterNo) {
        this.dataCenterNo = dataCenterNo;
    }

    public Long getCategoryNo() {
        return categoryNo;
    }

    public void setCategoryNo(Long categoryNo) {
        this.categoryNo = categoryNo;
    }

    public Integer getApplicationCodeLength() {
        return applicationCodeLength;
    }

    public void setApplicationCodeLength(Integer applicationCodeLength) {
        this.applicationCodeLength = applicationCodeLength;
    }

    public Character getApplicationFillChar() {
        return applicationFillChar;
    }

    public void setApplicationFillChar(Character applicationFillChar) {
        this.applicationFillChar = applicationFillChar;
    }

    public Integer getModuleCodeLength() {
        return moduleCodeLength;
    }

    public void setModuleCodeLength(Integer moduleCodeLength) {
        this.moduleCodeLength = moduleCodeLength;
    }

    public Character getModuleFillChar() {
        return moduleFillChar;
    }

    public void setModuleFillChar(Character moduleFillChar) {
        this.moduleFillChar = moduleFillChar;
    }

    public Integer getFunctionCodeLength() {
        return functionCodeLength;
    }

    public void setFunctionCodeLength(Integer functionCodeLength) {
        this.functionCodeLength = functionCodeLength;
    }

    public Character getFunctionFillChar() {
        return functionFillChar;
    }

    public void setFunctionFillChar(Character functionFillChar) {
        this.functionFillChar = functionFillChar;
    }

}
