package com.ezcoding.module.permission.controller;

import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.foundation.core.message.StandardResponseHttpEntity;
import com.ezcoding.common.foundation.core.message.StandardResponseMessageBuilder;
import com.ezcoding.common.security.configattribute.DynamicConfigAttribute;
import com.ezcoding.module.permission.bean.vo.DynamicConfigAttributeExpressionVO;
import com.ezcoding.module.permission.bean.vo.DynamicConfigAttributeVO;
import com.ezcoding.module.permission.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-28 17:31
 */
@Validated
@RestController
@RequestMapping("permissions")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    @PostMapping("{applicationName}")
    public StandardResponseHttpEntity<List<DynamicConfigAttributeExpressionVO>> loadPermissions(
            @PathVariable String applicationName,
            @RequestBody RequestMessage<HashSet<DynamicConfigAttributeVO>> requestMessage
    ) {
        //设置可推断的权限内容
        Set<DynamicConfigAttributeVO> payload = requestMessage.getPayload();
        if (payload.size() > 0) {
            Set<DynamicConfigAttribute> inferablePermissions = payload.stream().map(vo -> DynamicConfigAttribute.create(vo.getAttribute())).collect(Collectors.toSet());
            permissionService.registerInferablePermissions(applicationName, inferablePermissions);
        }

        //查找对应微服务接口权限
        Map<DynamicConfigAttribute, String> configAttributeStringMap = permissionService.loadPermissions(applicationName);
        List<DynamicConfigAttributeExpressionVO> list = new ArrayList<>(configAttributeStringMap.size());
        for (Map.Entry<DynamicConfigAttribute, String> entry : configAttributeStringMap.entrySet()) {
            DynamicConfigAttributeExpressionVO vo = new DynamicConfigAttributeExpressionVO();
            vo.setDynamicConfigAttribute(entry.getKey());
            vo.setExpression(entry.getValue());
            list.add(vo);
        }
        return StandardResponseMessageBuilder.<List<DynamicConfigAttributeExpressionVO>>ok().success(list).build();
    }

}
