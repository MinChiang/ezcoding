package com.ezcoding.common.foundation.core.exception;

import com.ezcoding.common.foundation.core.application.FunctionLayerModule;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-12-28 21:36
 */
public class ModuleExceptionCodeGenerator implements IExceptionCodeGeneratable {

    /**
     * 功能模块
     */
    protected FunctionLayerModule functionLayerModule;

    public ModuleExceptionCodeGenerator(FunctionLayerModule functionLayerModule) {
        this.functionLayerModule = functionLayerModule;
    }

    @Override
    public String generate() {
        return functionLayerModule.getCode();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
