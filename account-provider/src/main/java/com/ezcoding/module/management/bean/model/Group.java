package com.ezcoding.module.management.bean.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ezcoding.api.account.management.bean.model.GroupStatusEnum;
import com.ezcoding.common.mybatis.model.BaseModel;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-02 10:25
 */
@TableName("account_group")
public class Group extends BaseModel implements Serializable {

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String comment;

    /**
     * 状态
     */
    private GroupStatusEnum status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public GroupStatusEnum getStatus() {
        return status;
    }

    public void setStatus(GroupStatusEnum status) {
        this.status = status;
    }

}
