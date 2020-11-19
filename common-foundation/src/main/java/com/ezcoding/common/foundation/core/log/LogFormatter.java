package com.ezcoding.common.foundation.core.log;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-16 16:07
 */
public interface LogFormatter {

    /**
     * 进行参数输出
     *
     * @param expression    表达式
     * @param paramLogInfos 参数值
     */
    String format(String expression, List<ParamLogInfo> paramLogInfos);

}
