package com.ezcoding.common.foundation.core.enums;

import java.util.Objects;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-09-03 11:15
 */
public class MappingPair {

    private final Class<?> sourceClass;
    private final Class<?> targetClass;

    public MappingPair(Class<?> sourceClass, Class<?> targetClass) {
        if (sourceClass == null) {
            throw new IllegalArgumentException("sourceClass can not be null");
        }
        if (targetClass == null) {
            throw new IllegalArgumentException("targetClass can not be null");
        }
        this.sourceClass = sourceClass;
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
        MappingPair that = (MappingPair) o;
        return sourceClass.equals(that.sourceClass) &&
                targetClass.equals(that.targetClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceClass, targetClass);
    }

    public Class<?> getSourceClass() {
        return sourceClass;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

}
