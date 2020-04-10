package com.ezcoding.api.account.management.bean.dto;

import com.ezcoding.api.account.management.bean.model.RoleStatusEnum;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

import static com.ezcoding.api.account.management.constant.AccountManagementConstants.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-25 9:20
 */
public class RoleCreateDTO {

    /**
     * 名称
     */
    @Length(max = ROLE_NAME_LENGTH_MAX, message = "{role.name}")
    @NotEmpty(message = "{role.name}")
    private String name;

    /**
     * 唯一编号
     */
    @Length(max = ROLE_CODE_LENGTH_MAX, message = "{role.code}")
    @NotEmpty(message = "{role.name}")
    private String code;

    /**
     * 描述
     */
    @Length(max = ROLE_COMMENT_LENGTH_MAX, message = "{role.comment}")
    private String comment;

    /**
     * 状态
     */
    private RoleStatusEnum status;

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
