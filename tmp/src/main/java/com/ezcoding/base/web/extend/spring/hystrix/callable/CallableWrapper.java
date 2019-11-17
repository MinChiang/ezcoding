package com.ezcoding.base.web.extend.spring.hystrix.callable;

import java.util.concurrent.Callable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-10-31 22:22
 */
public interface CallableWrapper {

    /**
     * 包装Callable实例
     *
     * @param callable 待包装实例
     * @param <T>      返回类型
     * @return 包装后的实例
     */
    <T> Callable<T> wrap(Callable<T> callable);

}
