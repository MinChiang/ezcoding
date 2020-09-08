package com.ezcoding.common.foundation.core.enums;

import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-09-05 23:12
 */
public class ObjectEnumMappingInfo {

    private final MappingPair mappingPair;
    private final Map<?, ? extends Enum<?>> mapping;

    public ObjectEnumMappingInfo(MappingPair mappingPair, Map<?, ? extends Enum<?>> mapping) {
        if (mappingPair == null) {
            throw new IllegalArgumentException("typeMappingPair can not be null");
        }
        if (mapping == null) {
            throw new IllegalArgumentException("mapping can not be null");
        }
        this.mappingPair = mappingPair;
        this.mapping = mapping;
    }

    public MappingPair getMappingPair() {
        return mappingPair;
    }

    public Map<?, ? extends Enum<?>> getMapping() {
        return mapping;
    }

}
