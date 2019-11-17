package com.ezcoding.sdk.facility.area.bean.dto;

import com.ezcoding.sdk.facility.area.constant.FacilityAreaValidationConstants;
import com.ezcoding.sdk.facility.area.bean.model.LevelEnum;
import com.ezcoding.sdk.facility.area.constant.FacilityAreaConstants;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-11-19 14:43
 */
@Data
public class AreaSearchDTO {

    /**
     * 主键
     */
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
     * 邮政编码
     */
    private Integer zipCode;

    /**
     * 名称
     */
    @Length(min = FacilityAreaConstants.NAME_LENGTH_MIN,
            max = FacilityAreaConstants.NAME_LENGTH_MAX,
            groups = FacilityAreaValidationConstants.SearchArea.class)
    private String name;

    /**
     * 简称
     */
    @Length(min = FacilityAreaConstants.SHORT_NAME_LENGTH_MIN,
            max = FacilityAreaConstants.SHORT_NAME_LENGTH_MAX,
            groups = FacilityAreaValidationConstants.SearchArea.class)
    private String shortName;

    /**
     * 拼音
     */
    @Length(min = FacilityAreaConstants.PING_YIN_LENGTH_MIN,
            max = FacilityAreaConstants.PING_YIN_NAME_LENGTH_MAX,
            groups = FacilityAreaValidationConstants.SearchArea.class)
    private String pinyin;

}
