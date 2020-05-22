package com.ezcoding.common.core.user.model;

import com.ezcoding.common.core.user.LoginInfoPreservable;
import com.ezcoding.common.core.user.UserIdentifiable;

import java.util.Collection;
import java.util.Date;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-01-04 10:07
 */
public interface UserDetailInformationIdentifiable extends UserIdentifiable, LoginInfoPreservable {

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
