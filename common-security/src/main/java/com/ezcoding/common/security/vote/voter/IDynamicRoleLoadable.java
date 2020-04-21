package com.ezcoding.common.security.vote.voter;

import org.springframework.security.access.ConfigAttribute;

import java.io.IOException;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-21 14:09
 */
public interface IDynamicRoleLoadable {

    /**
     * 根据系统号加载对应的权限
     *
     * @return 对应的系统权限
     */
    Map<ConfigAttribute, String> load() throws IOException;

}
