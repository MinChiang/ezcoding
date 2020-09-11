package com.ezcoding.common.foundation.core.message;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public interface AppHead extends Head {

    /**
     * 获取分页信息
     *
     * @return 分页信息
     */
    PageInfo getPageInfo();

}
