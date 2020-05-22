package com.ezcoding.account.module.user.bean.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ezcoding.base.web.extend.mybatis.model.BaseModel;
import com.ezcoding.sdk.account.user.api.IUser;
import com.ezcoding.sdk.account.user.bean.model.GenderEnum;
import com.ezcoding.sdk.account.user.bean.model.UserStatusEnum;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-16 21:48
 */
@Data
@EqualsAndHashCode(callSuper = true)
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
    private Collection<? extends GrantedAuthority> authorities = Sets.newHashSet();

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
        Collection<String> result = Sets.newHashSet();
        this.authorities.forEach(a -> result.add(a.getAuthority()));
        return result;
    }

}
