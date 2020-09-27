package com.ezcoding.common.web.user;

import com.ezcoding.common.core.user.UserIdentifiable;
import com.ezcoding.common.core.user.model.*;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

/**
 * user代理对象
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-12-11 9:21
 */
public class UserProxy implements UserDetailInformationIdentifiable {

    /**
     * 用户额外加载器
     */
    private final UserProxyable proxy;

    /**
     * 真实的user对象
     */
    private volatile UserDetailInformationIdentifiable user;

    /**
     * 被代理的对象
     */
    private final UserIdentifiable identifiable;

    public UserProxy(UserIdentifiable identifiable, UserProxyable proxy) {
        this.identifiable = identifiable;
        this.proxy = proxy;
    }

    /**
     * 从远程获取用户详情
     */
    private UserDetailInformationIdentifiable loadUser() {
        if (this.user == null) {
            synchronized (this) {
                if (this.user == null) {
                    if (identifiable.identifiable()) {
                        //如果被代理对象中有对应的需要检索的字段
                        UserDetailInformationIdentifiable load = proxy.load(this.identifiable);
                        this.user = (load == null ? new User() : load);
                    } else {
                        this.user = new User();
                    }
                }
            }
        }
        return this.user;
    }

    @Override
    public Long getId() {
        return Optional
                .ofNullable(this.identifiable.getId())
                .orElseGet(() -> Optional.ofNullable(this.user).map(UserDetailInformationIdentifiable::getId).orElse(null));
    }

    @Override
    public String getAccount() {
        return Optional
                .ofNullable(this.identifiable.getAccount())
                .orElseGet(() -> Optional.ofNullable(this.user).map(UserDetailInformationIdentifiable::getAccount).orElse(null));
    }

    @Override
    public String getPhone() {
        return Optional
                .ofNullable(this.identifiable.getPhone())
                .orElseGet(() -> Optional.ofNullable(this.user).map(UserDetailInformationIdentifiable::getPhone).orElse(null));
    }

    @Override
    public String getEmail() {
        return Optional
                .ofNullable(this.identifiable.getEmail())
                .orElseGet(() -> Optional.ofNullable(this.user).map(UserDetailInformationIdentifiable::getEmail).orElse(null));
    }

    @Override
    public LoginRegisterTypeEnum getLoginType() {
        return this.user.getLoginType();
    }

    @Override
    public DeviceTypeEnum getDeviceType() {
        return this.user.getDeviceType();
    }

    @Override
    public String getName() {
        return this.loadUser().getName();
    }

    @Override
    public GenderEnum getGender() {
        return this.loadUser().getGender();
    }

    @Override
    public String getAddress() {
        return this.loadUser().getAddress();
    }

    @Override
    public Date getBirthday() {
        return this.loadUser().getBirthday();
    }

    @Override
    public Date getHireDate() {
        return this.loadUser().getHireDate();
    }

    @Override
    public String getProfilePhoto() {
        return this.loadUser().getProfilePhoto();
    }

    @Override
    public String getDescription() {
        return this.loadUser().getDescription();
    }

    @Override
    public Boolean getVerified() {
        return this.loadUser().getVerified();
    }

    @Override
    public Integer getProvince() {
        return this.loadUser().getProvince();
    }

    @Override
    public Integer getCity() {
        return this.loadUser().getCity();
    }

    @Override
    public UserStatusEnum getStatus() {
        return this.loadUser().getStatus();
    }

    @Override
    public Collection<String> getRoles() {
        return this.loadUser().getRoles();
    }

}
