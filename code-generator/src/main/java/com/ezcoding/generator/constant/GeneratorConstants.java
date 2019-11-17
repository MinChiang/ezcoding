package com.ezcoding.generator.constant;

import java.time.format.DateTimeFormatter;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-06-16 14:47
 */
public class GeneratorConstants {

    public static final String DEFAULT_CHARSET = "UTF-8";
    public static final String PACKAGE_SPLITTER = ".";
    public static final String JAVA_SUFFIX = ".java";

    public static final String CONSTANTS_SUFFIX = "Constants";
    public static final String EXCEPTION_SUFFIX = "Exception";
    public static final String COMPONENT_PATH = "componentPath";
    public static final String AUTHOR = "author";
    public static final String DATE = "date";

    public static final String MODULE_NUM = "moduleNum";
    public static final String JAVA_FTL_NAME = "java.ftl";
    public static final String EXCEPTION_FTL_NAME = "exception.ftl";

    public static final String COMPONENT_NAME = "componentName";
    public static final String CONSTANTS_NAME = "constant";
    public static final String EXCEPTION_NAME = "exception";

    public static final String DEFAULT_AUTO_BASE_PACKAGE = "com";
    public static final String[] PACKAGE_GROUP = {"bean.dto", "bean.model", CONSTANTS_NAME, "controller", "dao", EXCEPTION_NAME, "service.impl"};
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");

}
