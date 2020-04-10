package com.ezcoding.module.management.bean.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ezcoding.api.account.management.bean.model.RoleStatusEnum;
import com.ezcoding.common.mybatis.model.BaseModel;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-02 10:26
 */
@TableName("account_role")
public class Role extends BaseModel implements GrantedAuthority, Serializable {

    /**
     * 名称
     */
    private String name;

    /**
     * 唯一编号
     */
    private String code;

    /**
     * 描述
     */
    private String comment;

    /**
     * 状态
     */
    private RoleStatusEnum status;

    @Override
    public String getAuthority() {
        return this.code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public RoleStatusEnum getStatus() {
        return status;
    }

    public void setStatus(RoleStatusEnum status) {
        this.status = status;
    }

}