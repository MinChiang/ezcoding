package com.ezcoding.common.core.user.model;

import com.ezcoding.common.core.user.UserBasicIdentifiable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-10-11 0:51
 */
public class UserBasicLoginInfo implements UserBasicIdentifiable {

    private final Long id;

    private final LoginRegisterTypeEnum loginTye;

    private final DeviceTypeEnum deviceType;

    public UserBasicLoginInfo(Long id, LoginRegisterTypeEnum loginTye, DeviceTypeEnum deviceType) {
        this.id = id;
        this.loginTye = loginTye == null ? LoginRegisterTypeEnum.UNKNOWN : loginTye;
        this.deviceType = deviceType == null ? DeviceTypeEnum.UNKNOWN : deviceType;
    }

    public UserBasicLoginInfo() {
        this.id = null;
        this.loginTye = LoginRegisterTypeEnum.UNKNOWN;
        this.deviceType = DeviceTypeEnum.UNKNOWN;
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
