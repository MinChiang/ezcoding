package com.ezcoding.api.account.user.bean.dto;

import com.ezcoding.api.account.user.constant.AccountUserConstants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-09-28 14:27
 */
public class UserRegisterDTO {

    /**
     * 输入的验证码
     */
    @NotNull(message = "{user.verificationCode}")
    private String verificationCode;

    /**
     * 回执
     */
    @NotNull(message = "{user.receipt}")
    private String receipt;

    /**
     * 设置的密码
     */
    @NotNull(message = "{user.password}")
    private String password;

    /**
     * 用户名
     */
    @NotNull(message = "{user.name}")
    private String name;

    /**
     * 手机号码
     */
    @NotNull(message = "{user.phone}")
    @Pattern(regexp = AccountUserConstants.USER_PHONE_PATTERN, message = "{user.phone}")
    private String phone;

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
