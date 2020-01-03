package com.ezcoding.starter.foundation.config;

import lombok.Data;

import static com.ezcoding.common.foundation.core.application.IModuleNameable.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-11 11:41
 */
@Data
public class MetadataConfigBean {

    private Integer dataCenterNo;
    private String category;
    private Integer categoryNo;

    private Integer applicationCodeLength = APPLICATION_CODE_LENGTH;
    private Character applicationFillChar = FILL_CHAR;
    private Integer moduleCodeLength = MODULE_CODE_LENGTH;
    private Character moduleFillChar = FILL_CHAR;
    private Integer functionCodeLength = FUNCTION_CODE_LENGTH;
    private Character functionFillChar = FILL_CHAR;

}
