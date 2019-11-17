package com.ezcoding.account.module.user.core.authentication;

import com.ezcoding.account.module.user.bean.model.User;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-04-02 9:32
 */
public interface ILoginSuccessHandler {

    /**
     * 登陆成功触发钩子
     *
     * @param user 登陆成功的用户
     */
    void onLoginSuccess(User user);

}
