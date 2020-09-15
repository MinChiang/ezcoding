package com.ezcoding.common.web.resolver;

import com.ezcoding.common.core.user.UserIdentifiable;
import com.ezcoding.common.core.user.UserLoadable;
import com.ezcoding.common.core.user.model.User;
import com.ezcoding.common.web.user.CompositeUserLoader;
import com.ezcoding.common.web.user.UserProxy;
import com.ezcoding.common.web.user.UserProxyable;
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

    private UserLoadable loader;
    private UserProxyable proxy;

    public UserArgumentResolver(UserLoadable loader, UserProxyable proxy) {
        this.loader = loader;
        this.proxy = proxy;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class) && UserIdentifiable.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        //获取当前用户
        UserIdentifiable user = loader.load();

        CurrentUser parameterAnnotation = parameter.getParameterAnnotation(CurrentUser.class);
        //校验当前是否必须含有登陆用户
        if (parameterAnnotation.required()) {
            if (user == null || user.getId() == null) {
                throw new RuntimeException("user not login");
            }
        }

        switch (parameterAnnotation.proxy()) {
            case AUTO:
                user = new UserProxy(user, this.proxy);
                break;
            case NONE:
                user = new User(user.getId(), user.getAccount(), user.getPhone(), user.getEmail());
                break;
            default:
                break;
        }

        return user;
    }

    public UserLoadable getLoader() {
        return loader;
    }

    public void setLoader(CompositeUserLoader loader) {
        this.loader = loader;
    }

    public UserProxyable getProxy() {
        return proxy;
    }

    public void setProxy(UserProxyable proxy) {
        this.proxy = proxy;
    }

}
