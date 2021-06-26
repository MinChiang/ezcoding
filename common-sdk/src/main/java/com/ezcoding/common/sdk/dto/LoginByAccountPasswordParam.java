package com.ezcoding.common.sdk.dto;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-06-04 15:21
 */
public class LoginByAccountPasswordParam extends AbstractLoginParam implements Serializable {

    private static final long serialVersionUID = -37550354763296309L;

    private String account;
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
