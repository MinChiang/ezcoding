package com.ezcoding.sdk.account.user.api;

import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.sdk.account.user.bean.dto.UserDetailResultDTO;
import com.ezcoding.sdk.account.user.constant.AccountUserApiConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-10-17 22:16
 */
@FeignClient(name = "${service.account:127.0.0.1}")
@RequestMapping(AccountUserApiConstants.USER_API)
public interface UserFeignClient {

    /**
     * 根据code查找对应的用户
     *
     * @param requestMessage 请求信息
     * @return 用户信息
     */
    @PostMapping(AccountUserApiConstants.GET_USER_INFO)
    ResponseMessage<UserDetailResultDTO> getUserInfo(RequestMessage<?> requestMessage);

    /**
     * 批量查询用户信息
     *
     * @param requestMessage 请求信息
     * @return 用户信息
     */
    @PostMapping(AccountUserApiConstants.LIST_USERS_INFO)
    ResponseMessage<List<UserDetailResultDTO>> listUsersInfo(RequestMessage<?> requestMessage);

    @PostMapping("test")
    ResponseMessage test(RequestMessage<?> requestMessage);

}
