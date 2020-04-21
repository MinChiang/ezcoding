package com.ezcoding.common.security.configattribute;

import org.springframework.security.access.ConfigAttribute;

import java.util.Objects;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-17 21:22
 */
public class DynamicConfigAttribute implements ConfigAttribute {

    public static final String SPLIT = ".";
    public static final String PREFIX = "DYNAMIC_ROLE_";

    /**
     * 模块号
     */
    private String applicationName;

    /**
     * 代理类
     */
    private String className;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 全称
     */
    private String wholeName;

    public DynamicConfigAttribute(String applicationName, String className, String methodName) {
        this.applicationName = applicationName;
        this.className = className;
        this.methodName = methodName;
        this.wholeName = (PREFIX + applicationName + SPLIT + className + SPLIT + methodName).toUpperCase();
    }

    @Override
    public String getAttribute() {
        return this.getWholeName();
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getWholeName() {
        return wholeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DynamicConfigAttribute that = (DynamicConfigAttribute) o;
        return Objects.equals(wholeName, that.wholeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wholeName);
    }

}
