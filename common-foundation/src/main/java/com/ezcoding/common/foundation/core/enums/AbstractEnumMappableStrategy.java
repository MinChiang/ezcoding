package com.ezcoding.common.foundation.core.enums;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-09-08 0:24
 */
public abstract class AbstractEnumMappableStrategy implements EnumMappableStrategy {

    @Override
    public Set<TypeMappingInfo> map(Class<? extends Enum<?>> target) {
        TypeMappingInfo typeMappingInfo = doMap(target);
        if (typeMappingInfo == null) {
            return null;
        } else {
            TypeMappingPair typeMappingPair = new TypeMappingPair(target, String.class);
            Map<String, Enum<?>> mapping = new HashMap<>();
            for (Map.Entry<?, ? extends Enum<?>> entry : typeMappingInfo.getMapping().entrySet()) {
                mapping.put(entry.getKey().toString(), entry.getValue());
            }
            TypeMappingInfo ts = new TypeMappingInfo(typeMappingPair, mapping);

            Set<TypeMappingInfo> result = new HashSet<>();
            result.add(typeMappingInfo);
            result.add(ts);
            return result;
        }
    }

    /**
     * 进行普通映射
     *
     * @param target 普通映射enum
     * @return 映射结果
     */
    public abstract TypeMappingInfo doMap(Class<? extends Enum<?>> target);

}
