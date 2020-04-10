package com.ezcoding.module.management.bean.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ezcoding.api.account.management.bean.model.DepartmentStatusEnum;
import com.ezcoding.common.mybatis.model.BaseModel;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-02 10:27
 */
@TableName("account_department")
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public DepartmentStatusEnum getStatus() {
        return status;
    }

    public void setStatus(DepartmentStatusEnum status) {
        this.status = status;
    }

}
