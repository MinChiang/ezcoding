package com.ezcoding.common.sdk.core;

import com.ezcoding.common.core.user.model.DeviceTypeEnum;
import com.ezcoding.common.core.user.model.LoginRegisterTypeEnum;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-01-26 11:02
 */
public class UserAuthentication {

    /**
     * 用户id
     */
    private final Long id;

    /**
     * 登录类型
     */
    private final LoginRegisterTypeEnum loginType;

    /**
     * 登陆设备类型
     */
    private final DeviceTypeEnum deviceType;

    /**
     * 权限列表
     */
    private final List<String> authorities;

    /**
     * 额外信息
     */
    private final Map<String, Object> details;

    public UserAuthentication(Long id, LoginRegisterTypeEnum loginType, DeviceTypeEnum deviceType, List<String> authorities, Map<String, Object> details) {
        this.id = id;
        this.loginType = loginType;
        this.deviceType = deviceType;
        this.authorities = Collections.unmodifiableList(authorities);
        this.details = details;
    }

    public Long getId() {
        return id;
    }

    public LoginRegisterTypeEnum getLoginType() {
        return loginType;
    }

    public DeviceTypeEnum getDeviceType() {
        return deviceType;
    }

    public Map<String, Object> getDetails() {
        return details;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

}
