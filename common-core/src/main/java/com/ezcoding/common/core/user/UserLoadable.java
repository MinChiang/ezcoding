package com.ezcoding.common.core.user;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-30 22:48
 */
public interface UserLoadable {

    /**
     * 加载用户
     *
     * @return 加载的用户
     */
    UserIdentifiable load();

}
