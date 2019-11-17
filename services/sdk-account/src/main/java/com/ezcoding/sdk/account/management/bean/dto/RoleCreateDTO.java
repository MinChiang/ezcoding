package com.ezcoding.sdk.account.management.bean.dto;

import com.ezcoding.sdk.account.management.bean.model.RoleStatusEnum;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

import static com.ezcoding.sdk.account.management.constant.AccountManagementConstants.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-25 9:20
 */
@Data
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

}
