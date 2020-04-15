package com.ezcoding.extend.spring.security.successHandler;

import com.ezcoding.common.core.user.model.DeviceTypeEnum;
import com.ezcoding.common.core.user.model.LoginRegisterTypeEnum;
import com.ezcoding.common.security.authentication.UserAuthentication;
import com.ezcoding.common.web.util.NetworkUtils;
import com.ezcoding.module.user.bean.model.LoginInfo;
import com.ezcoding.module.user.dao.LoginInfoMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-10-20 0:08
 */
public class LoginRecordSuccessHandler implements AuthenticationSuccessHandler {

    private LoginInfoMapper loginInfoMapper;

    public LoginRecordSuccessHandler(LoginInfoMapper loginInfoMapper) {
        this.loginInfoMapper = loginInfoMapper;
    }

    private LoginInfo record(String ip, String user, DeviceTypeEnum deviceType, LoginRegisterTypeEnum loginRegisterType) {
        //获取真实的ip地址
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setIp(ip);
        loginInfo.setDeviceType(deviceType);
        loginInfo.setLoginType(loginRegisterType);
        loginInfo.setUser(user);
        loginInfo.setCreateTime(new Date());
        loginInfoMapper.insert(loginInfo);
        return loginInfo;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String ipAddress = NetworkUtils.getIpAddress(request);
        String user = authentication.getPrincipal().toString();

        DeviceTypeEnum deviceType = DeviceTypeEnum.UNKNOWN;
        LoginRegisterTypeEnum loginType = LoginRegisterTypeEnum.UNKNOWN;
        if (authentication instanceof UserAuthentication) {
            UserAuthentication userAuthentication = (UserAuthentication) authentication;
            loginType = userAuthentication.getLoginType();
            deviceType = userAuthentication.getDeviceType();
        }

        record(
                ipAddress,
                user,
                deviceType,
                loginType
        );
    }

    public LoginInfoMapper getLoginInfoMapper() {
        return loginInfoMapper;
    }

    public void setLoginInfoMapper(LoginInfoMapper loginInfoMapper) {
        this.loginInfoMapper = loginInfoMapper;
    }

}
