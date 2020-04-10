package com.ezcoding.module.user.controller;

import com.ezcoding.common.core.user.model.IUser;
import com.ezcoding.common.foundation.util.BeanUtils;
import com.ezcoding.common.web.resolver.CurrentUser;
import com.ezcoding.common.web.resolver.JsonParam;
import com.ezcoding.common.web.resolver.JsonResult;
import com.ezcoding.api.account.user.bean.dto.UserExistDTO;
import com.ezcoding.module.user.bean.model.User;
import com.ezcoding.module.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-09-05 23:05
 */
@Validated
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 校验用户是否存在(失焦自动触发)
     *
     * @param userExistDTO 用户信息
     * @return 校验用户是否存在
     */
    @PostMapping("exist")
    @JsonResult
    public boolean exist(@JsonParam UserExistDTO userExistDTO) {
        User user = BeanUtils.copy(userExistDTO, User.class);
        // 校验唯一信息
        return userService.exist(user);
    }

    /**
     * 踢出用户
     *
     * @param user    被踢出的用户
     * @param handler 操作人
     */
    @PostMapping("kickOut")
    @JsonResult
    public void kickOut(@JsonParam("codes") String user,
                        @CurrentUser @NotNull(message = "{user.code}") IUser handler) {
        userService.kickOut(user);
    }

}
