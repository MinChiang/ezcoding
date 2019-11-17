package com.ezcoding.sdk.account.user.bean.dto;

import com.ezcoding.sdk.account.user.bean.model.GenderEnum;
import com.ezcoding.sdk.account.user.constant.AccountUserConstants;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-01-22 13:53
 */
@Data
public class UserModifyDTO {

    /**
     * 用户编号
     */
    private String code;

    /**
     * 账号
     */
    @Length(min = AccountUserConstants.USER_ACCOUNT_LENGTH_MIN, max = AccountUserConstants.USER_ACCOUNT_LENGTH_MAX, message = "{user.account}")
    private String account;

    /**
     * 用户名
     */
    @Length(max = AccountUserConstants.USER_NAME_LENGTH_MAX, message = "{user.name}")
    private String name;

    /**
     * 性别
     */
    private GenderEnum gender;

    /**
     * 手机号码
     */
    @Pattern(regexp = AccountUserConstants.USER_PHONE_PATTERN, message = "{user.phone}")
    private String phone;

    /**
     * 邮箱
     */
    @Pattern(regexp = AccountUserConstants.USER_EMAIL_PATTERN, message = "{user.email}")
    private String email;

    /**
     * 地址
     */
    @Length(max = AccountUserConstants.USER_ADDRESS_LENGTH_MAX)
    private String address;

    /**
     * 生日
     */
    @Past
    private Date birthday;

    /**
     * 用户描述
     */
    @Length(max = AccountUserConstants.USER_DESCRIPTION_LENGTH_MAX)
    private String description;

    /**
     * 省份
     */
    private Long province;

    /**
     * 城市
     */
    private Long city;

}
