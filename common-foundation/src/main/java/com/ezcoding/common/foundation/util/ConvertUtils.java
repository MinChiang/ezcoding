package com.ezcoding.common.foundation.util;

import com.ezcoding.common.foundation.core.message.head.PageInfo;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.*;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-08 0:29
 */
public class ConvertUtils extends org.apache.commons.beanutils.ConvertUtils {

    public static final char SPLITTER = ',';

    static {
        init();
    }

    /**
     * 初始化
     */
    public static void init() {
        registerStandard();
        registerExtra();
    }

    /**
     * 覆盖原生转换器null转为默认值的converter
     */
    private static void registerStandard() {
        org.apache.commons.beanutils.ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
        org.apache.commons.beanutils.ConvertUtils.register(new BigIntegerConverter(null), BigInteger.class);
        org.apache.commons.beanutils.ConvertUtils.register(new BooleanConverter(null), Boolean.class);
        org.apache.commons.beanutils.ConvertUtils.register(new ByteConverter(null), Byte.class);
        org.apache.commons.beanutils.ConvertUtils.register(new CharacterConverter(null), Character.class);
        org.apache.commons.beanutils.ConvertUtils.register(new DoubleConverter(null), Double.class);
        org.apache.commons.beanutils.ConvertUtils.register(new FloatConverter(null), Float.class);
        org.apache.commons.beanutils.ConvertUtils.register(new IntegerConverter(null), Integer.class);
        org.apache.commons.beanutils.ConvertUtils.register(new LongConverter(null), Long.class);
        org.apache.commons.beanutils.ConvertUtils.register(new ShortConverter(null), Short.class);
        org.apache.commons.beanutils.ConvertUtils.register(new StringConverter(null), String.class);
    }

    /**
     * 扩展converter
     */
    private static void registerExtra() {
        org.apache.commons.beanutils.ConvertUtils.register(new PageInfoConverter(), PageInfo.class);
    }

    private static class PageInfoConverter implements Converter {

        private static final int SPLIT_LENGTH = 2;

        @Override
        public <T> T convert(Class<T> type, Object value) {
            String[] split = StringUtils.split(value.toString(), ConvertUtils.SPLITTER);
            if (split == null || split.length != SPLIT_LENGTH) {
                throw new IllegalArgumentException("转换参数个数不正确，请按照currentPage,pageSize格式传参");
            }
            Integer pageSize = (Integer) ConvertUtils.convert(split[0], Integer.class);
            Integer currentPage = (Integer) ConvertUtils.convert(split[1], Integer.class);
            return (T) new PageInfo(currentPage, pageSize);
        }

    }

}
