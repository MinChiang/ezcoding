package com.ezcoding.common.foundation.starter;

import com.ezcoding.common.foundation.core.lock.impl.SimpleLock;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-19 14:26
 */
public class LockConfigBean {

    private Boolean enable = true;

    private List<String> lockClass = new ArrayList<>();
    private String defaultLockClass = SimpleLock.class.getName();

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getDefaultLockClass() {
        return defaultLockClass;
    }

    public void setDefaultLockClass(String defaultLockClass) {
        this.defaultLockClass = defaultLockClass;
    }

    public List<String> getLockClass() {
        return lockClass;
    }

    public void setLockClass(List<String> lockClass) {
        this.lockClass = lockClass;
    }

}
