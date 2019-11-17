package com.ezcoding.base.web.extend.spring.config;

import com.ezcoding.common.foundation.core.constant.GlobalConstants;
import com.ezcoding.common.foundation.core.tools.jwt.AuthSettings;
import lombok.Data;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-11 10:22
 */
@Data
public class AuthConfigBean {

    private String header = GlobalConstants.Header.AUTHORIZATION;
    private Long expiration = AuthSettings.DEFAULT_EXPIRATION;

}
