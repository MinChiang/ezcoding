package com.ezcoding.base.web.extend.spring.aspect.impl;

import com.ezcoding.base.web.extend.spring.aspect.AbstractSpelLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-03 20:49
 */
public class Slf4jLogger extends AbstractSpelLogger {

    @Override
    protected void doLog(Object target, ServiceLogInfo serviceLogInfo, List<ParamLogInfo> paramLogInfos, ParamLogInfo resultLogInfo, Object message) {
        final Logger logger = LoggerFactory.getLogger(target.getClass());
        if (logger.isInfoEnabled()) {
            logger.info((String) message);
        }
    }
}
