package com.ezcoding.common.security.metadatasource;

import com.ezcoding.common.security.annotation.DynamicSecured;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.annotation.AnnotationMetadataExtractor;
import org.springframework.security.access.method.AbstractFallbackMethodSecurityMetadataSource;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-17 17:51
 */
public class DynamicAnnotationSecurityMetadataSource extends AbstractFallbackMethodSecurityMetadataSource {

    @Override
    protected Collection<ConfigAttribute> findAttributes(Method method, Class<?> targetClass) {
        return null;
    }

    @Override
    protected Collection<ConfigAttribute> findAttributes(Class<?> clazz) {
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    private static class DynamicAnnotationMetadataExtractor implements AnnotationMetadataExtractor<DynamicSecured> {

        public Collection<ConfigAttribute> extractAttributes(DynamicSecured dynamicSecured) {
            String[] attributeTokens = dynamicSecured.alias();
            List<ConfigAttribute> attributes = new ArrayList<>(attributeTokens.length);

            for (String token : attributeTokens) {
                attributes.add(new SecurityConfig(token));
            }

            return attributes;
        }

    }

}
