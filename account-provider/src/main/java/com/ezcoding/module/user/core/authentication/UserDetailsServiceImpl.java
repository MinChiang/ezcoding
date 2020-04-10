package com.ezcoding.module.user.core.authentication;

import com.ezcoding.common.foundation.core.exception.processor.WebExceptionBuilderFactory;
import com.ezcoding.common.foundation.util.AssertUtils;
import com.ezcoding.module.management.service.RoleService;
import com.ezcoding.module.user.bean.model.User;
import com.ezcoding.module.user.dao.UserMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;

import static com.ezcoding.module.user.exception.AccountUserExceptionConstants.GEN_USER_NOT_EXIST_ERROR;

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
        AssertUtils.mustNotNull(fullUser, () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_USER_NOT_EXIST_ERROR).build());
        fullUser.setAuthorities(this.loadAuthorities(fullUser));
        return fullUser;
    }

    private Collection<? extends GrantedAuthority> loadAuthorities(User user) {
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
