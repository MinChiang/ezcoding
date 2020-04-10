package com.ezcoding.module.management.bean.model;

import com.ezcoding.api.account.management.bean.model.PositionStatusEnum;
import com.ezcoding.common.mybatis.model.BaseModel;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-02 11:47
 */
public class Position extends BaseModel implements Serializable {

    /**
     * 所属部门
     */
    private Long departmentId;

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
    private PositionStatusEnum status;

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
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

    public PositionStatusEnum getStatus() {
        return status;
    }

    public void setStatus(PositionStatusEnum status) {
        this.status = status;
    }

}
