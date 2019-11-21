package com.ezcoding.common.web.filter;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-27 14:41
 */
public class FilterConstants {

    public static class Order {

        /**
         * druid数据源拦截器
         */
        public static final int DRUID_ORDER = 0;

        /**
         * Seata传播上下文xid拦截器
         */
        public static final int SEATA_XID_INJECT_ORDER = 100;

        /**
         * 全局变量context拦截器
         */
        public static final int APPLICATION_CONTEXT_HOLDER_ORDER = 200;

    }

    public static class Name {

        /**
         * 全局变量context拦截器
         */
        public static final String APPLICATION_CONTEXT_HOLDER_NAME = "applicationContextHolderFilter";

    }

}
