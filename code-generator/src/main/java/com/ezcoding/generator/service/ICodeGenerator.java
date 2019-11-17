package com.ezcoding.generator.service;

/**
 * Description:
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-11-21 20:44
 */
public interface ICodeGenerator<T> {

    /**
     * 生成代码
     *
     * @return 生成是否成功
     */
    T generate();

}
