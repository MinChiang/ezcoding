package com.ezcoding.common.web;

import com.ezcoding.common.core.user.EmptyUserLoader;
import com.ezcoding.common.core.user.LoginInfoPreservable;
import com.ezcoding.common.core.user.UserIdentifiable;
import com.ezcoding.common.core.user.model.DeviceTypeEnum;
import com.ezcoding.common.core.user.model.LoginRegisterTypeEnum;
import com.ezcoding.common.security.authentication.UserAuthentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-03 14:47
 */
public class SecurityUserLoader extends EmptyUserLoader {

    @Override
    public UserIdentifiable load() {
        UserAuthentication authentication = (UserAuthentication) ((OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication()).getUserAuthentication();
        return new SecurityUser(authentication.getId(), authentication.getLoginType(), authentication.getDeviceType());
    }

    private static class SecurityUser implements UserIdentifiable, LoginInfoPreservable {

        private final Long id;

        private final LoginRegisterTypeEnum loginTye;

        private final DeviceTypeEnum deviceType;

        public SecurityUser(Long id, LoginRegisterTypeEnum loginTye, DeviceTypeEnum deviceType) {
            this.id = id;
            this.loginTye = loginTye;
            this.deviceType = deviceType;
        }

        @Override
        public LoginRegisterTypeEnum getLoginType() {
            return this.loginTye;
        }

        @Override
        public DeviceTypeEnum getDeviceType() {
            return this.deviceType;
        }

        @Override
        public Long getId() {
            return this.id;
        }

        @Override
        public String getAccount() {
            return null;
        }

        @Override
        public String getPhone() {
            return null;
        }

        @Override
        public String getEmail() {
            return null;
        }

    }

}
