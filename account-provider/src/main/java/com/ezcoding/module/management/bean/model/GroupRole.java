package com.ezcoding.module.management.bean.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ezcoding.common.mybatis.model.BaseModel;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-25 11:03
 */
@TableName("account_group_role")
public class GroupRole extends BaseModel implements Serializable {

    /**
     * 群组主键
     */
    private Long groupId;

    /**
     * 角色主键
     */
    private Long roleId;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

}
