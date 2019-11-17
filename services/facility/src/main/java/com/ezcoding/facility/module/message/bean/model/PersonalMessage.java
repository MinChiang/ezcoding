package com.ezcoding.facility.module.message.bean.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-02-23 15:14
 */
@TableName("facility_personal_message")
@Data
public class PersonalMessage implements Serializable {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 私信内容
     */
    private String content;

    /**
     * 收信人
     */
    private String receiver;

    /**
     * 寄信人
     */
    private String sender;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 是否已读
     */
    private Boolean alreadyRead;

    /**
     * 是否已被删除
     */
    @TableLogic
    private Boolean deleted;

}
