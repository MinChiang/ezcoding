package com.ezcoding.sdk.account.user.bean.dto;

import com.ezcoding.sdk.account.user.bean.model.GenderEnum;
import lombok.Data;

import java.util.Date;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-01-06 14:37
 */
@Data
public class UserDetailResultDTO {

    /**
     * 主键
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
     * 省份id
     */
    private Long province;

    /**
     * 省份名称
     */
    private String provinceName;

    /**
     * 城市id
     */
    private Long city;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String modifier;

    /**
     * 更新时间
     */
    private Date modifyTime;

    /**
     * 是否被删除
     */
    private Boolean deleted;

}
