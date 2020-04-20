package com.ezcoding.common.security.metadatasource;

import com.ezcoding.common.security.annotation.DynamicSecured;
import com.ezcoding.common.security.configattribute.DynamicConfigAttribute;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.method.AbstractFallbackMethodSecurityMetadataSource;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-17 17:51
 */
public class DynamicAnnotationSecurityMetadataSource extends AbstractFallbackMethodSecurityMetadataSource {

    /**
     * 所属微服务名称
     */
    private String applicationName;

    public DynamicAnnotationSecurityMetadataSource(String applicationName) {
        this.applicationName = applicationName;
    }

    @Override
    protected Collection<ConfigAttribute> findAttributes(Method method, Class<?> targetClass) {
        DynamicSecured classAnnotation = AnnotationUtils.findAnnotation(targetClass, DynamicSecured.class);
        DynamicSecured methodAnnotation = AnnotationUtils.findAnnotation(method, DynamicSecured.class);

        if (classAnnotation == null && methodAnnotation == null) {
            return null;
        }

        String classAlias = classAnnotation == null || classAnnotation.alias().isEmpty() ? targetClass.getSimpleName() : classAnnotation.alias();
        String methodAlias = methodAnnotation == null || methodAnnotation.alias().isEmpty() ? method.getName() : methodAnnotation.alias();

        DynamicConfigAttribute dynamicConfigAttribute = new DynamicConfigAttribute(
                this.applicationName,
                classAlias,
                methodAlias
        );

        return Collections.singleton(dynamicConfigAttribute);
    }

    @Override
    protected Collection<ConfigAttribute> findAttributes(Class<?> clazz) {
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    public String getApplicationName() {
        return applicationName;
    }

}
