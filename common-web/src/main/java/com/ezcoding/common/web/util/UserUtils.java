package com.ezcoding.common.web.util;

import com.ezcoding.common.core.user.model.DeviceTypeEnum;
import com.ezcoding.common.core.user.model.LoginRegisterTypeEnum;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-10-25 0:36
 */
public class UserUtils {

    public static final String USER_AGENT_HEADER = HttpHeaders.USER_AGENT;

    /**
     * 鉴别设备类型
     *
     * @return 设备类型
     */
    public static DeviceTypeEnum distinguishDeviceType(HttpServletRequest request) {
        String header = request.getHeader(USER_AGENT_HEADER);
        if (header == null || header.isEmpty()) {
            return DeviceTypeEnum.UNKNOWN;
        }
        UserAgent userAgent = UserAgent.parseUserAgentString(header);
        DeviceType deviceType = userAgent.getOperatingSystem().getDeviceType();
        switch (deviceType) {
            case COMPUTER:
                return DeviceTypeEnum.COMPUTER;
            case MOBILE:
                return DeviceTypeEnum.MOBILE;
            case TABLET:
                return DeviceTypeEnum.TABLET;
            case GAME_CONSOLE:
                return DeviceTypeEnum.GAME_CONSOLE;
            case DMR:
                return DeviceTypeEnum.DMR;
            case WEARABLE:
                return DeviceTypeEnum.WEARABLE;
            default:
                return DeviceTypeEnum.UNKNOWN;
        }
    }

    /**
     * 确定登陆类型
     *
     * @param o 参数
     * @return 确定的登陆类型
     */
    public static LoginRegisterTypeEnum distinguishLoginType(Object o) {
        if (o instanceof LoginRegisterTypeEnum) {
            return (LoginRegisterTypeEnum) o;
        }
        if (o instanceof Integer) {
            return LoginRegisterTypeEnum.from((Integer) o);
        }
        if (o instanceof String) {
            return LoginRegisterTypeEnum.from(Integer.parseInt((String) o));
        }
        return LoginRegisterTypeEnum.UNKNOWN;
    }

    public static String getUserAgentHeader() {
        return USER_AGENT_HEADER;
    }

}
