package com.ezcoding.account.module.user.bean.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ezcoding.sdk.account.user.bean.model.DeviceTypeEnum;
import com.ezcoding.sdk.account.user.bean.model.LoginRegisterTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-16 21:48
 */
@Data
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

}
