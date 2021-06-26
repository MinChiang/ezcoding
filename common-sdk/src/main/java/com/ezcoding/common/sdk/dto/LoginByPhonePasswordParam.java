package com.ezcoding.common.sdk.dto;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-06-08 16:36
 */
public class LoginByPhonePasswordParam extends AbstractLoginParam implements Serializable {

    private static final long serialVersionUID = 7736472889361116197L;

    private String phone;
    private String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
