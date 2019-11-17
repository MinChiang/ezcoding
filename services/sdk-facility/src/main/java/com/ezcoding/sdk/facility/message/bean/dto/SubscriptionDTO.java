package com.ezcoding.sdk.facility.message.bean.dto;

import com.ezcoding.sdk.facility.message.constant.FacilityMessageValidationConstants;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-03-10 11:00
 */
@Data
public class SubscriptionDTO {

    /**
     * 订阅主键
     */
    private Long id;

    /**
     * 订阅物品类型
     */
    @NotNull(message = "{message.scene}", groups = {FacilityMessageValidationConstants.Subscribe.class})
    private String scene;

    /**
     * 订阅物品id
     */
    @NotNull(message = "{message.subjectId}", groups = {FacilityMessageValidationConstants.Subscribe.class})
    private Long subjectId;

    /**
     * 系统号
     */
    @NotEmpty(message = "{message.applicationCode}", groups = {FacilityMessageValidationConstants.Subscribe.class})
    private String applicationCode;

    /**
     * 订阅人
     */
    private String subscriber;

}
