package com.ezcoding.module.user.core.authentication;

import com.ezcoding.module.user.bean.model.User;

import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-04-02 9:32
 */
public interface IRegisterSuccessHandler {

    /**
     * 注册成功触发钩子
     *
     * @param context 参数上下文
     * @param user    注册成功的用户
     */
    void onRegisterSuccess(Map<String, ?> context, User user);

}
