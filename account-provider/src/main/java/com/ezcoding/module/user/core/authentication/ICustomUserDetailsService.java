package com.ezcoding.module.user.core.authentication;

import com.ezcoding.common.core.user.IUserIdentifiable;
import com.ezcoding.module.user.bean.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-04-02 21:40
 */
public interface ICustomUserDetailsService extends UserDetailsService {

    /**
     * 加载用户所有基本信息
     *
     * @param identification 用户可检索唯一信息
     * @return 用户所有基本信息
     */
    User loadUserByExample(IUserIdentifiable identification);

}
