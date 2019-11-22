package com.ezcoding.starter.foundation.config;

import com.ezcoding.common.foundation.core.tools.jwt.AuthSettings;
import lombok.Data;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-11 10:22
 */
@Data
public class AuthConfigBean {

    private String header = AuthSettings.DEFAULT_HEADER;
    private Long expiration = AuthSettings.DEFAULT_EXPIRATION;

}
