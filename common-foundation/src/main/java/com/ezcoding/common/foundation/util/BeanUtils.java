package com.ezcoding.common.foundation.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-12-10 21:14
 */
public class BeanUtils {

    /**
     * 实例化
     *
     * @param classString 列表
     * @param <T>         类型
     * @return 实例
     */
    public static <T> T getInstance(String classString) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Class<T> cls = (Class<T>) Class.forName(classString);
        return cls.newInstance();
    }

    /**
     * 实例化
     *
     * @param classStrings 列表
     * @param <T>          类型
     * @return 实例
     */
    public static <T> List<T> getInstances(List<String> classStrings) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        List<T> instances = new ArrayList<>();
        if (classStrings != null && !classStrings.isEmpty()) {
            for (String classString : classStrings) {
                instances.add(getInstance(classString));
            }
        }
        return instances;
    }

}
