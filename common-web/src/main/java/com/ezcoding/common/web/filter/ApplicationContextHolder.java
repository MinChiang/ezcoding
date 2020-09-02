package com.ezcoding.common.web.filter;

import java.util.HashMap;
import java.util.Map;

/**
 * 上下文默认实现
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-10-24 10:44
 */
public class ApplicationContextHolder implements ApplicationContext {

    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = new InheritableThreadLocal<Map<String, Object>>() {
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<>(0);
        }
    };

    private ApplicationContextHolder() {
    }

    /**
     * 获取对象实例
     *
     * @return 上下文对象实例
     */
    public static ApplicationContextHolder getInstance() {
        return AppContextHolder.INSTANCE;
    }

    @Override
    public void put(String key, Object value) {
        THREAD_LOCAL.get().put(key, value);
    }

    @Override
    public Object get(String key) {
        return THREAD_LOCAL.get().get(key);
    }

    @Override
    public Map<String, Object> getContext() {
        return THREAD_LOCAL.get();
    }

    @Override
    public void setContext(Map<String, Object> context) {
        THREAD_LOCAL.set(context);
    }

    @Override
    public void clear() {
        THREAD_LOCAL.remove();
    }

    private static final class AppContextHolder {

        private static final ApplicationContextHolder INSTANCE = new ApplicationContextHolder();

    }

}
