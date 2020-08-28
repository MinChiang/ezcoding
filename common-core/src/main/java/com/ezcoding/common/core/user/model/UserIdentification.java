package com.ezcoding.common.core.user.model;

import com.ezcoding.common.core.user.UserIdentifiable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-03-23 14:36
 */
public class UserIdentification implements UserIdentifiable {

    /**
     * 用户编号
     */
    private Long id;

    /**
     * 账号
     */
    private String account;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
