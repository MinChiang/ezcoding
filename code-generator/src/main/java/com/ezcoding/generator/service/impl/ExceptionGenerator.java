package com.ezcoding.generator.service.impl;

import com.ezcoding.generator.bean.JavaInfo;
import com.ezcoding.generator.constant.GeneratorConstants;

import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-11-21 23:49
 */
public class ExceptionGenerator extends JavaGenerator {

    private String moduleNum;

    public ExceptionGenerator() {
    }

    public ExceptionGenerator(Map<String, Object> context, String basePath, String className, String author, String moduleNum) {
        super(context, GeneratorConstants.EXCEPTION_FTL_NAME, basePath, className + GeneratorConstants.EXCEPTION_SUFFIX, author);
        this.moduleNum = moduleNum;
    }

    @Override
    public void extGenerate(JavaInfo javaInfo) {
        super.extGenerate(javaInfo);
        context.put(GeneratorConstants.MODULE_NUM, this.moduleNum);
    }

    public String getModuleNum() {
        return moduleNum;
    }

    public void setModuleNum(String moduleNum) {
        this.moduleNum = moduleNum;
    }
}
