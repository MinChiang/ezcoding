package com.ezcoding.auth.module.resource.controller;

import com.ezcoding.auth.module.resource.bean.model.ResourceBundle;
import com.ezcoding.auth.module.resource.service.IResourceService;
import com.ezcoding.base.web.extend.spring.resolver.CurrentUser;
import com.ezcoding.base.web.extend.spring.resolver.JsonParam;
import com.ezcoding.base.web.extend.spring.resolver.JsonResult;
import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.common.foundation.core.message.builder.RequestMessageBuilder;
import com.ezcoding.common.foundation.util.BeanUtils;
import com.ezcoding.sdk.account.user.api.IUser;
import com.ezcoding.sdk.account.user.api.UserFeignClient;
import com.ezcoding.sdk.auth.resource.bean.dto.ResourceBundleDTO;
import com.ezcoding.sdk.auth.resource.constant.AuthResourceConstants;
import io.seata.core.context.RootContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ezcoding.sdk.auth.resource.constant.AuthResourceConstants.HAS_RESOURCE;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-02 15:57
 */
@Validated
@RestController
@RequestMapping(AuthResourceConstants.RESOURCE_API)
public class ResourceController {

    @Autowired
    private IResourceService userService;

    @Autowired
    private UserFeignClient userFeignClient;

    /**
     * 判断用户是否含有某种资源
     *
     * @return 是否含有某种资源
     */
    @PostMapping(HAS_RESOURCE)
    @JsonResult
    public Boolean hasResource(@JsonParam @Validated ResourceBundleDTO resourceBundleDTO,
                               @CurrentUser IUser user) {
        ResourceBundle copy = BeanUtils.copy(resourceBundleDTO, ResourceBundle.class);
        return userService.hasResource(copy, user);
    }

    @PostMapping("test")
    @JsonResult
    public ResponseMessage test() {
        RootContext.bind("123");
        return userFeignClient.test(RequestMessageBuilder.create().build());
    }

}
