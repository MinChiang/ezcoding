package com.ezcoding.common.sdk.dto;

import com.ezcoding.common.core.user.model.DeviceTypeEnum;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-06-08 16:37
 */
public abstract class AbstractLoginParam implements Serializable {

    private static final long serialVersionUID = 8540049895955110646L;

    private DeviceTypeEnum deviceType;

    public DeviceTypeEnum getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceTypeEnum deviceType) {
        this.deviceType = deviceType;
    }

}
