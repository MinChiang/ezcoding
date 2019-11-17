package com.ezcoding.base.web.extend.spring.hystrix.concurrencyStrategy;

import com.ezcoding.base.web.extend.spring.hystrix.callable.CallableWrapper;
import com.google.common.collect.Lists;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariable;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableLifecycle;
import com.netflix.hystrix.strategy.properties.HystrixProperty;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-10-31 21:47
 */
public class ThreadLocalMaintainableConcurrencyStrategy extends HystrixConcurrencyStrategy {

    private HystrixConcurrencyStrategy delegate;

    private Collection<CallableWrapper> callableWrappers = Lists.newArrayList();

    public ThreadLocalMaintainableConcurrencyStrategy(HystrixConcurrencyStrategy delegate, Collection<CallableWrapper> callableWrappers) {
        this.delegate = delegate;
        if (CollectionUtils.isNotEmpty(callableWrappers)) {
            this.callableWrappers.addAll(callableWrappers);
        }
    }

    @Override
    public ThreadPoolExecutor getThreadPool(HystrixThreadPoolKey threadPoolKey, HystrixProperty<Integer> corePoolSize, HystrixProperty<Integer> maximumPoolSize, HystrixProperty<Integer> keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        return delegate.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    public ThreadPoolExecutor getThreadPool(HystrixThreadPoolKey threadPoolKey, HystrixThreadPoolProperties threadPoolProperties) {
        return delegate.getThreadPool(threadPoolKey, threadPoolProperties);
    }

    @Override
    public BlockingQueue<Runnable> getBlockingQueue(int maxQueueSize) {
        return delegate.getBlockingQueue(maxQueueSize);
    }

    @Override
    public <T> HystrixRequestVariable<T> getRequestVariable(HystrixRequestVariableLifecycle<T> rv) {
        return delegate.getRequestVariable(rv);
    }

    @Override
    public <T> Callable<T> wrapCallable(Callable<T> callable) {
        Callable<T> delegate = callable;

        for (CallableWrapper callableWrapper : callableWrappers) {
            delegate = callableWrapper.wrap(delegate);
        }

        return delegate;
    }

}
