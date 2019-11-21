package com.ezcoding.common.web.filter;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-27 14:41
 */
public class FilterOrderConstants {

    /**
     * druid数据源拦截器
     */
    public static final int DRUID_ORDER = 0;

    /**
     * Seata传播上下文xid拦截器
     */
    public static final int SEATA_XID_INJECT = 100;

    /**
     * 全局变量context拦截器
     */
    public static final int APPLICATION_CONTEXT_HOLDER = 200;

}
