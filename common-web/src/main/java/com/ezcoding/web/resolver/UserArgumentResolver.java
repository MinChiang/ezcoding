package com.ezcoding.web.resolver;

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

//    private static final UserResolver USER_RESOLVER_UTILS = UserResolver.getInstance();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
//        return parameter.hasParameterAnnotation(CurrentUser.class) && IUser.class.isAssignableFrom(parameter.getParameterType());
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
//        IUser user = USER_RESOLVER_UTILS.currentUser();
//
//        CurrentUser parameterAnnotation = parameter.getParameterAnnotation(CurrentUser.class);
//        //校验当前是否必须含有登陆用户
//        if (parameterAnnotation.required()) {
//            if (user == null || StringUtils.isEmpty(user.getCode())) {
//                throw CommonApplicationExceptionConstants.COMMON_USER_NOT_LOGIN_ERROR.instance().build();
//            }
//        }
//
//        switch (parameterAnnotation.type()) {
//            case AUTO:
//                user = USER_RESOLVER_UTILS.currentUserWithProxy(user);
//                break;
//            case PROXY:
//                user = USER_RESOLVER_UTILS.currentUserWithProxy(user);
//                break;
//            case AUTH:
//                break;
//            default:
//                break;
//        }
//
//        return user;
        return null;
    }

}
