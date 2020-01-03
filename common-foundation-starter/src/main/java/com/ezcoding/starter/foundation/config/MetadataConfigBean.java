package com.ezcoding.starter.foundation.config;

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

    private Integer applicationCodeLength = getApplicationCodeLength();
    private Character applicationFillChar = getApplicationFillChar();
    private Integer moduleCodeLength = getModuleCodeLength();
    private Character moduleFillChar = getModuleFillChar();
    private Integer functionCodeLength = getFunctionCodeLength();
    private Character functionFillChar = getFunctionFillChar();

}
