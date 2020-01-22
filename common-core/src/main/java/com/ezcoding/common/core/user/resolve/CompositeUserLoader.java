package com.ezcoding.common.core.user.resolve;

import com.ezcoding.common.core.user.model.IUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-12-11 10:34
 */
public class CompositeUserLoader implements IUserLoadable {

    private List<IUserLoadable> loaders = new ArrayList<>(0);

    private CompositeUserLoader() {
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
        return loaders
                .stream()
                .map(IUserLoadable::load)
                .findFirst()
                .orElse(null);
    }

    /**
     * 注册用户加载器
     *
     * @param index  注册的位置
     * @param loader 需要注册的加载器
     */
    public void registerLoader(int index, IUserLoadable loader) {
        Optional
                .of(loader)
                .ifPresent(ld -> loaders.add(index, ld));
    }

}
