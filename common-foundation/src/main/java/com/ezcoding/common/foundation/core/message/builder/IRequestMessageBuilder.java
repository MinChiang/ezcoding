package com.ezcoding.common.foundation.core.message.builder;

import com.ezcoding.common.foundation.core.message.RequestMessage;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-12-14 17:39
 */
public interface IRequestMessageBuilder<T extends IRequestMessageBuilder> {

    /**
     * 构建分页单页页数
     *
     * @param pageSize 分页页数
     * @return 请求构造器
     */
    T pageSize(int pageSize);

    /**
     * 构建分页当前页码
     *
     * @param currentPage 当前页码
     * @return 请求构造器
     */
    T currentPage(int currentPage);

    /**
     * 构造请求报文
     *
     * @return 构造后的请求报文
     */
    RequestMessage build();
}
