package com.ezcoding.common.web.user;

import com.ezcoding.common.core.user.UserBasicIdentifiable;
import com.ezcoding.common.core.user.UserLoadable;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-07-19 16:35
 */
public abstract class AbstractRequestUserLoader implements UserLoadable {

    @Override
    public UserBasicIdentifiable load() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (!(requestAttributes instanceof ServletRequestAttributes)) {
            throw new IllegalStateException("illegal invoke! maybe current enviroment is not web or org.springframework.web.context.request.RequestContextHolder.setRequestAttributes(org.springframework.web.context.request.RequestAttributes, boolean) not invoked!");
        }
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
        return this.load(httpServletRequest);
    }

    /**
     * 根据请求内容获取用户认证信息
     *
     * @param request 请求信息
     * @return 用户认证信息
     */
    public abstract UserBasicIdentifiable load(HttpServletRequest request);

}
