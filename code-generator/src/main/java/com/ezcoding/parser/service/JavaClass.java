package com.ezcoding.parser.service;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-06-16 15:43
 */
public class JavaClass implements IParsable {

    /**
     * 包名
     */
    private String packageName;

    /**
     * 类名
     */
    private String className;

    /**
     * 可见性
     */
    private VisualEnum visualType;

    /**
     * 注释
     */
    private String comment;

    public JavaClass() {
    }

    public JavaClass(String packageName, String className, VisualEnum visualType, String comment) {
        this.packageName = packageName;
        this.className = className;
        this.visualType = visualType;
        this.comment = comment;
    }

    @Override
    public boolean parse(String content) {
        boolean result = false;



        return result;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public VisualEnum getVisualType() {
        return visualType;
    }

    public void setVisualType(VisualEnum visualType) {
        this.visualType = visualType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
