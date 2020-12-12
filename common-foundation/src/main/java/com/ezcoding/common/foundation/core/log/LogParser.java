package com.ezcoding.common.foundation.core.log;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-16 16:53
 */
public interface LogParser {

    /**
     * 转换参数
     *
     * @param expression       表达式
     * @param paramLogMetadata 元数据
     * @param target           目标对象
     * @return 解析转换后的参数
     */
    Object parse(String expression, ParamLogMetadata paramLogMetadata, Object target);

}
