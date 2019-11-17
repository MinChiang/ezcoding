package com.ezcoding.generator.service.impl;

import com.ezcoding.generator.bean.JavaInfo;
import com.ezcoding.generator.constant.GeneratorConstants;
import com.ezcoding.generator.service.AbstractJavaFreemarkerGenerator;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-11-21 21:38
 */
public class JavaGenerator extends AbstractJavaFreemarkerGenerator {

    protected String author;

    public JavaGenerator() {
    }

    public JavaGenerator(Map<String, Object> context, String basePath, String wholeClassName) {
        super(context, GeneratorConstants.JAVA_FTL_NAME, basePath, wholeClassName);
    }

    public JavaGenerator(Map<String, Object> context, String templateName, String basePath, String wholeClassName, String author) {
        super(context, templateName, basePath, wholeClassName);
        this.author = author;
    }

    public JavaGenerator(Map<String, Object> context, String basePath, String wholeClassName, String author) {
        super(context, GeneratorConstants.JAVA_FTL_NAME, basePath, wholeClassName);
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public void extGenerate(JavaInfo javaInfo) {
        context.put(GeneratorConstants.AUTHOR, this.author == null ? "" : this.author);
        context.put(GeneratorConstants.DATE, GeneratorConstants.DATE_TIME_FORMATTER.format(LocalDateTime.now()));
        context.put(GeneratorConstants.COMPONENT_PATH, javaInfo.getPackageName());
        context.put(GeneratorConstants.COMPONENT_NAME, javaInfo.getJavaName());
    }
}
