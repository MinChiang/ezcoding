package com.ezcoding.common.core.user.model;

import com.google.common.collect.Sets;
import lombok.Data;

import java.util.Collection;
import java.util.Date;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-01-05 10:21
 */
@Data
public class UserDTO implements IUser {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户编号
     */
    private String code;

    /**
     * 账号
     */
    private String account;

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别
     */
    private GenderEnum gender;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 地址
     */
    private String address;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 入职日期
     */
    private Date hireDate;

    /**
     * 头像，为标准的http地址
     */
    private String profilePhoto;

    /**
     * 用户描述
     */
    private String description;

    /**
     * 是否已经实名认证
     */
    private Boolean verified;

    /**
     * 省份
     */
    private Integer province;

    /**
     * 城市
     */
    private Integer city;

    /**
     * 拥有的权限
     */
    private Collection<String> roles = Sets.newHashSet();

}
