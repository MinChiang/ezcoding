package com.ezcoding.common.user.resolve;

import com.ezcoding.common.user.model.IUser;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-30 22:48
 */
public interface IUserLoadable {

    /**
     * 加载用户
     *
     * @return 加载的用户
     */
    IUser load();

}
