package com.ezcoding.common.web.aspect.impl;

import com.ezcoding.common.web.aspect.AbstractSpelLogger;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-03 20:50
 */
public class SystemOutputLogger extends AbstractSpelLogger {

    @Override
    protected void doLog(Object target, ServiceLogInfo serviceLogInfo, List<ParamLogInfo> paramLogInfos, Object message) {
        System.out.println(message);
    }

}
