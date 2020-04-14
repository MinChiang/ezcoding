package com.ezcoding.extend.user;

import com.ezcoding.common.core.user.IUserIdentifiable;
import com.ezcoding.common.core.user.model.IUser;
import com.ezcoding.common.web.user.IUserProxyable;
import com.ezcoding.module.user.bean.model.User;
import com.ezcoding.module.user.dao.UserMapper;

import java.util.Optional;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-10 9:58
 */
public class LocalUserProxy implements IUserProxyable {

    private UserMapper userMapper;

    public LocalUserProxy(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public IUser load(IUserIdentifiable target) {
        User account = User
                .create()
                .code(target.getCode())
                .phone(target.getPhone())
                .email(target.getEmail())
                .account(target.getAccount());
        return Optional.ofNullable(userMapper.selectAuthenticationDetailByCondition(account)).orElse(new User());
    }

}
