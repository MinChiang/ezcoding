package com.ezcoding.common.foundation.util;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-25 9:44
 */
public class AssembleUtils {

    /**
     * 实例化装配映射器
     *
     * @param <S>    原类型
     * @param <T>    需要创建的目标类型
     * @param src    原类型对象
     * @param target 目标类型对象
     * @return 装配映射器
     */
    public static <S, T> AssembleMapper<S, T> instance(S src, T target) {
        if (src == null || target == null) {
            throw new RuntimeException("源对象和目标对象不能为空");
        }
        return new AssembleMapper<>(src, target);
    }

    public static class AssembleMapper<S, T> {

        private S src;
        private T target;
        private List<FunctionAndBiConsumerMapping<S, T>> mappings = new LinkedList<>();

        private AssembleMapper(S src, T target) {
            this.src = src;
            this.target = target;
        }

        /**
         * 映射流程
         *
         * @param function   获取源数据
         * @param biConsumer 设置目标数据
         * @param <K>        获取到的源数据类型
         * @return 装配映射器
         */
        public <K> AssembleMapper<S, T> map(Function<S, K> function, BiConsumer<T, K> biConsumer) {
            mappings.add(new FunctionAndBiConsumerMapping<>(function, biConsumer));
            return this;
        }

        /**
         * 装配
         *
         * @return 装配输出
         */
        public T assemble() {
            for (FunctionAndBiConsumerMapping mapping : this.mappings) {
                mapping.biConsumer.accept(target, mapping.function.apply(src));
            }
            return target;
        }

        private static class FunctionAndBiConsumerMapping<S, T> {

            Function<S, ?> function;
            BiConsumer<T, ?> biConsumer;

            FunctionAndBiConsumerMapping(Function<S, ?> function, BiConsumer<T, ?> biConsumer) {
                this.function = function;
                this.biConsumer = biConsumer;
            }

        }

    }

}
