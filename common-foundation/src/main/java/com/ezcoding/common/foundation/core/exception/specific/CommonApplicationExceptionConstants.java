package com.ezcoding.common.foundation.core.exception.specific;

import com.ezcoding.common.foundation.core.application.specific.DefaultModuleLayerModule;
import com.ezcoding.common.foundation.core.application.ModuleLayerModule;
import com.ezcoding.common.foundation.core.exception.ExceptionBuilderFactory;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-26 23:30
 */
public class CommonApplicationExceptionConstants {

    private static final ModuleLayerModule DEFAULT_MODULE_LAYER_MODULE = DefaultModuleLayerModule.getInstance();

    public static final ExceptionBuilderFactory<CommonApplicationException>
            COMMON_PARAM_VALIDATE_ERROR = ExceptionBuilderFactory.create(CommonApplicationException.class, DEFAULT_MODULE_LAYER_MODULE, "0001", "参数校验异常：{0}"),
            COMMON_REQUEST_TYPE_ERROR = ExceptionBuilderFactory.create(CommonApplicationException.class, DEFAULT_MODULE_LAYER_MODULE, "0002", "请求类型异常"),
            COMMON_RESOURCE_NOT_FIND_ERROR = ExceptionBuilderFactory.create(CommonApplicationException.class, DEFAULT_MODULE_LAYER_MODULE, "0003", "未找到访问的资源"),
            COMMON_USER_NOT_LOGIN_ERROR = ExceptionBuilderFactory.create(CommonApplicationException.class, DEFAULT_MODULE_LAYER_MODULE, "0004", "用户未登录"),
            COMMON_NO_PERMISSION_ERROR = ExceptionBuilderFactory.create(CommonApplicationException.class, DEFAULT_MODULE_LAYER_MODULE, "0005", "权限不足"),
            COMMON_PARAM_PARSE_ERROR = ExceptionBuilderFactory.create(CommonApplicationException.class, DEFAULT_MODULE_LAYER_MODULE, "0006", "参数解析异常：{0}"),
            COMMON_TOKEN_PARSE_ERROR = ExceptionBuilderFactory.create(CommonApplicationException.class, DEFAULT_MODULE_LAYER_MODULE, "0007", "用户令牌异常"),
            COMMON_SERVICE_NOT_AVALIABLE = ExceptionBuilderFactory.create(CommonApplicationException.class, DEFAULT_MODULE_LAYER_MODULE, "0008", "服务暂不可用，请稍后重试"),
            COMMON_USER_IS_KICKED_OUT = ExceptionBuilderFactory.create(CommonApplicationException.class, DEFAULT_MODULE_LAYER_MODULE, "0009", "本用户已被踢出，请重新登陆");

}
