package com.ezcoding.sdk.facility.message.bean.dto;

import com.ezcoding.sdk.facility.message.constant.FacilityMessageValidationConstants;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-02-20 15:29
 */
@Data
public class MessageDTO {

    /**
     * 业务动作内容（json）
     */
    @NotNull(message = "{message.content}")
    private JsonNode content;

    /**
     * 主体类型
     */
    @NotEmpty(message = "{message.scene}", groups = {FacilityMessageValidationConstants.Record.class})
    private String scene;

    /**
     * 主体唯一主键
     */
    @NotNull(message = "{message.subjectId}", groups = {FacilityMessageValidationConstants.Record.class})
    private Long subjectId;

    /**
     * 系统号
     */
    @NotEmpty(message = "{message.applicationCode}", groups = {FacilityMessageValidationConstants.Record.class})
    private String applicationCode;

    /**
     * 创建人
     */
    private String creator;

}
