package com.ezcoding.extend.user;

import com.ezcoding.common.core.user.IUserIdentifiable;
import com.ezcoding.common.core.user.model.IUser;
import com.ezcoding.common.web.user.IUserProxyable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-10 9:58
 */
public class LocalUserProxy implements IUserProxyable {

    @Override
    public IUser load(IUserIdentifiable target) {
        return null;
    }

}
