package com.ezcoding.common.sdk.dto;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-06-08 16:36
 */
public class LoginByEmailPasswordParam extends AbstractLoginParam implements Serializable {

    private static final long serialVersionUID = -6024735577535191027L;

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
