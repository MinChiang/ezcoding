package com.ezcoding.common.web.user;

import com.ezcoding.common.core.user.model.IUserIdentifiable;

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

    public CompositeUserLoader(List<IUserLoadable> loaders) {
        Optional
                .ofNullable(loaders)
                .ifPresent(lds -> this.loaders.addAll(lds));
    }

    @Override
    public IUserIdentifiable load() {
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
