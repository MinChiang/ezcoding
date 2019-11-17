package com.ezcoding.common.foundation.core.message;

import com.ezcoding.common.foundation.util.BeanUtils;

import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-26 23:39
 */
public interface Mappable {

    /**
     * 从本类到map的映射
     *
     * @return map映射
     */
    default Map<String, Object> toMap() {
        return BeanUtils.beanToMap(this, false);
    }

}
