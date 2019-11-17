package com.ezcoding.web.resolver;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-31 15:50
 */
public enum CurrentUserTypeEnum {

    /**
     * 主程序决定
     */
    AUTO,

    /**
     * 使用user代理对象
     */
    PROXY,

    /**
     * 身份验证user
     */
    AUTH

}
