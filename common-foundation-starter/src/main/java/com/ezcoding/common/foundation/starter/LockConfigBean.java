package com.ezcoding.common.foundation.starter;

import com.ezcoding.common.foundation.core.lock.impl.SimpleLockIdentification;
import com.ezcoding.common.foundation.core.lock.impl.SimpleLockImplement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-19 14:26
 */
public class LockConfigBean {

    private Boolean enable = true;

    private String defaultLockImplementClass = SimpleLockImplement.class.getName();
    private String defaultLockIdentificationClass = SimpleLockIdentification.class.getName();

    private List<String> lockImplementClass = new ArrayList<>();
    private List<String> identificationClass = new ArrayList<>();

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getDefaultLockImplementClass() {
        return defaultLockImplementClass;
    }

    public void setDefaultLockImplementClass(String defaultLockImplementClass) {
        this.defaultLockImplementClass = defaultLockImplementClass;
    }

    public String getDefaultLockIdentificationClass() {
        return defaultLockIdentificationClass;
    }

    public void setDefaultLockIdentificationClass(String defaultLockIdentificationClass) {
        this.defaultLockIdentificationClass = defaultLockIdentificationClass;
    }

    public List<String> getLockImplementClass() {
        return lockImplementClass;
    }

    public void setLockImplementClass(List<String> lockImplementClass) {
        this.lockImplementClass = lockImplementClass;
    }

    public List<String> getIdentificationClass() {
        return identificationClass;
    }

    public void setIdentificationClass(List<String> identificationClass) {
        this.identificationClass = identificationClass;
    }

}
