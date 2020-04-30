package com.ezcoding.common.security.vote.voter;

import com.ezcoding.common.security.configattribute.DynamicConfigAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-21 15:23
 */
public class DynamicSecheduledTriggerProxy implements IDynamicRoleLoadable {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicSecheduledTriggerProxy.class);

    /**
     * 加载器
     */
    private IDynamicRoleLoadable loadable;

    /**
     * 投票器
     */
    private DynamicRoleVoter dynamicRoleVoter;

    /**
     * 执行服务
     */
    private ScheduledExecutorService executorService;

    public DynamicSecheduledTriggerProxy(IDynamicRoleLoadable loadable, DynamicRoleVoter dynamicRoleVoter) {
        if (loadable == null || dynamicRoleVoter == null) {
            throw new IllegalArgumentException("loadable和dynamicRoleVoter不能为空");
        }
        this.loadable = loadable;
        this.dynamicRoleVoter = dynamicRoleVoter;
    }

    /**
     * 配置
     *
     * @param enableAutoRefresh 是否打开自动刷新
     * @param refreshSeconds    自动刷新秒数
     */
    public synchronized DynamicSecheduledTriggerProxy config(boolean enableAutoRefresh, Long refreshSeconds) {
        //开启和关闭定时任务
        if (enableAutoRefresh) {
            this.executorService = Executors.newSingleThreadScheduledExecutor();
            this.executorService.scheduleWithFixedDelay(
                    () -> {
                        try {
                            this.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    },
                    0,
                    refreshSeconds,
                    TimeUnit.SECONDS
            );
        } else {
            if (this.executorService != null) {
                executorService.shutdown();
            }
        }
        return this;
    }

    @Override
    public Map<DynamicConfigAttribute, String> load() throws IOException {
        Map<DynamicConfigAttribute, String> content = this.loadable.load();
        for (Map.Entry<DynamicConfigAttribute, String> entry : content.entrySet()) {
            this.dynamicRoleVoter.addExpressionHandler(entry.getKey(), entry.getValue());
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("加载权限：{} : {}", entry.getKey(), entry.getValue());
            }
        }
        return content;
    }

}
