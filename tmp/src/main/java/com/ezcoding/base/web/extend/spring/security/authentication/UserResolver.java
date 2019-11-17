package com.ezcoding.base.web.extend.spring.security.authentication;

import com.ezcoding.sdk.account.user.api.IUser;
import com.ezcoding.sdk.account.user.bean.dto.UserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-12-11 10:34
 */
public class UserResolver {

    private UserResolver() {
    }

    public static UserResolver getInstance() {
        return UserResolverUtilsHolder.INSTANCE;
    }

    /**
     * 根据上下文获取当前的用户，使用代理对象
     *
     * @return 当前用户
     */
    public IUser currentUserWithProxy(IUser target) {
        return new UserProxy(target == null ? createEmptyUser() : target);
    }

    /**
     * 根据上下文获取当前的用户，不使用代理对象
     *
     * @return 当前用户
     */
    public IUser currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDTO userDTO = new UserDTO();
        userDTO.setCode(authentication.getName());
        userDTO.setAuthorities(authentication.getAuthorities());
        return userDTO;
    }

    /**
     * 获取空用户
     *
     * @return 空用户
     */
    private IUser createEmptyUser() {
        return new UserDTO();
    }

    private static final class UserResolverUtilsHolder {
        private static final UserResolver INSTANCE = new UserResolver();
    }

}
