package com.ezcoding.sdk.facility.message.bean.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.Date;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-02-20 14:40
 */
@Data
public class MessageSearchDTO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 系统号
     */
    private String applicationCode;

    /**
     * 场景号
     */
    private String scene;

    /**
     * 主体唯一主键
     */
    private Long subjectId;

    /**
     * 发生动作的用户
     */
    private String user;

    /**
     * 动作发生时间
     */
    private Date createTime;

    /**
     * 业务动作类型
     */
    private JsonNode content;

    /**
     * 是否已读
     */
    private Boolean alreadyRead;

}
