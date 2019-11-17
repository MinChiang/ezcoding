package com.ezcoding.base.web.extend.spring.security.authentication;

import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.common.foundation.core.message.builder.RequestMessageBuilder;
import com.ezcoding.common.foundation.util.BeanUtils;
import com.ezcoding.sdk.account.user.api.IUser;
import com.ezcoding.sdk.account.user.api.UserFeignClient;
import com.ezcoding.sdk.account.user.bean.dto.UserDTO;
import com.ezcoding.sdk.account.user.bean.dto.UserDetailResultDTO;
import com.ezcoding.sdk.account.user.constant.AccountUserApiConstants;
import com.google.common.collect.ImmutableMap;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-30 22:49
 */
public class FeignUserLoader implements IUserLoadable {

    private UserFeignClient userFeignClient;

    public FeignUserLoader(UserFeignClient userFeignClient) {
        this.userFeignClient = userFeignClient;
    }

    @Override
    public IUser load(IUser target) {
        ResponseMessage<UserDetailResultDTO> userInfo = userFeignClient.getUserInfo(
                RequestMessageBuilder
                        .create(
                                ImmutableMap
                                        .builder()
                                        .put("details", AccountUserApiConstants.USER_FIELD_DETAILS)
                                        .put("code", target.getCode())
                                        .build()
                        )
                        .build()
        );
        UserDetailResultDTO payload = userInfo.getPayload();
        return BeanUtils.copy(payload, UserDTO.class);
    }

    public UserFeignClient getUserFeignClient() {
        return userFeignClient;
    }

    public void setUserFeignClient(UserFeignClient userFeignClient) {
        this.userFeignClient = userFeignClient;
    }

}
