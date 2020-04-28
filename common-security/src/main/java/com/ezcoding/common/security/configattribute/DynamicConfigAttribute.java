package com.ezcoding.common.security.configattribute;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.ConfigAttribute;

import java.util.Objects;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-17 21:22
 */
public class DynamicConfigAttribute implements ConfigAttribute {

    public static final String SPLIT = ":";
    public static final String PREFIX = "DYNAMIC_ROLE_";

    /**
     * 模块号
     */
    private transient String applicationName;

    /**
     * 代理类
     */
    private transient String className;

    /**
     * 方法名
     */
    private transient String methodName;

    /**
     * 全称
     */
    private String wholeName;

    /**
     * 描述
     */
    private String description;

    public DynamicConfigAttribute(String applicationName, String className, String methodName) {
        this.applicationName = applicationName;
        this.className = className;
        this.methodName = methodName;
        this.wholeName = (PREFIX + applicationName + SPLIT + className + SPLIT + methodName).toUpperCase();
    }

    public DynamicConfigAttribute(String applicationName, String className, String methodName, String description) {
        this(applicationName, className, methodName);
        this.description = description;
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

    public String getDescription() {
        return description;
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

    public static DynamicConfigAttribute create(String wholeName) {
        String string = StringUtils.substringAfter(wholeName.toUpperCase(), PREFIX);
        if (StringUtils.isBlank(string)) {
            throw new IllegalArgumentException("不正确的表达式，必须为：" + PREFIX + "[applicationName]" + SPLIT + "[className]" + SPLIT + "[methodName]");
        }
        String[] split = StringUtils.split(string, SPLIT);
        if (split == null || split.length != 3) {
            throw new IllegalArgumentException("不正确的表达式，必须为：" + PREFIX + "[applicationName]" + SPLIT + "[className]" + SPLIT + "[methodName]");
        }
        return new DynamicConfigAttribute(split[0], split[1], split[2]);
    }

}
