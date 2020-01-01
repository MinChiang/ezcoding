package com.ezcoding.common.foundation.core.exception;

import com.ezcoding.common.foundation.core.application.ModuleLayerModule;
import org.apache.commons.lang3.StringUtils;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-12-28 21:36
 */
public class ModuleExceptionCodeGenerator extends ModuleExceptionIdentification implements IExceptionCodeGeneratable {

    /**
     * 标识号
     */
    private String identification;

    public ModuleExceptionCodeGenerator(ModuleLayerModule moduleLayerModule, String detailCode) {
        super(moduleLayerModule, detailCode);
        this.identification = innerGenerate();
    }

    /**
     * 生成唯一编号
     *
     * @return 生成的唯一编号
     */
    private String innerGenerate() {
        return moduleLayerModule.getCode() + StringUtils.leftPad(detailCode, defaultCodeLength, defaultFillChar);
    }

    @Override
    public String generate() {
        return this.identification;
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
