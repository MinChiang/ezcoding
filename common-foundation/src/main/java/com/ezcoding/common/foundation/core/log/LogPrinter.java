package com.ezcoding.common.foundation.core.log;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-03 20:48
 */
public interface LogPrinter {

    /**
     * 进行日志打印处理
     *
     * @param message       打印内容
     * @param serviceLogger 方法信息
     * @param objects       解析对象
     */
    void print(String message, ServiceLogger serviceLogger, List<Object> objects);

}
