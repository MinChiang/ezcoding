package com.ezcoding.facility.module.message.bean.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-02-19 9:38
 */
@Data
@TableName("facility_message")
public class Message implements Serializable {

    /**
     * 主键
     */
    @TableId
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
     * 业务动作内容（json）
     */
    private JsonNode content;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 动作发生时间
     */
    private Date createTime;

}
