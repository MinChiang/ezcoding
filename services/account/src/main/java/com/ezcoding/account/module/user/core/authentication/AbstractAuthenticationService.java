package com.ezcoding.account.module.user.core.authentication;

import com.ezcoding.account.module.user.bean.model.User;
import com.ezcoding.account.module.user.dao.UserMapper;
import com.ezcoding.base.web.extend.spring.security.authentication.AbstractLoginInfoPreservableAuthentication;
import com.ezcoding.common.foundation.core.tools.uuid.OriginalUUIDProducer;
import com.ezcoding.sdk.account.user.bean.model.DeviceTypeEnum;
import com.ezcoding.sdk.account.user.bean.model.LoginRegisterTypeEnum;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-04-01 21:10
 */
public abstract class AbstractAuthenticationService implements IAuthenticationService, IRegisterSuccessHandler, ILoginSuccessHandler {

    protected UserMapper userMapper;
    private AuthenticationManager authenticationManager;
    private List<IRegisterSuccessHandler> registerHandlers = Lists.newArrayList();
    private List<ILoginSuccessHandler> loginHandlers = Lists.newArrayList();

    @Override
    public AbstractLoginInfoPreservableAuthentication login(Map<String, ?> context) {
        AbstractLoginInfoPreservableAuthentication authenticationWithBasicInfo = createAuthenticationWithBasicInfo(context);
        AbstractLoginInfoPreservableAuthentication authentication = (AbstractLoginInfoPreservableAuthentication) authenticationManager.authenticate(authenticationWithBasicInfo);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    @Override
    public User register(Map<String, ?> context) {
        //校验并且生成默认用户
        User user = checkAndCreateUser(context);
        //填充用户信息
        fillBlankContent(user);
        //插入用户
        userMapper.insert(user);
        //触发注册成功钩子函数
        onRegisterSuccess(context, user);
        return user;
    }

    /**
     * 填充用户空缺字段
     *
     * @param user 用户
     */
    public void fillBlankContent(User user) {
        user.code(OriginalUUIDProducer.getInstance().produce());
        String defaultName = "用户" + DateFormatUtils.format(new Date(), "yyyyMMddhhmmss");
        if (StringUtils.isBlank(user.getAccount())) {
            user.account(defaultName);
        }
        if (StringUtils.isBlank(user.getName())) {
            user.name(defaultName);
        }
    }

    @Override
    public void onRegisterSuccess(Map<String, ?> context, User user) {
        Optional
                .ofNullable(registerHandlers)
                .ifPresent(rs -> rs.forEach(r -> r.onRegisterSuccess(context, user)));
    }

    @Override
    public void onLoginSuccess(User user) {
        Optional
                .ofNullable(loginHandlers)
                .ifPresent(ls -> ls.forEach(l -> onLoginSuccess(user)));
    }

    /**
     * 注册对应的注册钩子服务
     *
     * @param registerSuccessHandler 注册钩子服务
     */
    public void registerHandler(IRegisterSuccessHandler registerSuccessHandler) {
        Optional
                .ofNullable(registerSuccessHandler)
                .ifPresent(r -> registerHandlers.add(r));
    }

    /**
     * 注册对应的注册钩子服务
     *
     * @param loginSuccessHandler 注册钩子服务
     */
    public void loginHandler(ILoginSuccessHandler loginSuccessHandler) {
        Optional
                .ofNullable(loginSuccessHandler)
                .ifPresent(l -> loginHandlers.add(l));
    }

    /**
     * 创建登陆令牌
     *
     * @param context 上下文
     * @return 登陆令牌
     */
    private AbstractLoginInfoPreservableAuthentication createAuthenticationWithBasicInfo(Map<String, ?> context) {
        AbstractLoginInfoPreservableAuthentication authentication = createAuthentication(context);
        authentication.setDeviceType((DeviceTypeEnum) context.get(IAuthenticationService.DEVICE_TYPE));
        authentication.setLoginType((LoginRegisterTypeEnum) context.get(IAuthenticationService.LOGIN_TYPE_KEY));
        return authentication;
    }

    /**
     * 创建登陆令牌
     *
     * @param context 上下文
     * @return 登陆令牌
     */
    public abstract AbstractLoginInfoPreservableAuthentication createAuthentication(Map<String, ?> context);

    /**
     * 校验并且生成对应的用户
     *
     * @param context 上下文
     * @return 是否通过校验
     */
    public abstract User checkAndCreateUser(Map<String, ?> context);

    public UserMapper getUserMapper() {
        return userMapper;
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public List<IRegisterSuccessHandler> getRegisterHandlers() {
        return registerHandlers;
    }

    public void setRegisterHandlers(List<IRegisterSuccessHandler> registerHandlers) {
        this.registerHandlers = registerHandlers;
    }

    public List<ILoginSuccessHandler> getLoginHandlers() {
        return loginHandlers;
    }

    public void setLoginHandlers(List<ILoginSuccessHandler> loginHandlers) {
        this.loginHandlers = loginHandlers;
    }
}
