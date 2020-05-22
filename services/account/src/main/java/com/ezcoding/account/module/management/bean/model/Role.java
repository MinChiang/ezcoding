package com.ezcoding.account.module.management.bean.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ezcoding.base.web.extend.mybatis.model.BaseModel;
import com.ezcoding.sdk.account.management.bean.model.RoleStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-02 10:26
 */
@Data
@TableName("account_role")
@EqualsAndHashCode(callSuper = true)
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

}