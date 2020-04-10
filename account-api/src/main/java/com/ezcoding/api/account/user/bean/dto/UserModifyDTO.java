package com.ezcoding.api.account.user.bean.dto;

import com.ezcoding.common.core.user.model.GenderEnum;
import com.ezcoding.api.account.user.constant.AccountUserConstants;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-01-22 13:53
 */
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getProvince() {
        return province;
    }

    public void setProvince(Long province) {
        this.province = province;
    }

    public Long getCity() {
        return city;
    }

    public void setCity(Long city) {
        this.city = city;
    }

}
