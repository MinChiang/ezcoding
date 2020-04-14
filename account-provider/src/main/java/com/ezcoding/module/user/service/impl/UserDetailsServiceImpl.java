package com.ezcoding.module.user.service.impl;

import com.ezcoding.common.core.user.IUserIdentifiable;
import com.ezcoding.common.core.user.model.IUser;
import com.ezcoding.common.core.user.model.UserIdentification;
import com.ezcoding.common.foundation.core.exception.processor.WebExceptionBuilderFactory;
import com.ezcoding.common.foundation.util.AssertUtils;
import com.ezcoding.module.management.service.RoleService;
import com.ezcoding.module.user.bean.model.User;
import com.ezcoding.module.user.core.authentication.IBasicUserService;
import com.ezcoding.module.user.core.authentication.ICustomUserDetailsService;
import com.ezcoding.module.user.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.ezcoding.module.user.exception.AccountUserExceptionConstants.GEN_USER_NOT_EXIST_ERROR;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-27 22:52
 */
@Service
public class UserDetailsServiceImpl implements ICustomUserDetailsService, IBasicUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleService roleService;

    @Override
    public User loadUserByExample(IUserIdentifiable identification) {
        User fullUser = userMapper.selectAuthenticationDetailByCondition(identification);
        AssertUtils.mustNotNull(fullUser, () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_USER_NOT_EXIST_ERROR).build());
        fullUser.setAuthorities(roleService.findAllRoles(fullUser.getCode()));
        return fullUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserIdentification userIdentification = new UserIdentification();
        userIdentification.setCode(username);
        return this.loadUserByExample(userIdentification);
    }

    @Override
    public boolean exist(IUser user) {
        return userMapper.selectAuthenticationDetailByCondition(user) != null;
    }

    @Override
    public void persist(IUser user) {
        User newUser = User
                .create()
                .account(user.getAccount())
                .phone(user.getPhone())
                .code(user.getCode())
                .email(user.getEmail())
                .name(user.getName())
                .gender(user.getGender())
                .address(user.getAddress())
                .birthday(user.getBirthday())
                .hireDate(user.getHireDate())
                .profilePhoto(user.getProfilePhoto())
                .description(user.getDescription())
                .verified(user.getVerified())
                .province(user.getProvince())
                .city(user.getCity());
        userMapper.insert(newUser);
    }

}
