package com.ezcoding.base.web.config;

import com.ezcoding.base.web.extend.spring.hystrix.callable.ApplicationContextMaintainableCallable;
import com.ezcoding.base.web.extend.spring.hystrix.callable.CallableWrapper;
import com.ezcoding.base.web.extend.spring.hystrix.callable.SeataMaintainableCallable;
import com.ezcoding.base.web.extend.spring.hystrix.concurrencyStrategy.ThreadLocalMaintainableConcurrencyStrategy;
import com.google.common.collect.ImmutableList;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisher;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesStrategy;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-10-28 17:28
 */
@Configuration
public class HystrixConfig {

    @PostConstruct
    public void init() {
        HystrixEventNotifier eventNotifier = HystrixPlugins.getInstance().getEventNotifier();
        HystrixMetricsPublisher metricsPublisher = HystrixPlugins.getInstance().getMetricsPublisher();
        HystrixPropertiesStrategy propertiesStrategy = HystrixPlugins.getInstance().getPropertiesStrategy();
        HystrixCommandExecutionHook commandExecutionHook = HystrixPlugins.getInstance().getCommandExecutionHook();
        HystrixConcurrencyStrategy concurrencyStrategy = HystrixPlugins.getInstance().getConcurrencyStrategy();

        HystrixPlugins.reset();

        ThreadLocalMaintainableConcurrencyStrategy threadLocalMaintainableConcurrencyStrategy = threadLocalMaintainableConcurrencyStrategy(concurrencyStrategy);
        HystrixPlugins.getInstance().registerConcurrencyStrategy(threadLocalMaintainableConcurrencyStrategy);
        HystrixPlugins.getInstance().registerEventNotifier(eventNotifier);
        HystrixPlugins.getInstance().registerMetricsPublisher(metricsPublisher);
        HystrixPlugins.getInstance().registerPropertiesStrategy(propertiesStrategy);
        HystrixPlugins.getInstance().registerCommandExecutionHook(commandExecutionHook);
    }

    private ThreadLocalMaintainableConcurrencyStrategy threadLocalMaintainableConcurrencyStrategy(HystrixConcurrencyStrategy concurrencyStrategy) {
        List<CallableWrapper> wrappers =
                ImmutableList
                        .<CallableWrapper>builder()
                        .add(ApplicationContextMaintainableCallable::new)
                        .add(SeataMaintainableCallable::new)
                        .build();
        return new ThreadLocalMaintainableConcurrencyStrategy(concurrencyStrategy, wrappers);
    }

}
