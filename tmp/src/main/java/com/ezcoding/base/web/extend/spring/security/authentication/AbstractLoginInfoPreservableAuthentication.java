package com.ezcoding.base.web.extend.spring.security.authentication;

import com.ezcoding.common.foundation.core.constant.GlobalConstants;
import com.ezcoding.sdk.account.user.bean.model.DeviceTypeEnum;
import com.ezcoding.sdk.account.user.bean.model.LoginRegisterTypeEnum;
import com.google.common.collect.Maps;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

/**
 * 平台统一认认证凭证
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-09-29 9:29
 */
public abstract class AbstractLoginInfoPreservableAuthentication extends AbstractAuthenticationToken {

    /**
     * 登录类型
     */
    private LoginRegisterTypeEnum loginType;

    /**
     * 登陆设备类型
     */
    private DeviceTypeEnum deviceType;

    public AbstractLoginInfoPreservableAuthentication(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    public AbstractLoginInfoPreservableAuthentication(Collection<? extends GrantedAuthority> authorities, boolean authenticated) {
        this(authorities);
        setAuthenticated(authenticated);
    }

    /**
     * 在detail中设置额外参数
     *
     * @param key   需要设置的key
     * @param value 需要设置的value
     */
    public void addAdditionalInformation(String key, Object value) {
        Object details = this.getDetails();
        if (details == null) {
            details = Maps.newHashMap();
            this.setDetails(details);
        }
        if (details instanceof Map) {
            ((Map<String, Object>) details).put(key, value);
        }
    }

    public LoginRegisterTypeEnum getType() {
        return loginType;
    }

    public void setType(LoginRegisterTypeEnum loginType) {
        this.loginType = loginType;
    }

    public LoginRegisterTypeEnum getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginRegisterTypeEnum loginType) {
        this.loginType = loginType;
        this.addAdditionalInformation(GlobalConstants.Context.LOGIN_TYPE, loginType);
    }

    public DeviceTypeEnum getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceTypeEnum deviceType) {
        this.deviceType = deviceType;
        this.addAdditionalInformation(GlobalConstants.Context.DEVICE_TYPE, deviceType);
    }

}
