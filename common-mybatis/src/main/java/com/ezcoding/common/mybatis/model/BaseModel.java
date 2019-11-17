package com.ezcoding.common.mybatis.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * mybatis实体自动处理类，本类中包含所有表中重要字段
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-09-30 9:49
 */
@Data
public abstract class BaseModel implements Serializable {

    /**
     * 主键
     */
    @TableId
    protected Long id;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    protected String creator;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    protected Date createTime;

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected String modifier;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected Date modifyTime;

}
