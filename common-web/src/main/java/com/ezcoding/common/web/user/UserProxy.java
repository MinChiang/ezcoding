package com.ezcoding.common.web.user;

import com.ezcoding.common.core.user.IUserIdentifiable;
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
public class UserProxy implements IUser {

    /**
     * 标志user类是否已经被解析实例化
     */
    private boolean resolved = false;

    /**
     * 用户额外加载器
     */
    private IUserProxyable proxy;

    /**
     * 真实的user对象
     */
    private IUser user;

    /**
     * 被代理的对象
     */
    private IUserIdentifiable identifiable;

    public UserProxy(IUserIdentifiable identifiable, IUserProxyable proxy) {
        this.identifiable = identifiable;
        this.proxy = proxy;
    }

    /**
     * 从远程获取用户详情
     */
    private IUser loadUser() {
        if (resolved) {
            return this.user;
        }

        synchronized (this) {
            this.resolved = true;

            //如果被代理对象中没有对应的需要检索的字段，则直返回空
            if (!identifiable.identifiable()) {
                this.user = new User(identifiable.getCode(), identifiable.getAccount(), identifiable.getPhone(), identifiable.getEmail());
                return this.user;
            }

            this.user = proxy.load(this);
        }

        return this.user;
    }

    @Override
    public String getCode() {
        return Optional
                .ofNullable(this.identifiable.getCode())
                .orElseGet(() -> Optional.ofNullable(this.user).map(IUser::getCode).orElse(null));
    }

    @Override
    public String getAccount() {
        return Optional
                .ofNullable(this.identifiable.getAccount())
                .orElseGet(() -> Optional.ofNullable(this.user).map(IUser::getAccount).orElse(null));
    }

    @Override
    public String getPhone() {
        return Optional
                .ofNullable(this.identifiable.getPhone())
                .orElseGet(() -> Optional.ofNullable(this.user).map(IUser::getPhone).orElse(null));
    }

    @Override
    public String getEmail() {
        return Optional
                .ofNullable(this.identifiable.getEmail())
                .orElseGet(() -> Optional.ofNullable(this.user).map(IUser::getEmail).orElse(null));
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
    public Collection<String> getRoles() {
        return this.user.getRoles();
    }

}
