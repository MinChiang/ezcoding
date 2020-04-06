package com.ezcoding.common.core.user;

import com.ezcoding.common.core.user.model.DeviceTypeEnum;
import com.ezcoding.common.core.user.model.LoginRegisterTypeEnum;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-03 22:51
 */
public interface ILoginInfoPreservable {

    /**
     * 获取登录类型
     *
     * @return 登录类型
     */
    LoginRegisterTypeEnum getLoginType();

    /**
     * 获取登录设备类型
     *
     * @return 登录设备类型
     */
    DeviceTypeEnum getDeviceType();

}
