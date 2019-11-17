package com.ezcoding.account.module.management.bean.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ezcoding.base.web.extend.mybatis.model.BaseModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-25 11:03
 */
@Data
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

}
