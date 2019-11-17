package com.ezcoding.web.resolver;

import java.lang.annotation.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-12-03 14:15
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface JsonPage {

    /**
     * 是否返回总条数
     *
     * @return 是否返回总条数
     */
    boolean searchCount() default false;

    /**
     * 默认当前页，此项一般无需改动
     *
     * @return 默认当前页码
     */
    int defaultCurrentPage() default PageConstants.CURRENT_PAGE;

    /**
     * 默认每页条数
     *
     * @return 默认每页条数
     */
    int defaultPageSize() default PageConstants.PAGE_SIZE;

}