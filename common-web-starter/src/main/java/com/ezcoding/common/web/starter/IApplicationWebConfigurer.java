package com.ezcoding.common.web.starter;

import com.ezcoding.common.web.filter.IApplicationContextValueFetchable;
import com.ezcoding.common.web.resolver.parameter.IRequestMessageParameterResolvable;
import com.ezcoding.common.web.resolver.result.IResponseMessageReturnValueResolvable;
import com.ezcoding.common.web.user.IUserLoadable;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-23 11:02
 */
public interface IApplicationWebConfigurer {

    /**
     * 注册额外的上下文内容
     *
     * @param fetchers 已存在的上下文值获取器列表
     */
    default void registerApplicationContextFetchers(List<IApplicationContextValueFetchable> fetchers) {

    }

    /**
     * 注册额外的用户加载器
     *
     * @param loaders 用户加载器
     */
    default void registerUserLoaders(List<IUserLoadable> loaders) {

    }

    /**
     * 注册额外的请求报文参数解析器
     *
     * @param resolver 解析器列表
     */
    default void registerRequestMessageParameterResolvers(List<IRequestMessageParameterResolvable> resolver) {

    }

    /**
     * 注册额外的请求报文返回解析器
     *
     * @param resolver 解析器列表
     */
    default void registerResponseMessageReturnValueResolvers(List<IResponseMessageReturnValueResolvable> resolver) {

    }

}
