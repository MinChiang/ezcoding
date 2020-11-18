package com.ezcoding.common.web.aspect;

import com.ezcoding.common.web.aspect.impl.ParamLogInfo;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-16 16:53
 */
public interface LogParser {

    /**
     * 转换参数
     *
     * @param paramLogInfo 方法的参数
     * @return 解析转换后的参数
     */
    Object parse(ParamLogInfo paramLogInfo);

}
