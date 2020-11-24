package com.ezcoding.common.foundation.core.log;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-16 16:53
 */
public interface LogParser {

    /**
     * 转换参数
     *
     * @param paramLogger 方法的参数
     * @param target      目标对象
     * @return 解析转换后的参数
     */
    List<Object> parse(ParamLogger paramLogger, Object target);

}
