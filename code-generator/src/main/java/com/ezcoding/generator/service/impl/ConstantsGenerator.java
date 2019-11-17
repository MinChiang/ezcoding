package com.ezcoding.generator.service.impl;

import com.ezcoding.generator.constant.GeneratorConstants;

import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-11-21 23:49
 */
public class ConstantsGenerator extends JavaGenerator {

    public ConstantsGenerator() {
    }

    public ConstantsGenerator(Map<String, Object> context, String basePath, String wholeClassName) {
        super(context, basePath, wholeClassName + GeneratorConstants.CONSTANTS_SUFFIX);
    }

    public ConstantsGenerator(Map<String, Object> context, String basePath, String wholeClassName, String author) {
        super(context, basePath, wholeClassName + GeneratorConstants.CONSTANTS_SUFFIX, author);
    }
}
