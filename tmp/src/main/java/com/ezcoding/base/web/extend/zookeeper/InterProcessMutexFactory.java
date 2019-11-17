package com.ezcoding.base.web.extend.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-18 17:20
 */
public class InterProcessMutexFactory {

    private CuratorFramework client;
    private String applicationName;

    public InterProcessMutexFactory(CuratorFramework curatorFramework, String applicationName) {
        this.client = curatorFramework;
        this.applicationName = applicationName;
    }

    public final InterProcessMutex createInterProcessMutex(String moduleName, String businessKey) {
        return new InterProcessMutex(client, this.applicationName + "/" + moduleName + "/" + businessKey);
    }

}
