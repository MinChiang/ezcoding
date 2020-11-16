package com.ezcoding.common.web.aspect.impl;

import com.ezcoding.common.web.aspect.ServiceLogger;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-03 20:50
 */
public class SystemOutputLogger implements ServiceLogger {

    @Override
    public void log(String message, ServiceLogInfo serviceLogInfo, List<ParamLogInfo> paramLogInfos) {
        System.out.println(message);
    }

}
