package com.ezcoding.common.web.aspect.impl;

import com.ezcoding.common.web.aspect.ServiceLogger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-03 20:49
 */
public class Slf4jLogger implements ServiceLogger {

    @Override
    public void log(String message, ServiceLogInfo serviceLogInfo, List<ParamLogInfo> paramLogInfos) {
        LoggerFactory.getLogger(serviceLogInfo.getTarget().getClass()).info(message);
    }

}
