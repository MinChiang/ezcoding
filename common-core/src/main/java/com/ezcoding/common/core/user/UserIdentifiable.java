package com.ezcoding.common.core.user;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-14 11:43
 */
public interface UserIdentifiable {

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
        return getId() != null ||
                getAccount() != null ||
                getPhone() != null ||
                getEmail() != null;
    }

    /**
     * 按照id，Phone，Account，Email的顺序获取id
     *
     * @return 获取到的id
     */
    default Object acquireId() {
        Object result = getId();
        if (result != null) {
            return result;
        }
        result = getPhone();
        if (result != null) {
            return result;
        }
        result = getAccount();
        if (result != null) {
            return result;
        }
        result = getEmail();
        return result;
    }

}
