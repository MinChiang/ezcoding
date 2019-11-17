package com.ezcoding.facility.module.area.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezcoding.base.web.extend.spring.resolver.JsonPage;
import com.ezcoding.base.web.extend.spring.resolver.JsonParam;
import com.ezcoding.base.web.extend.spring.resolver.JsonResult;
import com.ezcoding.common.foundation.util.BeanUtils;
import com.ezcoding.facility.module.area.bean.model.Area;
import com.ezcoding.facility.module.area.service.IAreaService;
import com.ezcoding.sdk.facility.area.bean.dto.AreaDTO;
import com.ezcoding.sdk.facility.area.bean.dto.AreaSearchDTO;
import com.ezcoding.sdk.facility.area.constant.FacilityAreaApiConstants;
import com.ezcoding.sdk.facility.area.constant.FacilityAreaValidationConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.Set;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-11-19 14:38
 */
@Slf4j
@RestController
@RequestMapping(FacilityAreaApiConstants.AREA_API)
@Validated
public class AreaController {

    @Autowired
    private IAreaService areaService;

    /**
     * 检索地区信息
     *
     * @param areaSearchDTO 检索条件
     * @param page          分页信息
     * @return 检索的地区信息
     */
    @JsonResult
    @PostMapping(FacilityAreaApiConstants.SEARCH_AREA)
    public Page<Area> searchArea(@JsonParam @Validated(FacilityAreaValidationConstants.SearchArea.class) AreaSearchDTO areaSearchDTO,
                                 @JsonPage Page<Area> page) {
        return areaService.searchArea(areaSearchDTO, page);
    }

    /**
     * 根据主键批量搜索地区信息
     *
     * @param ids 主键
     * @return 地区信息
     */
    @JsonResult
    @PostMapping(FacilityAreaApiConstants.SEARCH_AREA_BY_IDS)
    public Collection<AreaDTO> searchAreaByIds(@JsonParam @NotEmpty(message = "{area.id}") Set<Long> ids) {
        Collection<Area> areas = areaService.searchAreaByIds(ids);
        if (CollectionUtils.isEmpty(areas)) {
            return Lists.newArrayList();
        }
        return BeanUtils.copy(areas, AreaDTO.class);
    }

}
