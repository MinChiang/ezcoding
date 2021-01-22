package com.ezcoding.common.core.user;

import com.ezcoding.common.core.user.model.UserBasicLoginInfo;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-22 22:46
 */
public class EmptyUserLoader implements UserLoadable {

    private static final UserBasicIdentifiable INSTANCE = new UserBasicLoginInfo();

    @Override
    public UserBasicIdentifiable load() {
        return INSTANCE;
    }

}
