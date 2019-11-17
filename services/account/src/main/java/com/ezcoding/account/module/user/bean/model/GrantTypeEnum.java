package com.ezcoding.account.module.user.bean.model;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.ezcoding.common.foundation.util.EnumMappableUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-23 17:58
 */
public enum GrantTypeEnum {

    /**
     * 客户端模式
     */
    CLIENT_CREDENTIALS(0),

    /**
     * 密码模式
     */
    PASSWORD(1),

    /**
     * 授权码模式
     */
    AUTHORIZATION_CODE(2),

    /**
     * 令牌刷新
     */
    REFRESH_TOKEN(3),

    /**
     * 简化模式
     */
    IMPLICIT(4);

    /**
     * 标志
     */
    @EnumValue
    @JsonValue
    private final int id;

    GrantTypeEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @JsonCreator
    public static GrantTypeEnum from(int i) {
        return EnumMappableUtils.map(i, GrantTypeEnum.class);
    }
}
