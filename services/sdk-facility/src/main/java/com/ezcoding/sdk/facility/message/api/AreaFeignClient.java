package com.ezcoding.sdk.facility.message.api;

import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.sdk.facility.area.bean.dto.AreaDTO;
import com.ezcoding.sdk.facility.area.constant.FacilityAreaApiConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.Set;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-10-17 22:16
 */
@FeignClient(name = "${service.facility:127.0.0.1}")
@RequestMapping(FacilityAreaApiConstants.AREA_API)
public interface AreaFeignClient {

    /**
     * 根据主键批量搜索地区信息
     *
     * @param ids 主键
     * @return 地区信息
     */
    @PostMapping(FacilityAreaApiConstants.SEARCH_AREA_BY_IDS)
    ResponseMessage<Collection<AreaDTO>> searchAreaByIds(RequestMessage<Set<Long>> ids);

}
