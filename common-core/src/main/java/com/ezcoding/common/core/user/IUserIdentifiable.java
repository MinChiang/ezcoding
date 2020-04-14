package com.ezcoding.common.core.user;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-14 11:43
 */
public interface IUserIdentifiable {

    /**
     * 获取用户编号
     *
     * @return 用户编号
     */
    String getCode();

    /**
     * 获取用户账号
     *
     * @return 用户账号
     */
    String getAccount();

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
     * 是否有唯一用户鉴别属性
     *
     * @return 是否有唯一用户鉴别属性
     */
    default boolean identifiable() {
        return getCode() != null ||
                getAccount() != null ||
                getPhone() != null ||
                getEmail() == null;
    }

}
