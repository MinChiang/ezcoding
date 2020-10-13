package com.ezcoding.common.web;

import com.ezcoding.common.core.user.EmptyUserLoader;
import com.ezcoding.common.core.user.UserBasicIdentifiable;
import com.ezcoding.common.core.user.model.UserBasicLoginInfo;
import com.ezcoding.common.security.authentication.UserAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-03 14:47
 */
public class SecurityUserLoader extends EmptyUserLoader {

    @Override
    public UserBasicIdentifiable load() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2Authentication) {
            authentication = ((OAuth2Authentication) authentication).getUserAuthentication();
            if (authentication instanceof UserAuthentication) {
                UserAuthentication userAuthentication = (UserAuthentication) authentication;
                return new UserBasicLoginInfo(userAuthentication.getId(), userAuthentication.getLoginType(), userAuthentication.getDeviceType());
            }
        }
        return new UserBasicLoginInfo();
    }

}
