package com.ezcoding.account.extend.user;

import com.ezcoding.account.module.user.service.IUserService;
import com.ezcoding.base.web.extend.spring.security.authentication.IUserLoadable;
import com.ezcoding.common.foundation.util.BeanUtils;
import com.ezcoding.sdk.account.user.api.IUser;
import com.ezcoding.sdk.account.user.bean.dto.UserDTO;
import com.ezcoding.sdk.account.user.bean.dto.UserDetailResultDTO;
import com.ezcoding.sdk.account.user.constant.AccountUserApiConstants;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-30 23:26
 */
public class LocalUserLoader implements IUserLoadable {

    private IUserService userService;

    public LocalUserLoader(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public IUser load(IUser target) {
        UserDetailResultDTO userDetailedInfo = userService.getUserDetailedInfo(AccountUserApiConstants.USER_FIELD_DETAILS, target.getCode());
        return BeanUtils.copy(userDetailedInfo, UserDTO.class);
    }

    public IUserService getUserService() {
        return userService;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

}
