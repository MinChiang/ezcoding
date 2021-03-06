package com.ezcoding.account.module.management.bean.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ezcoding.base.web.extend.mybatis.model.BaseModel;
import com.ezcoding.sdk.account.management.bean.model.DepartmentStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-02 10:27
 */
@Data
@TableName("account_department")
@EqualsAndHashCode(callSuper = true)
public class Department extends BaseModel implements Serializable {

    /**
     * 上级部门
     */
    private Long parentId;

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
     * 所在层级
     */
    private Integer level;

    /**
     * 是否为叶子层级
     */
    @TableField(value = "is_leaf")
    private Boolean leaf;

    /**
     * 节点全路径，使用","分割
     */
    private String path;

    /**
     * 所属组别id
     */
    private Long groupId;

    /**
     * 状态
     */
    private DepartmentStatusEnum status;

}
