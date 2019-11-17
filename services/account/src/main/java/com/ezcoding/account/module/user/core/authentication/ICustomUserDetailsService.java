package com.ezcoding.account.module.user.core.authentication;

import com.ezcoding.account.module.user.bean.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-04-02 21:40
 */
public interface ICustomUserDetailsService extends UserDetailsService {

    /**
     * 加载用户所有基本信息
     *
     * @param user 用户样例
     * @return 用户所有基本信息
     */
    User loadUserByExample(User user);

    /**
     * 加载用户权限
     *
     * @param user 用户信息
     * @return 用户所拥有的权限
     */
    Collection<? extends GrantedAuthority> loadAuthorities(User user);

}
