package com.ezcoding.api.account.management.bean.dto;

import com.ezcoding.api.account.management.bean.model.DepartmentStatusEnum;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

import static com.ezcoding.api.account.management.constant.AccountManagementConstants.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-24 17:49
 */
public class DepartmentCreateDTO {

    /**
     * 上级部门
     */
    private Long parentId;

    /**
     * 部门名称
     */
    @Length(max = DEPARTMENT_NAME_LENGTH_MAX, message = "{department.name}")
    @NotEmpty(message = "{department.name}")
    private String name;

    /**
     * 部门编号
     */
    @Length(max = DEPARTMENT_CODE_LENGTH_MAX, message = "{department.code}")
    @NotEmpty(message = "{department.name}")
    private String code;

    /**
     * 部门描述
     */
    @Length(max = DEPARTMENT_COMMENT_LENGTH_MAX, message = "{department.comment}")
    private String comment;

    /**
     * 部门状态
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

    public DepartmentStatusEnum getStatus() {
        return status;
    }

    public void setStatus(DepartmentStatusEnum status) {
        this.status = status;
    }

}
