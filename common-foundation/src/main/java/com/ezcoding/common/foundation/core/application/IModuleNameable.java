package com.ezcoding.common.foundation.core.application;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-10 13:46
 */
public interface IModuleNameable {

    /**
     * 获取模块整体路径
     *
     * @param pathSplitter 路径分隔符
     * @return 模块整体路径
     */
    String getPath(String pathSplitter);

    /**
     * 获取模块唯一标志号码
     *
     * @return 唯一标志号码
     */
    String getCode();

}
