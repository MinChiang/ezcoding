package com.ezcoding.common.web.user;

import com.ezcoding.common.core.user.UserIdentifiable;
import com.ezcoding.common.core.user.UserLoadable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-12-11 10:34
 */
public class CompositeUserLoader implements UserLoadable {

    private final List<UserLoadable> loaders = new ArrayList<>();

    public CompositeUserLoader(List<UserLoadable> loaders) {
        Optional
                .ofNullable(loaders)
                .ifPresent(this.loaders::addAll);
    }

    @Override
    public UserIdentifiable load() {
        return loaders
                .stream()
                .map(UserLoadable::load)
                .findFirst()
                .orElse(null);
    }

    /**
     * 注册用户加载器
     *
     * @param index  注册的位置
     * @param loader 需要注册的加载器
     */
    public void registerLoader(int index, UserLoadable loader) {
        Optional
                .of(loader)
                .ifPresent(ld -> loaders.add(index, ld));
    }

}
