package com.ezcoding.common.web.user;

import com.ezcoding.common.core.user.model.IUserIdentifiable;
import com.ezcoding.common.core.user.model.User;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-22 22:46
 */
public class EmptyUserLoader implements IUserLoadable {

    @Override
    public IUserIdentifiable load() {
        return new User();
    }

}
