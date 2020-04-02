package com.ezcoding.common.web.resolver;

import com.ezcoding.common.core.user.model.IUser;
import com.ezcoding.common.web.user.UserProxy;
import com.ezcoding.common.web.user.CompositeUserLoader;
import com.ezcoding.common.web.user.IUserLoadable;
import com.ezcoding.common.web.user.IUserProxyable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-10-15 15:19
 */
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private IUserLoadable loader;
    private IUserProxyable proxy;

    public UserArgumentResolver(IUserLoadable loader, IUserProxyable proxy) {
        this.loader = loader;
        this.proxy = proxy;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class) && IUser.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        //获取当前用户
        IUser user = loader.load();

        CurrentUser parameterAnnotation = parameter.getParameterAnnotation(CurrentUser.class);
        //校验当前是否必须含有登陆用户
        assert parameterAnnotation != null;
        if (parameterAnnotation.required()) {
            if (user == null || StringUtils.isEmpty(user.getCode())) {
                throw new RuntimeException("用户未登录");
            }
        }

        switch (parameterAnnotation.type()) {
            case AUTO:
            case PROXY:
                user = new UserProxy(user, this.proxy);
                break;
            case AUTH:
                break;
            default:
                break;
        }

        return user;
    }

    public IUserLoadable getLoader() {
        return loader;
    }

    public void setLoader(CompositeUserLoader loader) {
        this.loader = loader;
    }

    public IUserProxyable getProxy() {
        return proxy;
    }

    public void setProxy(IUserProxyable proxy) {
        this.proxy = proxy;
    }

}
