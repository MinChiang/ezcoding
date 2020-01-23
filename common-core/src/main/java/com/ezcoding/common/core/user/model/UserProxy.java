package com.ezcoding.common.core.user.model;

import com.ezcoding.common.core.user.resolve.IUserLoadable;
import com.ezcoding.common.core.user.resolve.IUserProxyable;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Date;

/**
 * user代理对象
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-12-11 9:21
 */
public class UserProxy implements IUser, IUserLoadable {

    /**
     * 标志user类是否已经被解析实例化
     */
    private boolean resolved;

    /**
     * 用户额外加载器
     */
    private IUserProxyable proxy;

    /**
     * 真实的user对象
     */
    private IUser user;

    public UserProxy(IUser user, IUserProxyable proxy) {
        this.user = user;
        this.proxy = proxy;
        this.resolved = false;
    }

    /**
     * 从远程获取用户详情
     */
    private IUser loadUser() {
        if (resolved) {
            return this.user;
        }

        synchronized (this) {
            //如果被代理对象中没有对应的用户编号，则直接空解析
            if (StringUtils.isEmpty(this.user.getCode())) {
                this.user = new User();
                this.resolved = true;
                return this.user;
            }

            this.user = this.load();
            this.resolved = true;
        }
        return this.user;
    }

    @Override
    public Long getId() {
        return this.loadUser().getId();
    }

    @Override
    public String getCode() {
        return this.user.getCode();
    }

    @Override
    public String getAccount() {
        return this.loadUser().getAccount();
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
    public String getPhone() {
        return this.loadUser().getPhone();
    }

    @Override
    public String getEmail() {
        return this.loadUser().getEmail();
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

    @Override
    public IUser load() {
        return proxy.load(this);
    }

}
