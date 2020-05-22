package com.ezcoding.common.security.vote.voter;

import com.ezcoding.common.security.configattribute.DynamicConfigAttribute;
import org.apache.commons.collections.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-28 15:39
 */
public class DelegateDynamicRoleLoader implements DynamicRoleLoadable {

    private List<DynamicRoleLoadable> loadables = new ArrayList<>();

    public DelegateDynamicRoleLoader(List<DynamicRoleLoadable> loadables) {
        this.addLoaders(loadables);
    }

    public void addLoaders(List<DynamicRoleLoadable> loadables) {
        if (CollectionUtils.isEmpty(loadables)) {
            return;
        }
        this.loadables.addAll(loadables);
    }

    @Override
    public Map<DynamicConfigAttribute, String> load() throws IOException {
        Map<DynamicConfigAttribute, String> result = new HashMap<>();
        if (CollectionUtils.isEmpty(loadables)) {
            return result;
        }

        for (DynamicRoleLoadable loadable : this.loadables) {
            result.putAll(loadable.load());
        }
        return result;
    }

}
