package com.ezcoding.base.web.extend.spring.security.vote.voter;

import com.ezcoding.base.web.extend.spring.security.authentication.UserAuthentication;
import com.ezcoding.sdk.account.user.bean.model.DeviceTypeEnum;
import com.ezcoding.sdk.account.user.bean.model.LoginRegisterTypeEnum;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Collection;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-10-27 22:24
 */
public class LoginInfoVoter implements AccessDecisionVoter<Object> {

    private String loginTypePrefix = "LOGIN_TYPE_";
    private String deviceTypePrefix = "DEVICE_TYPE_";

    @Override
    public boolean supports(ConfigAttribute attribute) {
        String attr = attribute.getAttribute();
        return (attribute.getAttribute() != null) &&
                (attr.startsWith(loginTypePrefix) || attr.startsWith(deviceTypePrefix));
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        int result = ACCESS_ABSTAIN;

        if (!(authentication instanceof OAuth2Authentication)) {
            return result;
        }

        Authentication userAuthentication = ((OAuth2Authentication) authentication).getUserAuthentication();

        if (!(userAuthentication instanceof UserAuthentication)) {
            return result;
        }

        UserAuthentication ua = (UserAuthentication) userAuthentication;
        LoginRegisterTypeEnum loginType = ua.getLoginType();
        DeviceTypeEnum deviceType = ua.getDeviceType();

        String loginTypeStr = (loginTypePrefix + (loginType == null ? LoginRegisterTypeEnum.UNKNOWN : loginType).toString());
        String deviceTypeStr = (deviceTypePrefix + (deviceType == null ? DeviceTypeEnum.UNKNOWN : deviceType).toString());

        for (ConfigAttribute attribute : attributes) {
            if (!this.supports(attribute)) {
                continue;
            }

            result = ACCESS_DENIED;

            String attr = attribute.getAttribute().toUpperCase();
            if (loginTypeStr.equals(attr) || deviceTypeStr.equals(attr)) {
                return ACCESS_GRANTED;
            }

        }

        return result;
    }

    public String getLoginTypePrefix() {
        return loginTypePrefix;
    }

    public void setLoginTypePrefix(String loginTypePrefix) {
        this.loginTypePrefix = loginTypePrefix;
    }

    public String getDeviceTypePrefix() {
        return deviceTypePrefix;
    }

    public void setDeviceTypePrefix(String deviceTypePrefix) {
        this.deviceTypePrefix = deviceTypePrefix;
    }
}
