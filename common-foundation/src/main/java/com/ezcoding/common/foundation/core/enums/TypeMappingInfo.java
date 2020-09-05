package com.ezcoding.common.foundation.core.enums;

import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-09-05 23:12
 */
public class TypeMappingInfo {

    private final TypeMappingPair typeMappingPair;
    private final Map<?, ? extends Enum<?>> mapping;

    public TypeMappingInfo(TypeMappingPair typeMappingPair, Map<?, ? extends Enum<?>> mapping) {
        if (typeMappingPair == null) {
            throw new IllegalArgumentException("typeMappingPair can not be null");
        }
        if (mapping == null) {
            throw new IllegalArgumentException("mapping can not be null");
        }
        this.typeMappingPair = typeMappingPair;
        this.mapping = mapping;
    }

    public TypeMappingPair getTypeMappingPair() {
        return typeMappingPair;
    }

    public Map<?, ? extends Enum<?>> getMapping() {
        return mapping;
    }

}
