package com.ezcoding.common.web.aspect;

import com.ezcoding.common.web.aspect.impl.ParamLogInfo;
import com.ezcoding.common.web.aspect.impl.ServiceLogInfo;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-03 20:48
 */
public interface ServiceLogger {

    /**
     * 进行日志打印处理
     *
     * @param message        打印内容
     * @param serviceLogInfo 方法信息
     * @param paramLogInfos  方法的参数
     */
    void log(String message, ServiceLogInfo serviceLogInfo, List<ParamLogInfo> paramLogInfos);

}
