package com.ezcoding.common.foundation.core.context;

import java.util.HashMap;
import java.util.Map;

/**
 * 上下文默认实现
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-10-24 10:44
 */
public class ApplicationContextHolder implements IApplicationContext {

    private static final ThreadLocal<Map<String, Object>> THREADLOCAL = new InheritableThreadLocal<Map<String, Object>>() {
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
        THREADLOCAL.get().put(key, value);
    }

    @Override
    public Object get(String key) {
        return THREADLOCAL.get().get(key);
    }

    @Override
    public Map<String, Object> getContext() {
        return THREADLOCAL.get();
    }

    @Override
    public void setContext(Map<String, Object> context) {
        THREADLOCAL.set(context);
    }

    @Override
    public void clear() {
        THREADLOCAL.remove();
    }

    private static final class AppContextHolder {

        private static final ApplicationContextHolder INSTANCE = new ApplicationContextHolder();

    }

}
