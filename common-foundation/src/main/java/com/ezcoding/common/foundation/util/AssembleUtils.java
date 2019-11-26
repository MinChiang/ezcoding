package com.ezcoding.common.foundation.util;

import com.google.common.collect.Lists;

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
     * @param <T> 需要创建的目标类型
     * @return 装配映射器
     */
    public static <S, T> AssembleMapper<S, T> instance(T target) {
        if (target == null) {
            throw new RuntimeException("目标对象不能为空");
        }
        return new AssembleMapper<>(target);
    }

    public static class AssembleMapper<S, T> {

        private T target;
        private List<FunctionAndBiConsumerMapping<S, ?, T>> mappings = Lists.newLinkedList();

        private AssembleMapper(T target) {
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
         * @param source 装配原料
         * @return 装配输出
         */
        public T assemble(S source) {
            if (source == null || target == null) {
                return null;
            }

            for (FunctionAndBiConsumerMapping mapping : mappings) {
                mapping.biConsumer.accept(target, mapping.function.apply(source));
            }
            return target;
        }

        private static class FunctionAndBiConsumerMapping<S, K, T> {

            Function<S, K> function;
            BiConsumer<T, K> biConsumer;

            FunctionAndBiConsumerMapping(Function<S, K> function, BiConsumer<T, K> biConsumer) {
                this.function = function;
                this.biConsumer = biConsumer;
            }

        }

    }

}
