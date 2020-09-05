package com.ezcoding.common.security.metadatasource;

import com.ezcoding.common.security.annotation.DynamicSecured;
import com.ezcoding.common.security.configattribute.DynamicConfigAttribute;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.method.AbstractFallbackMethodSecurityMetadataSource;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-17 17:51
 */
public class DynamicAnnotationSecurityMetadataSource extends AbstractFallbackMethodSecurityMetadataSource {

    /**
     * 所属微服务名称
     */
    private final String applicationName;

    private final Map<Key, Collection<ConfigAttribute>> mapping = new ConcurrentHashMap<>();

    public DynamicAnnotationSecurityMetadataSource(String applicationName) {
        this.applicationName = applicationName;
    }

    @Override
    protected Collection<ConfigAttribute> findAttributes(Method method, Class<?> targetClass) {
        Key key = new Key(method, targetClass);
        Collection<ConfigAttribute> configAttributes = mapping.get(key);
        if (configAttributes != null) {
            return configAttributes;
        }

        DynamicSecured classAnnotation = AnnotationUtils.findAnnotation(targetClass, DynamicSecured.class);
        DynamicSecured methodAnnotation = AnnotationUtils.findAnnotation(method, DynamicSecured.class);

        if (classAnnotation == null && methodAnnotation == null) {
            return null;
        }

        String classAlias = classAnnotation == null || classAnnotation.alias().isEmpty() ? targetClass.getSimpleName() : classAnnotation.alias();
        String methodAlias = methodAnnotation == null || methodAnnotation.alias().isEmpty() ? method.getName() : methodAnnotation.alias();
        String description = methodAnnotation == null ? null : methodAnnotation.description();

        DynamicConfigAttribute dynamicConfigAttribute = new DynamicConfigAttribute(
                this.applicationName,
                classAlias,
                methodAlias,
                description
        );

        Collection<ConfigAttribute> result = Collections.singleton(dynamicConfigAttribute);
        mapping.put(key, result);
        return result;
    }

    @Override
    protected Collection<ConfigAttribute> findAttributes(Class<?> clazz) {
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return this.acquireMetadata();
    }

    public String getApplicationName() {
        return applicationName;
    }

    /**
     * 获取所有的配置信息
     *
     * @return 所有的配置信息
     */
    private Set<ConfigAttribute> acquireMetadata() {
        Set<ConfigAttribute> attributes = new HashSet<>();
        for (Collection<ConfigAttribute> value : mapping.values()) {
            attributes.addAll(value);
        }
        return attributes;
    }

    private static class Key {

        private final Method method;

        private final Class<?> targetClass;

        public Key(Method method, Class<?> targetClass) {
            this.method = method;
            this.targetClass = targetClass;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Key key = (Key) o;
            return Objects.equals(method, key.method) &&
                    Objects.equals(targetClass, key.targetClass);
        }

        @Override
        public int hashCode() {
            return Objects.hash(method, targetClass);
        }

    }

}
