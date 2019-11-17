package com.ezcoding.common.foundation.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-10-01 21:21
 */
public class NetworkUtils {

    private static final String UNKNOWN_HEADER = "unknown";

    private NetworkUtils() {

    }

    /**
     * 获取用户真实的ip地址
     *
     * @param request 请求
     * @return 真实的ip地址
     */
    public static String getIPAddress(HttpServletRequest request) {
        String ip = null;

        //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");

        if (StringUtils.isBlank(ipAddresses) || UNKNOWN_HEADER.equalsIgnoreCase(ipAddresses)) {
            //Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }

        if (StringUtils.isBlank(ipAddresses) || UNKNOWN_HEADER.equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }

        if (StringUtils.isBlank(ipAddresses) || UNKNOWN_HEADER.equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }

        if (StringUtils.isBlank(ipAddresses) || UNKNOWN_HEADER.equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }

        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (StringUtils.isNotEmpty(ipAddresses)) {
            ip = ipAddresses.split(",")[0];
        }

        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (StringUtils.isBlank(ipAddresses) || UNKNOWN_HEADER.equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
