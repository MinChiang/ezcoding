package com.ezcoding.common.foundation.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-08 0:29
 */
public class ConvertUtils {

    private static Map<Class<?>, IConverter<?>> converterMap;

    static {
        converterMap = new HashMap<>();
        converterMap.put(int.class, new AbstractConverter<Integer>(0) {
            @Override
            public Integer convertByNotBlank(String string) {
                return Integer.parseInt(string);
            }
        });
        converterMap.put(Integer.class, new AbstractConverter<Integer>(null) {
            @Override
            public Integer convertByNotBlank(String string) {
                return Integer.valueOf(string);
            }
        });
        converterMap.put(double.class, new AbstractConverter<Double>(0D) {
            @Override
            public Double convertByNotBlank(String string) {
                return Double.parseDouble(string);
            }
        });
        converterMap.put(Double.class, new AbstractConverter<Double>(null) {
            @Override
            public Double convertByNotBlank(String string) {
                return Double.valueOf(string);
            }
        });
        converterMap.put(long.class, new AbstractConverter<Long>(0L) {
            @Override
            public Long convertByNotBlank(String string) {
                return Long.parseLong(string);
            }
        });
        converterMap.put(Long.class, new AbstractConverter<Long>(null) {
            @Override
            public Long convertByNotBlank(String string) {
                return Long.valueOf(string);
            }
        });
        converterMap.put(boolean.class, new AbstractConverter<Boolean>(false) {
            @Override
            public Boolean convertByNotBlank(String string) {
                return Boolean.parseBoolean(string);
            }
        });
        converterMap.put(Boolean.class, new AbstractConverter<Boolean>(null) {
            @Override
            public Boolean convertByNotBlank(String string) {
                return Boolean.valueOf(string);
            }
        });
        converterMap.put(short.class, new AbstractConverter<Short>((short) 0) {
            @Override
            public Short convertByNotBlank(String string) {
                return Short.parseShort(string);
            }
        });
        converterMap.put(Short.class, new AbstractConverter<Short>(null) {
            @Override
            public Short convertByNotBlank(String string) {
                return Short.valueOf(string);
            }
        });
        converterMap.put(float.class, new AbstractConverter<Float>(0F) {
            @Override
            public Float convertByNotBlank(String string) {
                return Float.parseFloat(string);
            }
        });
        converterMap.put(Float.class, new AbstractConverter<Float>(null) {
            @Override
            public Float convertByNotBlank(String string) {
                return Float.valueOf(string);
            }
        });
        converterMap.put(BigDecimal.class, new AbstractConverter<BigDecimal>(null) {
            @Override
            public BigDecimal convertByNotBlank(String string) {
                return new BigDecimal(string);
            }
        });
    }

    public static <T> IConverter register(IConverter<T> converter, Class<T> cls) {
        converterMap.put(cls, converter);
        return converter;
    }

    public static <T> T convert(String string, Class<T> cls) {
        IConverter<?> converter = converterMap.get(cls);
        return converter == null ? null : (T) converter.convert(string);
    }

    public interface IConverter<T> {

        /**
         * string转基本类型
         *
         * @param string 需要转换的字符串
         * @return 转换完成的数值
         */
        T convert(String string);
    }

    public abstract static class AbstractConverter<T> implements IConverter<T> {
        protected T defaultValue;

        public AbstractConverter(T defaultValue) {
            this.defaultValue = defaultValue;
        }

        public T getDefaultValue() {
            return this.defaultValue;
        }

        public void setDefaultValue(T defaultValue) {
            this.defaultValue = defaultValue;
        }

        @Override
        public T convert(String string) {
            return StringUtils.isBlank(string) ? this.defaultValue : this.convertByNotBlank(string);
        }

        /**
         * 将非空的字符串转换为特定的类型
         *
         * @param string 非空字符串
         * @return 目标类型
         */
        public abstract T convertByNotBlank(String string);
    }

}
