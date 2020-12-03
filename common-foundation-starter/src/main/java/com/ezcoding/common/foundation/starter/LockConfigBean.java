package com.ezcoding.common.foundation.starter;

import com.ezcoding.common.foundation.core.lock.impl.SimpleLockIdentification;
import com.ezcoding.common.foundation.core.lock.impl.SimpleLockImplement;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-19 14:26
 */
public class LockConfigBean {

    private Boolean enable = true;

    private String defaultLockImplementClass = SimpleLockImplement.class.getName();
    private String defaultLockIdentificationClass = SimpleLockIdentification.class.getName();

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

}
