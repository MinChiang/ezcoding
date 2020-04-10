package com.ezcoding.module.user.bean.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ezcoding.common.core.user.model.*;
import com.ezcoding.common.mybatis.model.BaseModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-16 21:48
 */
@TableName("account_user")
public class User extends BaseModel implements IUser, UserDetails, Serializable {

    /**
     * 用户编号
     */
    private String code;

    /**
     * 账号
     */
    private String account;

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别
     */
    private GenderEnum gender;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 地址
     */
    private String address;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 入职日期
     */
    private Date hireDate;

    /**
     * 头像，为标准的http地址
     */
    private String profilePhoto;

    /**
     * 用户描述
     */
    private String description;

    /**
     * 是否已经实名认证
     */
    private Boolean verified;

    /**
     * 省份
     */
    private Integer province;

    /**
     * 城市
     */
    private Integer city;

    /**
     * 状态
     */
    private UserStatusEnum status;

    /**
     * 权限
     */
    @TableField(exist = false)
    private Collection<? extends GrantedAuthority> authorities = new HashSet<>(0);

    /**
     * 登录类型
     */
    @TableField(exist = false)
    private LoginRegisterTypeEnum loginType;

    /**
     * 设备类型
     */
    @TableField(exist = false)
    private DeviceTypeEnum deviceType;

    /**
     * 新建用户实例
     *
     * @return 新建的用户实例
     */
    public static User create() {
        return new User();
    }

    public User code(String code) {
        this.code = code;
        return this;
    }

    public User account(String account) {
        this.account = account;
        return this;
    }

    public User name(String name) {
        this.name = name;
        return this;
    }

    public User password(String password) {
        this.password = password;
        return this;
    }

    public User gender(GenderEnum gender) {
        this.gender = gender;
        return this;
    }

    public User phone(String phone) {
        this.phone = phone;
        return this;
    }

    public User email(String email) {
        this.email = email;
        return this;
    }

    public User address(String address) {
        this.address = address;
        return this;
    }

    public User birthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }

    public User hireDate(Date hireDate) {
        this.hireDate = hireDate;
        return this;
    }

    public User profilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
        return this;
    }

    public User description(String description) {
        this.description = description;
        return this;
    }

    public User verified(Boolean verified) {
        this.verified = verified;
        return this;
    }

    public User province(Integer province) {
        this.province = province;
        return this;
    }

    public User city(Integer city) {
        this.city = city;
        return this;
    }

    public User status(UserStatusEnum status) {
        this.status = status;
        return this;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getUsername() {
        return this.code;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.status == UserStatusEnum.NORMAL;
    }

    @Override
    public Collection<String> getRoles() {
        Collection<String> result = new HashSet<>(0);
        this.authorities.forEach(a -> result.add(a.getAuthority()));
        return result;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
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

    @Override
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    @Override
    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    @Override
    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    @Override
    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public UserStatusEnum getStatus() {
        return status;
    }

    public void setStatus(UserStatusEnum status) {
        this.status = status;
    }

    @Override
    public LoginRegisterTypeEnum getLoginType() {
        return null;
    }

    @Override
    public DeviceTypeEnum getDeviceType() {
        return null;
    }

}
