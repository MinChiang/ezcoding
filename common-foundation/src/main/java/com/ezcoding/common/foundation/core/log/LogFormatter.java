package com.ezcoding.common.foundation.core.log;

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
     * @param serviceLogger 方法信息
     * @param target        目标对象
     * @param objects       参数值
     */
    String format(String expression, ServiceLogger serviceLogger, Object target, Object[] objects);

}
