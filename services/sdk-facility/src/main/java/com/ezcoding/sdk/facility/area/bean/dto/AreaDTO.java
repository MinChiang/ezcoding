package com.ezcoding.sdk.facility.area.bean.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.ezcoding.sdk.facility.area.bean.model.LevelEnum;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-31 20:17
 */
@Data
public class AreaDTO {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 父节点id
     */
    private Long parentId;

    /**
     * 层级
     */
    private LevelEnum level;

    /**
     * 行政代码
     */
    private Long areaCode;

    /**
     * 邮政编码
     */
    private Integer zipCode;

    /**
     * 区号
     */
    private String cityCode;

    /**
     * 名称
     */
    private String name;

    /**
     * 简称
     */
    private String shortName;

    /**
     * 组合名
     */
    private String mergerName;

    /**
     * 拼音
     */
    private String pinyin;

    /**
     * 经度
     */
    private BigDecimal lng;

    /**
     * 纬度
     */
    private BigDecimal lat;

}
