package com.ezcoding.common.web.user;

import com.ezcoding.common.core.user.model.IUser;
import com.ezcoding.common.core.user.model.IUserIdentifiable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-18 14:42
 */
public interface IUserProxyable {

    /**
     * 加载用户
     *
     * @param target 被代理的对象（含有部分对象信息）
     * @return 加载的用户
     */
    IUser load(IUserIdentifiable target);

}
