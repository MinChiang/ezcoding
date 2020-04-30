package com.ezcoding.module.permission.bean.vo;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-29 11:25
 */
public class DynamicConfigAttributeVO {

    private String attribute;

    private String description;

    public DynamicConfigAttributeVO() {
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
