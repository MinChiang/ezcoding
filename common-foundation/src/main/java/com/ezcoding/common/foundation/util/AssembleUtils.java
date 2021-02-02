package com.ezcoding.common.foundation.util;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * 延时化装配工具类
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-25 9:44
 */
public class AssembleUtils {

    /**
     * 实例化装配映射器
     *
     * @param <S>    源类型
     * @param <T>    目标类型
     * @param src    原类型对象
     * @param target 目标类型对象
     * @return 装配映射器
     */
    public static <S, T> Assembler<S, T> of(S src, T target) {
        if (src == null || target == null) {
            throw new RuntimeException("source and target can not be null");
        }
        return new Assembler<>(src, target);
    }

    /**
     * 装配器
     *
     * @param <S> 源类型
     * @param <T> 目标类型
     */
    public static class Assembler<S, T> {

        private final S src;
        private final T target;
        private final List<FunctionAndBiConsumerAssembler<S, T, ?>> mappingList = new LinkedList<>();

        private Assembler(S src, T target) {
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
        public <K> Assembler<S, T> with(Function<S, K> function, BiConsumer<T, K> biConsumer) {
            mappingList.add(new FunctionAndBiConsumerAssembler<>(function, biConsumer));
            return this;
        }

        /**
         * 装配
         * 所有属性赋值均延时到此方法执行
         *
         * @return 装配输出
         */
        public T assemble() {
            for (FunctionAndBiConsumerAssembler<S, T, ?> mapping : this.mappingList) {
                Function<S, Object> function = (Function<S, Object>) mapping.function;
                BiConsumer<T, Object> biConsumer = (BiConsumer<T, Object>) mapping.biConsumer;

                biConsumer.accept(target, function.apply(src));
            }
            return target;
        }

        /**
         * 临时内部类
         *
         * @param <S> 源类型
         * @param <T> 目标类型
         * @param <K> 中间函数映射类型
         */
        private static class FunctionAndBiConsumerAssembler<S, T, K> {

            Function<S, K> function;
            BiConsumer<T, K> biConsumer;

            FunctionAndBiConsumerAssembler(Function<S, K> function, BiConsumer<T, K> biConsumer) {
                this.function = function;
                this.biConsumer = biConsumer;
            }

        }

    }

}
