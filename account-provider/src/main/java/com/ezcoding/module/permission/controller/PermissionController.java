package com.ezcoding.module.permission.controller;

import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.foundation.core.message.StandardResponseHttpEntity;
import com.ezcoding.common.foundation.core.message.StandardResponseMessageBuilder;
import com.ezcoding.common.security.configattribute.DynamicConfigAttribute;
import com.ezcoding.module.permission.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

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
    public StandardResponseHttpEntity<Map<ConfigAttribute, String>> loadPermissions(@PathVariable String applicationName,
                                                                                    @RequestBody RequestMessage<Set<DynamicConfigAttribute>> requestMessage
    ) {
        Set<DynamicConfigAttribute> payload = requestMessage.getPayload();
        Map<ConfigAttribute, String> configAttributeStringMap = permissionService.loadPermissions(applicationName);
        return StandardResponseMessageBuilder.<Map<ConfigAttribute, String>>ok().success(configAttributeStringMap).build();
    }

}
