package com.ezcoding.base.web.extend.spring.security.authentication;

import com.ezcoding.sdk.account.user.api.IUser;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-30 22:48
 */
public interface IUserLoadable {

    /**
     * 加载用户
     *
     * @param target 被代理的对象（含有部分对象信息）
     * @return 加载的用户
     */
    IUser load(IUser target);

}
