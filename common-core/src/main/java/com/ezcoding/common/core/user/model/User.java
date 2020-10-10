package com.ezcoding.common.core.user.model;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-01-05 10:21
 */
public class User implements UserDetailInformationAvailable {

    /**
     * 用户id
     */
    private Long id;

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
     * 拥有的权限
     */
    private Collection<String> roles = new HashSet<>(0);

    /**
     * 登录类型
     */
    private LoginRegisterTypeEnum loginType;

    /**
     * 登录设备类型
     */
    private DeviceTypeEnum deviceType;

    public User() {
    }

    public User(Long id, String account, String phone, String email) {
        this.id = id;
        this.account = account;
        this.phone = phone;
        this.email = email;
    }

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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    @Override
    public Collection<String> getRoles() {
        return roles;
    }

    public void setRoles(Collection<String> roles) {
        this.roles = roles;
    }

    @Override
    public LoginRegisterTypeEnum getLoginType() {
        return this.loginType;
    }

    public void setLoginType(LoginRegisterTypeEnum loginType) {
        this.loginType = loginType;
    }

    @Override
    public DeviceTypeEnum getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(DeviceTypeEnum deviceType) {
        this.deviceType = deviceType;
    }

    @Override
    public UserStatusEnum getStatus() {
        return status;
    }

    public void setStatus(UserStatusEnum status) {
        this.status = status;
    }

}
