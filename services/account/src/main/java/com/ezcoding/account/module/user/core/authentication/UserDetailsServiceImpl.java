package com.ezcoding.account.module.user.core.authentication;

import com.ezcoding.account.module.management.service.RoleService;
import com.ezcoding.account.module.user.bean.model.User;
import com.ezcoding.account.module.user.dao.UserMapper;
import com.ezcoding.account.module.user.exception.UserExceptionConstants;
import com.ezcoding.account.module.user.core.authentication.ICustomUserDetailsService;
import com.ezcoding.common.foundation.util.AssertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-27 22:52
 */
public class UserDetailsServiceImpl implements ICustomUserDetailsService {

    private UserMapper userMapper;

    private RoleService roleService;

    @Override
    public User loadUserByExample(User user) {
        User fullUser = userMapper.selectAuthenticationDetailByCondition(user);
        AssertUtils.mustNotNull(fullUser, UserExceptionConstants.USER_NOT_EXIST_ERROR);
        fullUser.setAuthorities(this.loadAuthorities(fullUser));
        return fullUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> loadAuthorities(User user) {
        return roleService.findAllRoles(user.getCode());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.loadUserByExample(User.create().code(username));
    }

    public UserMapper getUserMapper() {
        return userMapper;
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public RoleService getRoleService() {
        return roleService;
    }

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

}
