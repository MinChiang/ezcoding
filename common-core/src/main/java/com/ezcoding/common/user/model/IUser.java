package com.ezcoding.common.user.model;

import java.util.Collection;
import java.util.Date;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-01-04 10:07
 */
public interface IUser extends IUserIdentifiable {

    /**
     * 获取用户id
     *
     * @return 用户id
     */
    Long getId();

    /**
     * 获取用户账号
     *
     * @return 用户账号
     */
    String getAccount();

    /**
     * 获取用户名字
     *
     * @return 用户名字
     */
    String getName();

    /**
     * 获取用户性别
     *
     * @return 用户性别
     */
    GenderEnum getGender();

    /**
     * 获取用户手机号
     *
     * @return 用户手机号
     */
    String getPhone();

    /**
     * 获取用户邮箱
     *
     * @return 用户邮箱
     */
    String getEmail();

    /**
     * 获取用户地址
     *
     * @return 用户地址
     */
    String getAddress();

    /**
     * 获取用户生日
     *
     * @return 用户生日
     */
    Date getBirthday();

    /**
     * 获取用户入职日期
     *
     * @return 用户入职日期
     */
    Date getHireDate();

    /**
     * 获取用户头像
     *
     * @return 用户头像
     */
    String getProfilePhoto();

    /**
     * 获取用户描述
     *
     * @return 用户描述
     */
    String getDescription();

    /**
     * 是否已经实名认证
     *
     * @return 是否已经实名认证
     */
    Boolean getVerified();

    /**
     * 省份
     *
     * @return 省份
     */
    Integer getProvince();

    /**
     * 城市
     *
     * @return 城市
     */
    Integer getCity();

    /**
     * 获取对应的权限列表
     *
     * @return 用户权限列表
     */
    Collection<String> getRoles();

}
