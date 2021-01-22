package com.ezcoding.common.web.user;

import com.ezcoding.common.core.user.UserBasicIdentifiable;
import com.ezcoding.common.core.user.UserDetailInformationAvailable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-03 11:03
 */
public class RemoteUserProxy implements UserProxyable {

    @Override
    public UserDetailInformationAvailable load(UserBasicIdentifiable target) {
        return null;
    }

}
