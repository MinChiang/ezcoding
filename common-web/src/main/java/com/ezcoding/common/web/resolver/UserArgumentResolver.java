package com.ezcoding.common.web.resolver;

import com.ezcoding.common.core.user.model.IUser;
import com.ezcoding.common.core.user.resolve.CurrentUserLoader;
import com.ezcoding.common.core.user.resolve.IUserProxyable;
import com.ezcoding.common.foundation.core.exception.CommonApplicationException;
import com.ezcoding.common.foundation.core.exception.ExceptionBuilderFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.ezcoding.common.foundation.core.exception.CommonApplicationException.COMMON_USER_NOT_LOGIN_ERROR;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-10-15 15:19
 */
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private CurrentUserLoader currentUserLoader;
    private IUserProxyable proxy;

    public UserArgumentResolver(CurrentUserLoader currentUserLoader, IUserProxyable proxy) {
        this.currentUserLoader = currentUserLoader;
        this.proxy = proxy;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class) && IUser.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        //获取当前用户
        IUser user = currentUserLoader.load();

        CurrentUser parameterAnnotation = parameter.getParameterAnnotation(CurrentUser.class);
        //校验当前是否必须含有登陆用户
        if (parameterAnnotation.required()) {
            if (user == null || StringUtils.isEmpty(user.getCode())) {
                throw ExceptionBuilderFactory.lookupByAlias(CommonApplicationException.class, COMMON_USER_NOT_LOGIN_ERROR).instance().build();
            }
        }

        switch (parameterAnnotation.type()) {
            case AUTO:
                user = proxy.load(user);
                break;
            case PROXY:
                user = proxy.load(user);
                break;
            case AUTH:
                break;
            default:
                break;
        }

        return user;
    }

    public CurrentUserLoader getCurrentUserLoader() {
        return currentUserLoader;
    }

    public void setCurrentUserLoader(CurrentUserLoader currentUserLoader) {
        this.currentUserLoader = currentUserLoader;
    }

    public IUserProxyable getProxy() {
        return proxy;
    }

    public void setProxy(IUserProxyable proxy) {
        this.proxy = proxy;
    }

}
