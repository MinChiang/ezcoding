package com.ezcoding.common.foundation.core.constant;

import com.ezcoding.common.foundation.core.application.ModuleNameable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-29 10:07
 */
public class GlobalConstants {

    /**
     * 服务应用级别层面参数
     */
    public static final class Application {

        /**
         * 微服务序号最大长度，最大为范围为0到63
         */
        public static final int APPLICATION_CODE_BIT_LENGTH = 6;

        /**
         * 系统码长度，因为服务序号为0到63，因此长度最长为2位
         */
        public static final int APPLICATION_CODE_LENGTH = String.valueOf((1 << APPLICATION_CODE_BIT_LENGTH) - 1).length();

        /**
         * 模块码长度
         */
        public static final int MODULE_CODE_LENGTH = ModuleNameable.MODULE_CODE_LENGTH;

        /**
         * 业务码长度
         */
        public static final int DETAIL_CODE_LENGTH = ModuleNameable.FUNCTION_CODE_LENGTH;

        /**
         * 系统基本模块
         */
        public static final String COMMON = "common";

        /**
         * 网关模块
         */
        public static final String GATEWAY = "gateway";

        /**
         * 统一鉴权微服务模块
         */
        public static final String AUTH = "auth";

        /**
         * 基础组建模块
         */
        public static final String FACILITY = "facility";

        /**
         * 用户微服务模块
         */
        public static final String USER = "user";

        /**
         * 系统基本模块
         */
        public static final int COMMON_APPLICATION_CODE = 0;

        /**
         * 网关模块
         */
        public static final int GATEWAY_APPLICATION_CODE = 1;

        /**
         * 统一鉴权微服务模块
         */
        public static final int AUTH_APPLICATION_CODE = 2;

        /**
         * 基础组建模块
         */
        public static final int FACILITY_APPLICATION_CODE = 3;

        /**
         * 用户微服务模块
         */
        public static final int USER_APPLICATION_CODE = 4;

        /**
         * 服务名 <--> 模块id映射关系
         */
        public static final Map<String, Integer> SERVICE_NAME_APPLICATION_CODE_MAPPING = new HashMap<>();

        static {
            SERVICE_NAME_APPLICATION_CODE_MAPPING.put(COMMON, COMMON_APPLICATION_CODE);
            SERVICE_NAME_APPLICATION_CODE_MAPPING.put(GATEWAY, GATEWAY_APPLICATION_CODE);
            SERVICE_NAME_APPLICATION_CODE_MAPPING.put(AUTH, AUTH_APPLICATION_CODE);
            SERVICE_NAME_APPLICATION_CODE_MAPPING.put(FACILITY, FACILITY_APPLICATION_CODE);
            SERVICE_NAME_APPLICATION_CODE_MAPPING.put(USER, USER_APPLICATION_CODE);
        }
    }

}
