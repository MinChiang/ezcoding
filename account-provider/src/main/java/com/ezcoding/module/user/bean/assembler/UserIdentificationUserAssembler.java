package com.ezcoding.module.user.bean.assembler;

import com.ezcoding.common.core.user.model.UserIdentification;
import com.ezcoding.module.user.bean.model.User;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-13 10:22
 */
public class UserIdentificationUserAssembler {

    public static User to(UserIdentification userIdentification) {
        User user = new User();
        user.setCode(userIdentification.getCode());
        user.setAccount(userIdentification.getAccount());
        user.setPhone(userIdentification.getPhone());
        user.setEmail(userIdentification.getEmail());
        return user;
    }

}
