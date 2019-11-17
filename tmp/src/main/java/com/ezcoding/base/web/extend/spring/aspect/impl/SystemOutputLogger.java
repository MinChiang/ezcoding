package com.ezcoding.base.web.extend.spring.aspect.impl;

import com.ezcoding.base.web.extend.spring.aspect.AbstractSpelLogger;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-03 20:50
 */
public class SystemOutputLogger extends AbstractSpelLogger {

    @Override
    protected void doLog(Object target, ServiceLogInfo serviceLogInfo, List<ParamLogInfo> paramLogInfos, ParamLogInfo resultLogInfo, Object message) {
        System.out.println(message);
    }
}
