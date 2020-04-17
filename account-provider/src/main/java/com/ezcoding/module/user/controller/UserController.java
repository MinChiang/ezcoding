package com.ezcoding.module.user.controller;

import com.ezcoding.common.core.user.model.IUser;
import com.ezcoding.common.core.user.model.UserIdentification;
import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.common.foundation.core.message.StandardResponseHttpEntity;
import com.ezcoding.common.foundation.core.message.StandardResponseMessageBuilder;
import com.ezcoding.common.web.resolver.CurrentUser;
import com.ezcoding.common.web.resolver.JsonParam;
import com.ezcoding.common.web.resolver.JsonResult;
import com.ezcoding.module.user.bean.assembler.UserIdentificationUserAssembler;
import com.ezcoding.module.user.bean.model.User;
import com.ezcoding.module.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-09-05 23:05
 */
@Validated
@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 校验用户是否存在(失焦自动触发)
     *
     * @param userIdentification 用户信息
     * @return 校验用户是否存在
     */
    @GetMapping("exist")
    @JsonResult
    public boolean exist(UserIdentification userIdentification) {
        // 校验唯一信息
        return userService.exist(UserIdentificationUserAssembler.to(userIdentification));
    }

    /**
     * 踢出用户
     *
     * @param user    被踢出的用户
     * @param handler 操作人
     */
    @DeleteMapping("kickOut")
    public void kickOut(@JsonParam("codes") String user,
                        @CurrentUser @NotNull(message = "{user.code}") IUser handler) {
        String address = handler.getAddress();
        userService.kickOut(user);
    }

    @GetMapping("test")
    @ResponseBody
    public StandardResponseHttpEntity<User> test() {
        StandardResponseHttpEntity<User> cu =
                StandardResponseMessageBuilder
                        .<User>ok()
                        .success(User.create().code("cu"))
                        .build();
        return cu;
    }

    @DeleteMapping("test2")
    @ResponseBody
    public StandardResponseHttpEntity<User> test2(@RequestBody RequestMessage<User> requestMessage) {
        User payload = requestMessage.getPayload();
        System.out.println(payload);
        return new StandardResponseHttpEntity<>(new ResponseMessage<>(payload), HttpStatus.OK);
    }

}
