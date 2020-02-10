package com.ezcoding.common.web.jwt;

import org.springframework.http.HttpHeaders;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-10-14 15:15
 */
public class AuthSettings {

    /**
     * 默认请求头
     */
    public static final String DEFAULT_HEADER = HttpHeaders.AUTHORIZATION;

    /**
     * 默认过期时间
     */
    public static final long DEFAULT_EXPIRATION = 7 * 24 * 60 * 60;

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
