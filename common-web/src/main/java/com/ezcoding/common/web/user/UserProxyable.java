package com.ezcoding.common.web.user;

import com.ezcoding.common.core.user.UserBasicIdentifiable;
import com.ezcoding.common.core.user.model.UserDetailInformationAvailable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-18 14:42
 */
public interface UserProxyable {

    /**
     * 加载用户
     *
     * @param target 被代理的对象（含有部分对象信息）
     * @return 加载的用户
     */
    UserDetailInformationAvailable load(UserBasicIdentifiable target);

}
