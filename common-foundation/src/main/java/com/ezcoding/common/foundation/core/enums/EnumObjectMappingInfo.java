package com.ezcoding.common.foundation.core.enums;

import java.util.Map;
import java.util.Objects;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-09-08 11:34
 */
public class EnumObjectMappingInfo {

    private final MappingPair mappingPair;
    private final Map<? extends Enum<?>, ?> mapping;

    public EnumObjectMappingInfo(MappingPair mappingPair, Map<? extends Enum<?>, ?> mapping) {
        if (mappingPair == null) {
            throw new IllegalArgumentException("mappingPair can not be null");
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

    public Map<? extends Enum<?>, ?> getMapping() {
        return mapping;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EnumObjectMappingInfo that = (EnumObjectMappingInfo) o;
        return Objects.equals(mappingPair, that.mappingPair) &&
                Objects.equals(mapping, that.mapping);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mappingPair, mapping);
    }

}
