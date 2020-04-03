package com.ezcoding.common.web.user;

import com.ezcoding.common.core.user.model.IUserIdentifiable;

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
    IUserIdentifiable load();

}
