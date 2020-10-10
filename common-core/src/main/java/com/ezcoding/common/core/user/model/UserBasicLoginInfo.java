package com.ezcoding.common.core.user.model;

import com.ezcoding.common.core.user.UserBasicIdentifiable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-10-11 0:51
 */
public class UserBasicLoginInfo implements UserBasicIdentifiable {

    private Long id;

    private LoginRegisterTypeEnum loginTye;

    private DeviceTypeEnum deviceType;

    public UserBasicLoginInfo(Long id, LoginRegisterTypeEnum loginTye, DeviceTypeEnum deviceType) {
        this.id = id;
        this.loginTye = loginTye;
        this.deviceType = deviceType;
    }

    public UserBasicLoginInfo() {
    }

    @Override
    public LoginRegisterTypeEnum getLoginType() {
        return this.loginTye;
    }

    @Override
    public DeviceTypeEnum getDeviceType() {
        return this.deviceType;
    }

    @Override
    public Long getId() {
        return this.id;
    }

}
