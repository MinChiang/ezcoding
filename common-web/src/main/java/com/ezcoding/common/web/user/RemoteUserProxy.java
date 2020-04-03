package com.ezcoding.common.web.user;

import com.ezcoding.common.core.user.model.IUser;
import com.ezcoding.common.core.user.model.IUserIdentifiable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-03 11:03
 */
public class RemoteUserProxy implements IUserProxyable {

    @Override
    public IUser load(IUserIdentifiable target) {
        return null;
    }

}
