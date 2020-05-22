package com.ezcoding.common.web.starter;

import com.ezcoding.common.core.user.UserLoadable;
import com.ezcoding.common.web.filter.ApplicationContextValueFetchable;
import com.ezcoding.common.web.resolver.parameter.RequestMessageParameterResolvable;
import com.ezcoding.common.web.resolver.result.ResponseMessageReturnValueResolvable;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-23 11:02
 */
public interface ApplicationWebConfigurer {

    /**
     * 注册额外的上下文内容
     *
     * @param fetchers 已存在的上下文值获取器列表
     */
    default void registerApplicationContextFetchers(List<ApplicationContextValueFetchable> fetchers) {

    }

    /**
     * 注册额外的用户加载器（不使用默认的加载器）
     *
     * @param loaders 用户加载器
     */
    default void configUserLoaders(List<UserLoadable> loaders) {

    }

    /**
     * 注册额外的用户加载器（使用默认的加载器）
     *
     * @param loaders 用户加载器
     */
    default void registerUserLoaders(List<UserLoadable> loaders) {

    }

    /**
     * 注册额外的请求报文参数解析器
     *
     * @param resolver 解析器列表
     */
    default void registerRequestMessageParameterResolvers(List<RequestMessageParameterResolvable> resolver) {

    }

    /**
     * 注册额外的请求报文返回解析器
     *
     * @param resolver 解析器列表
     */
    default void registerResponseMessageReturnValueResolvers(List<ResponseMessageReturnValueResolvable> resolver) {

    }

}
