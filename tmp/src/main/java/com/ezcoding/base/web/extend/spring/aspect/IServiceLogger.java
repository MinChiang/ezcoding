package com.ezcoding.base.web.extend.spring.aspect;

import com.ezcoding.base.web.extend.spring.aspect.impl.ParamLogInfo;
import com.ezcoding.base.web.extend.spring.aspect.impl.ServiceLogInfo;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-03 20:48
 */
public interface IServiceLogger {

    /**
     * 进行日志打印处理
     *
     * @param target         目标实体
     * @param serviceLogInfo 方法信息
     * @param paramLogInfos  方法的参数
     */
    void logBefore(Object target, ServiceLogInfo serviceLogInfo, List<ParamLogInfo> paramLogInfos);

    /**
     * 进行日志打印处理
     *
     * @param target         目标实体
     * @param serviceLogInfo 方法信息
     * @param paramLogInfos  方法的参数
     * @param resultLogInfo  返回值信息
     */
    void logAfter(Object target, ServiceLogInfo serviceLogInfo, List<ParamLogInfo> paramLogInfos, ParamLogInfo resultLogInfo);

}
