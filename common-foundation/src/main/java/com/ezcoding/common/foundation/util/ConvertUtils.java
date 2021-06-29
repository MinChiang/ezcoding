package com.ezcoding.common.foundation.util;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-06-29 11:22
 */
public class ConvertUtils {

    /**
     * 转换实体为基本类型
     *
     * @param type  目标类型
     * @param value 传入值
     * @return 转换后的基本实体
     */
    public static Object convertToPrimitive(Class<?> type, String value) {
        if (type == char.class || type == Character.class) {
            return value.length() > 0 ? value.charAt(0) : '\0';
        } else if (type == boolean.class || type == Boolean.class) {
            return Boolean.valueOf(value);
        } else if (type == byte.class || type == Byte.class) {
            return Byte.valueOf(value);
        } else if (type == short.class || type == Short.class) {
            return Short.valueOf(value);
        } else if (type == int.class || type == Integer.class) {
            return Integer.valueOf(value);
        } else if (type == long.class || type == Long.class) {
            return Long.valueOf(value);
        } else if (type == float.class || type == Float.class) {
            return Float.valueOf(value);
        } else if (type == double.class || type == Double.class) {
            return Double.valueOf(value);
        }
        return value;
    }

    /**
     * 判断类型是否为基本类型
     *
     * @param type 目标类型
     * @return 是否为基本类型
     */
    public static boolean isPrimitive(Class<?> type) {
        return type == char.class || type == Character.class ||
                type == boolean.class || type == Boolean.class ||
                type == byte.class || type == Byte.class ||
                type == short.class || type == Short.class ||
                type == int.class || type == Integer.class ||
                type == long.class || type == Long.class ||
                type == float.class || type == Float.class ||
                type == double.class || type == Double.class;
    }

}
