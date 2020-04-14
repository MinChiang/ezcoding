package com.ezcoding.module.user.core.authentication;

import com.ezcoding.common.core.user.model.IUser;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-14 17:42
 */
public interface IBasicUserService {

    /**
     * 判断用户是否存在
     *
     * @param user 用户信息
     * @return 用户是否存在
     */
    boolean exist(IUser user);

    /**
     * 持久化用户信息
     *
     * @param user 需要持久化的用户
     */
    void persist(IUser user);

}
