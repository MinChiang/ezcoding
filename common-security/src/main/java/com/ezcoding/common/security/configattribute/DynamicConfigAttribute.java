package com.ezcoding.common.security.configattribute;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Objects;
import java.util.Set;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-17 21:22
 */
public class DynamicConfigAttribute implements ConfigAttribute {

    private static final long serialVersionUID = 7426954738300828506L;

    public static final String SPLIT = ":";
    public static final String PREFIX = "DYNAMIC_ROLE_";

    /**
     * 模块号
     */
    private final transient String applicationName;

    /**
     * 代理类
     */
    private final transient String className;

    /**
     * 方法名
     */
    private final transient String methodName;

    /**
     * 全称
     */
    private final String attribute;

    /**
     * 描述
     */
    private String description;

    /**
     * 请求路径
     */
    private Set<String> paths;

    /**
     * 请求方法
     */
    private Set<RequestMethod> requestMethods;

    public DynamicConfigAttribute(String applicationName, String className, String methodName) {
        this.applicationName = applicationName;
        this.className = className;
        this.methodName = methodName;
        this.attribute = (PREFIX + applicationName + SPLIT + className + SPLIT + methodName).toUpperCase();
    }

    public DynamicConfigAttribute(String applicationName, String className, String methodName, String description) {
        this(applicationName, className, methodName);
        this.description = description;
    }

    @Override
    public String getAttribute() {
        return this.attribute;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getPaths() {
        return paths;
    }

    public void setPaths(Set<String> paths) {
        this.paths = paths;
    }

    public Set<RequestMethod> getRequestMethods() {
        return requestMethods;
    }

    public void setRequestMethods(Set<RequestMethod> requestMethods) {
        this.requestMethods = requestMethods;
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
        return Objects.equals(attribute, that.attribute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attribute);
    }

    /**
     * 根据全称创建对象
     *
     * @param attribute 全称
     * @return 对象
     */
    public static DynamicConfigAttribute create(String attribute) {
        if (attribute == null || attribute.isEmpty()) {
            throw new IllegalArgumentException("incorrect expression, must be: " + PREFIX + "[applicationName]" + SPLIT + "[className]" + SPLIT + "[methodName]");
        }
        int index = attribute.toUpperCase().indexOf(PREFIX);
        String string = attribute.substring(index);
        if (string.isEmpty()) {
            throw new IllegalArgumentException("incorrect expression, must be: " + PREFIX + "[applicationName]" + SPLIT + "[className]" + SPLIT + "[methodName]");
        }
        String[] split = string.split(SPLIT);
        if (split.length != 3) {
            throw new IllegalArgumentException("incorrect expression, must be: " + PREFIX + "[applicationName]" + SPLIT + "[className]" + SPLIT + "[methodName]");
        }
        return new DynamicConfigAttribute(split[0], split[1], split[2]);
    }

}
