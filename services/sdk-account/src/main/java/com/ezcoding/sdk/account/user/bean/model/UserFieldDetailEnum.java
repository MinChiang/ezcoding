package com.ezcoding.sdk.account.user.bean.model;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.ezcoding.common.foundation.util.EnumMappableUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 查询字段
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-12-06 16:20
 */
public enum UserFieldDetailEnum {

    /**
     * 主键
     */
    ID("id"),

    /**
     * 用户编号
     */
    CODE("code"),

    /**
     * 账号
     */
    ACCOUNT("account"),

    /**
     * 用户名
     */
    NAME("name"),

    /**
     * 性别
     */
    GENDER("gender"),

    /**
     * 手机号码
     */
    PHONE("phone"),

    /**
     * 邮箱
     */
    EMAIL("email"),

    /**
     * 地址
     */
    ADDRESS("address"),

    /**
     * 生日
     */
    BIRTHDAY("birthday"),

    /**
     * 入职日期
     */
    HIRE_DATE("hireDate"),

    /**
     * 头像，为标准的http地址
     */
    PROFILE_PHOTO("profilephoto"),

    /**
     * 用户描述
     */
    DESCRIPTION("description"),

    /**
     * 是否已经实名认证
     */
    VERIFIED("verified"),

    /**
     * 区域
     */
    AREA("area"),

    /**
     * 状态
     */
    STATUS("status"),

    /**
     * 创建人
     */
    CREATOR("creator"),

    /**
     * 创建时间
     */
    CREATE_TIME("createTime"),

    /**
     * 更新人
     */
    MODIFIER("modifier"),

    /**
     * 更新时间
     */
    MODIFY_TIME("modifyTime"),

    /**
     * 是否被删除
     */
    DELETED("deleted");

    @EnumValue
    @JsonValue
    private final String field;

    UserFieldDetailEnum(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }

    @JsonCreator
    public static UserFieldDetailEnum from(String i) {
        return EnumMappableUtils.map(i, UserFieldDetailEnum.class);
    }

}
