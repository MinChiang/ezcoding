package com.ezcoding.common.foundation.core.tools.jwt;

import com.ezcoding.common.foundation.core.constant.GlobalConstants;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-10-14 15:15
 */
public class AuthSettings {

    public static final String DEFAULT_HEADER = "Authorization";
    public static final long DEFAULT_EXPIRATION = 604800L;

    private String header = DEFAULT_HEADER;
    private long expiration = DEFAULT_EXPIRATION;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }
}
