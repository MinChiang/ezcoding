package com.ezcoding.parser.service;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-06-16 15:48
 */
public class JavaProperty {

    /**
     * 可见性
     */
    private VisualEnum visualType;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 属性名称
     */
    private String propertyName;

    /**
     * 注释
     */
    private String comment;

    public VisualEnum getVisualType() {
        return visualType;
    }

    public void setVisualType(VisualEnum visualType) {
        this.visualType = visualType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
}
