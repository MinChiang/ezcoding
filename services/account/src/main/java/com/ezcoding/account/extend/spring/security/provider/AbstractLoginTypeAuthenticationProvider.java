package com.ezcoding.account.extend.spring.security.provider;

import com.ezcoding.account.module.user.bean.model.User;
import com.ezcoding.account.module.user.core.authentication.ICustomUserDetailsService;
import com.ezcoding.account.module.user.exception.UserExceptionConstants;
import com.ezcoding.base.web.extend.spring.security.authentication.AbstractLoginInfoPreservableAuthentication;
import com.ezcoding.base.web.extend.spring.security.authentication.UserAuthentication;
import com.ezcoding.common.foundation.util.AssertUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-09-29 17:24
 */
public abstract class AbstractLoginTypeAuthenticationProvider<A extends AbstractLoginInfoPreservableAuthentication> implements AuthenticationProvider {

    private ICustomUserDetailsService userDetailsService;

    /**
     * 内部身份认证接口
     *
     * @param loginTypeAuthentication 认证内容
     * @param user                    用户认证信息
     */
    abstract void postCheck(A loginTypeAuthentication, User user);

    /**
     * 找出能够唯一确定用户信息的模板
     *
     * @param authentication 用户凭证
     * @return 唯一确定用户信息的用户模板
     */
    abstract User createUserExample(A authentication);

    /**
     * 校验身份的前置基本认证
     *
     * @param authentication 待校验的验证信息
     */
    abstract void preCheck(A authentication);

    /**
     * 生成用户凭证
     * 此时用户凭证需要包含用户所有的有效身份信息：
     * 登陆方式、用户唯一编码、权限信息
     *
     * @param authentication 用户凭证
     * @param authorities    权限
     * @param user           用户信息
     * @return 新的用户凭证
     */
    protected AbstractLoginInfoPreservableAuthentication createNewAuthentication(A authentication, User user, Collection<? extends GrantedAuthority> authorities) {
        return new UserAuthentication(user.getCode(), authorities, true);
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        A abstractLoginInfoPreservableAuthentication = (A) authentication;

        //前置校验，以免基本信息缺失导致多次加载数据库
        preCheck(abstractLoginInfoPreservableAuthentication);

        //根据能够唯一确定用户的用户信息查询出用户所有的基本信息9
        User user = createUserExample(abstractLoginInfoPreservableAuthentication);
        user = userDetailsService.loadUserByExample(user);
        AssertUtils.mustNotNull(user, UserExceptionConstants.USER_NOT_EXIST_ERROR);

        //校验登陆权限
        postCheck(abstractLoginInfoPreservableAuthentication, user);

        //进行身份额外信息补充、私密信息清除
        AbstractLoginInfoPreservableAuthentication newAuthentication = createNewAuthentication(abstractLoginInfoPreservableAuthentication, user, userDetailsService.loadAuthorities(user));
        newAuthentication.setLoginType(abstractLoginInfoPreservableAuthentication.getLoginType());
        newAuthentication.setDeviceType(abstractLoginInfoPreservableAuthentication.getDeviceType());
        return newAuthentication;
    }

    public ICustomUserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(ICustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

}
