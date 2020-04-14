package com.ezcoding.module.user.bean.assembler;

import com.ezcoding.api.account.user.bean.dto.UserModificationDTO;
import com.ezcoding.module.user.bean.model.User;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-13 10:45
 */
public class UserModificationDTOUserAssembler {

    public static User to(UserModificationDTO userModificationDTO) {
        User user = new User();
        user.setCode(userModificationDTO.getCode());
        user.setAccount(userModificationDTO.getAccount());
        user.setName(userModificationDTO.getName());
        user.setGender(userModificationDTO.getGender());
        user.setPhone(userModificationDTO.getPhone());
        user.setEmail(userModificationDTO.getEmail());
        user.setAddress(userModificationDTO.getAddress());
        user.setBirthday(userModificationDTO.getBirthday());
        user.setDescription(userModificationDTO.getDescription());
        user.setProvince(userModificationDTO.getProvince());
        user.setCity(userModificationDTO.getCity());
        return user;
    }

}
