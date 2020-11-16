package com.ezcoding.common.web.aspect;

import com.ezcoding.common.web.aspect.impl.ParamLogInfo;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-16 16:53
 */
public interface LogParser {

    /**
     * 进行日志打印处理
     *
     * @param paramLogInfo 方法的参数
     */
    void parse(ParamLogInfo paramLogInfo);

}
