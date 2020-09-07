package com.ezcoding.common.web.filter;

import com.ezcoding.common.web.jwt.AuthSettings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-27 17:40
 */
public class UserTokenContextSettable implements ApplicationContextValueFetchable<String> {

    private String header = AuthSettings.DEFAULT_HEADER;

    public UserTokenContextSettable() {
    }

    public UserTokenContextSettable(String header) {
        this.header = header;
    }

    @Override
    public String fetch(HttpServletRequest request, HttpServletResponse response) {
        return request.getHeader(this.header);
    }

    @Override
    public String key() {
        return ApplicationContextValueConstants.TOKEN;
    }

}
