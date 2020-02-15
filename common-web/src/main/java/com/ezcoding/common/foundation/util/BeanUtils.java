package com.ezcoding.common.foundation.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-19 14:54
 */
public class BeanUtils {

    private static final ObjectMapper OBJECT_MAPPER;
    private static final ObjectMapper OBJECT_MAPPER_WITHOUT_NULL;

    private static final TypeReference<?> MAP_TYPE = new MapTypeReference();

    private static final TypeReference<?> LIST_TYPE = new ListTypeReference();

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
        OBJECT_MAPPER.setLocale(Locale.SIMPLIFIED_CHINESE);

        OBJECT_MAPPER_WITHOUT_NULL = new ObjectMapper();
        OBJECT_MAPPER_WITHOUT_NULL.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        OBJECT_MAPPER_WITHOUT_NULL.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER_WITHOUT_NULL.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
        OBJECT_MAPPER_WITHOUT_NULL.setLocale(Locale.SIMPLIFIED_CHINESE);
    }

    private BeanUtils() {

    }

    /**
     * 类属性拷贝
     *
     * @param src         被拷贝的对象，目标对象必须实现空构造方法
     * @param targetClass 目标类
     * @param <T>         目标类
     * @return 目标实体
     */
    public static <T> T copy(Object src, Class<T> targetClass) {
        T t = org.springframework.beans.BeanUtils.instantiateClass(targetClass);
        return copy(src, t);
    }

    /**
     * 类属性拷贝
     *
     * @param src    被拷贝的对象，目标对象必须实现空构造方法
     * @param target 目标对象
     * @param <T>    目标类
     * @return 目标对象
     */
    public static <T> T copy(Object src, T target) {
        try {
            org.springframework.beans.BeanUtils.copyProperties(src, target);
            return target;
        } catch (Exception e) {
            throw new RuntimeException("不能从" + src.getClass() + "转换到" + target, e);
        }
    }

    /**
     * 列表属性拷贝
     *
     * @param src         被拷贝列表
     * @param targetClass 目标类
     * @param <S>         列表中的类
     * @param <T>         目标类
     * @return 目标对象列表
     */
    public static <S, T> List<T> copy(Collection<S> src, Class<T> targetClass) {
        try {
            List<T> targets = new ArrayList<>();
            for (S s : src) {
                T t = copy(s, targetClass);
                targets.add(t);
            }
            return targets;
        } catch (Exception e) {
            throw new RuntimeException("不能从" + src.getClass() + "转换到" + targetClass, e);
        }
    }

    /**
     * 将对象装换为map
     *
     * @param bean             目标对象
     * @param withNullProperty 是否带有空的属性
     * @return 目标map对象
     */
    public static <T> Map<String, Object> beanToMap(T bean, boolean withNullProperty) {
        if (bean == null) {
            return new HashMap<>(0);
        }
        return withNullProperty ? OBJECT_MAPPER.convertValue(bean, MAP_TYPE) : OBJECT_MAPPER_WITHOUT_NULL.convertValue(bean, MAP_TYPE);
    }

    /**
     * 将map装换为javabean对象，目标对象必须有无参构造方法
     *
     * @param map         源map对象
     * @param targetClass 目标对象类型
     * @return 目标对象
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> targetClass) {
        return OBJECT_MAPPER.convertValue(map, targetClass);
    }

    private static class MapTypeReference extends TypeReference<Map<String, Object>> {

    }

    private static class ListTypeReference extends TypeReference<List<Object>> {

    }
}
