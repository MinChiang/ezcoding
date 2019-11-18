package com.ezcoding.common.user.resolve;

import com.ezcoding.common.user.model.IUser;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-12-11 10:34
 */
public class CurrentUserLoader implements IUserLoadable {

    private static final List<IUserLoadable> LOADERS = Lists.newArrayList();

    private CurrentUserLoader() {
    }

    public static CurrentUserLoader getInstance() {
        return UserResolverUtilsHolder.INSTANCE;
    }

//    /**
//     * 根据上下文获取当前的用户，使用代理对象
//     *
//     * @return 当前用户
//     */
//    public IUser currentUserWithProxy(IUser target) {
//        return new UserProxy(target == null ? createEmptyUser() : target);
//    }
//
//    /**
//     * 根据上下文获取当前的用户，不使用代理对象
//     *
//     * @return 当前用户
//     */
//    public IUser currentUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDTO userDTO = new UserDTO();
//        userDTO.setCode(authentication.getName());
//        userDTO.setAuthorities(authentication.getAuthorities());
//        return userDTO;
//    }
//
//    /**
//     * 获取空用户
//     *
//     * @return 空用户
//     */
//    private IUser createEmptyUser() {
//        return new UserDTO();
//    }

    @Override
    public IUser load() {
        return null;
    }

    public static void registerLoaders(){

    }

    private static final class UserResolverUtilsHolder {

        private static final CurrentUserLoader INSTANCE = new CurrentUserLoader();

    }

}
