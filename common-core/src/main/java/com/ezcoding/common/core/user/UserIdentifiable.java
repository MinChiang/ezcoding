package com.ezcoding.common.core.user;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-10-11 0:48
 */
public interface UserIdentifiable {

    /**
     * 获取用户id
     *
     * @return 用户id
     */
    Long getId();

    /**
     * 是否有唯一用户鉴别属性
     *
     * @return 是否有唯一用户鉴别属性
     */
    default boolean identifiable() {
        return getId() != null;
    }

}
