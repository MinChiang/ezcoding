package com.ezcoding.sdk.account.management.bean.dto;

import com.ezcoding.sdk.account.management.bean.model.GroupStatusEnum;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

import static com.ezcoding.sdk.account.management.constant.AccountManagementConstants.GROUP_COMMENT_LENGTH_MAX;
import static com.ezcoding.sdk.account.management.constant.AccountManagementConstants.GROUP_NAME_LENGTH_MAX;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-24 21:19
 */
@Data
public class GroupCreateDTO {

    /**
     * 名称
     */
    @Length(max = GROUP_NAME_LENGTH_MAX, message = "{group.name}")
    @NotEmpty(message = "{group.name}")
    private String name;

    /**
     * 描述
     */
    @Length(max = GROUP_COMMENT_LENGTH_MAX, message = "{group.comment}")
    private String comment;

    /**
     * 状态
     */
    private GroupStatusEnum status;

}
