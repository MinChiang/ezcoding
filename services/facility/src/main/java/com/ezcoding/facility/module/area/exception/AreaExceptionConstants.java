package com.ezcoding.facility.module.area.exception;

import com.ezcoding.common.foundation.core.exception.ExceptionBuilderFactory;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-01-22 16:45
 */
public class AreaExceptionConstants {

    public static final ExceptionBuilderFactory<AreaException>
            AREA_NOT_CORRECT_LEVEL_ERROR = ExceptionBuilderFactory.create(AreaException.class, "0001", "区域层级不正确"),
            AREA_NOT_DIRECT_AFFILIATION_ERROR = ExceptionBuilderFactory.create(AreaException.class, "0002", "非直属区域错误");

}
