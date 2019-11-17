package com.ezcoding.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-27 17:29
 */
public interface IApplicationContextValueFetchable {

    /**
     * 在程序上下文中设置对应的参数
     *
     * @param request  请求
     * @param response 响应
     */
    Object fetch(HttpServletRequest request, HttpServletResponse response);

    /**
     * 需要设置的主键
     *
     * @return 需要设置的主键
     */
    String key();

}
