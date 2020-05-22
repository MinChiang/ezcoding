package com.ezcoding.common.web.user;

import com.ezcoding.common.core.user.UserIdentifiable;
import com.ezcoding.common.core.user.model.UserDetailInformationIdentifiable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-03 11:03
 */
public class RemoteUserProxy implements UserProxyable {

    @Override
    public UserDetailInformationIdentifiable load(UserIdentifiable target) {
        return null;
    }

}
