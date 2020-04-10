package com.ezcoding.module.user.bean.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ezcoding.common.core.user.model.DeviceTypeEnum;
import com.ezcoding.common.core.user.model.LoginRegisterTypeEnum;

import java.io.Serializable;
import java.util.Date;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-16 21:48
 */
@TableName("account_login_info")
public class LoginInfo implements Serializable {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 登录ip
     */
    private String ip;

    /**
     * 登陆方式
     */
    private LoginRegisterTypeEnum loginType;

    /**
     * 登录设备类型
     */
    private DeviceTypeEnum deviceType;

    /**
     * 登录用户编号
     */
    private String user;

    /**
     * 登陆时间
     */
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public LoginRegisterTypeEnum getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginRegisterTypeEnum loginType) {
        this.loginType = loginType;
    }

    public DeviceTypeEnum getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceTypeEnum deviceType) {
        this.deviceType = deviceType;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
