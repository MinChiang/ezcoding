package com.ezcoding.sdk.account.user.bean.dto;

import com.ezcoding.sdk.account.user.constant.AccountUserConstants;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-09-28 14:27
 */
@Data
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

}
