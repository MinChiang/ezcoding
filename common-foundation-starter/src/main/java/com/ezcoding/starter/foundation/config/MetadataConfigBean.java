package com.ezcoding.starter.foundation.config;

import com.ezcoding.common.foundation.core.application.ApplicationLayerModule;
import com.ezcoding.common.foundation.core.application.FunctionLayerModule;
import com.ezcoding.common.foundation.core.application.ModuleLayerModule;
import lombok.Data;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-11 11:41
 */
@Data
public class MetadataConfigBean {

    private Long dataCenterNo;
    private String category;
    private Long categoryNo;

    private Integer applicationCodeLength = ApplicationLayerModule.getApplicationCodeLength();
    private Character applicationFillChar = ApplicationLayerModule.getApplicationFillChar();
    private Integer moduleCodeLength = ModuleLayerModule.getModuleCodeLength();
    private Character moduleFillChar = ModuleLayerModule.getModuleFillChar();
    private Integer functionCodeLength = FunctionLayerModule.getFunctionCodeLength();
    private Character functionFillChar = FunctionLayerModule.getFunctionFillChar();

}
