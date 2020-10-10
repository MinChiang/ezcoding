package com.ezcoding.common.web;

import com.ezcoding.common.core.user.EmptyUserLoader;
import com.ezcoding.common.core.user.UserBasicIdentifiable;
import com.ezcoding.common.core.user.model.UserBasicLoginInfo;
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
    public UserBasicIdentifiable load() {
        UserAuthentication authentication = (UserAuthentication) ((OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication()).getUserAuthentication();
        return new UserBasicLoginInfo(authentication.getId(), authentication.getLoginType(), authentication.getDeviceType());
    }

}
