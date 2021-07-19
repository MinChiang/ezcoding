package com.ezcoding.common.foundation.util;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-06-26 17:03
 */
public class StreamUtils {

    /**
     * 关联列表
     * 此关联操作，返回结果是与sourceList的顺序保证一致
     *
     * @param sourceList     元素列表
     * @param keyExtractor   关联key提取器
     * @param extraCollector 额外信息收集器
     * @param resultMapper   返回结果映射器，注意此时第二个参数，可能为空，需要额外判断
     * @param <K>            关联元素
     * @param <S>            源元素
     * @param <E>            额外信息
     * @param <T>            目标类型
     * @return 目标对象列表
     */
    public static <K, S, E, T> List<T> associateList(List<? extends S> sourceList,
                                                     Function<? super S, ? extends K> keyExtractor,
                                                     Function<Set<? extends K>, Map<? extends K, ? extends E>> extraCollector,
                                                     BiFunction<? super S, ? super E, ? extends T> resultMapper) {
        if (sourceList == null || sourceList.isEmpty()) {
            return Collections.emptyList();
        }
        Objects.requireNonNull(keyExtractor);
        Objects.requireNonNull(extraCollector);
        Objects.requireNonNull(resultMapper);

        Set<? extends K> set = sourceList.stream().filter(Objects::nonNull).map(keyExtractor).collect(Collectors.toSet());
        if (set.isEmpty()) {
            return sourceList.stream().map(s -> resultMapper.apply(s, null)).collect(Collectors.toList());
        }
        Map<? extends K, ? extends E> extraMapping = extraCollector.apply(set);
        if (extraMapping == null || extraMapping.isEmpty()) {
            return sourceList.stream().map(s -> resultMapper.apply(s, null)).collect(Collectors.toList());
        }
        List<T> results = new ArrayList<>();
        // 保证元素的顺序
        for (S source : sourceList) {
            K key = keyExtractor.apply(source);
            E extra = extraMapping.get(key);
            T result = resultMapper.apply(source, extra);
            if (result == null) {
                continue;
            }
            results.add(result);
        }
        return results;
    }

    /**
     * 关联列表
     * 此关联操作，返回结果是与sourceList的顺序保证一致
     *
     * @param sourceList     元素列表
     * @param keyExtractor   关联key提取器
     * @param extraCollector 额外信息收集器
     * @param resultMapper   返回结果映射器
     * @param <K>            关联元素
     * @param <S>            源元素
     * @param <E>            额外信息
     * @param <T>            目标类型
     * @return 目标对象列表
     */
    public static <K, S, E, T> List<T> associateListOptional(List<? extends S> sourceList,
                                                             Function<? super S, ? extends K> keyExtractor,
                                                             Function<Set<? extends K>, Map<? extends K, ? extends E>> extraCollector,
                                                             BiFunction<? super S, Optional<? extends E>, ? extends T> resultMapper) {
        return
                associateList(
                        sourceList,
                        keyExtractor,
                        extraCollector,
                        (s, e) -> resultMapper.apply(s, Optional.ofNullable(e))
                );
    }

    /**
     * 关联集合
     *
     * @param source         元素列表
     * @param keyExtractor   关联key提取器
     * @param extraCollector 额外信息收集器
     * @param resultMapper   返回结果映射器，注意此时第二个参数，可能为空，需要额外判断
     * @param <K>            关联元素
     * @param <S>            源元素
     * @param <E>            额外信息
     * @param <T>            目标类型
     * @return 目标对象列表
     */
    public static <K, S, E, T> Set<T> associateSet(Collection<? extends S> source,
                                                   Function<? super S, ? extends K> keyExtractor,
                                                   Function<Set<? extends K>, Map<? extends K, ? extends E>> extraCollector,
                                                   BiFunction<? super S, ? super E, ? extends T> resultMapper) {
        if (source == null || source.isEmpty()) {
            return Collections.emptySet();
        }
        Objects.requireNonNull(keyExtractor);
        Objects.requireNonNull(extraCollector);
        Objects.requireNonNull(resultMapper);

        Map<? extends K, ? extends List<? extends S>> groupingElements = source.stream().collect(Collectors.groupingBy(keyExtractor, HashMap::new, Collectors.mapping(Function.identity(), Collectors.toList())));
        if (groupingElements.isEmpty()) {
            return source.stream().map(s -> resultMapper.apply(s, null)).collect(Collectors.toSet());
        }
        Map<? extends K, ? extends E> extraMapping = extraCollector.apply(groupingElements.keySet());
        if (extraMapping == null || extraMapping.isEmpty()) {
            return source.stream().map(s -> resultMapper.apply(s, null)).collect(Collectors.toSet());
        }
        Set<T> result = new HashSet<>();
        for (Map.Entry<? extends K, ? extends List<? extends S>> entry : groupingElements.entrySet()) {
            K key = entry.getKey();
            List<? extends S> values = entry.getValue();
            E e = extraMapping.get(key);
            for (S value : values) {
                T t = resultMapper.apply(value, e);
                if (t == null) {
                    continue;
                }
                result.add(t);
            }
        }
        return result;
    }

    /**
     * 关联集合
     *
     * @param source         元素列表
     * @param keyExtractor   关联key提取器
     * @param extraCollector 额外信息收集器
     * @param resultMapper   返回结果映射器
     * @param <K>            关联元素
     * @param <S>            源元素
     * @param <E>            额外信息
     * @param <T>            目标类型
     * @return 目标对象列表
     */
    public static <K, S, E, T> Set<T> associateSetOptional(Collection<? extends S> source,
                                                           Function<? super S, ? extends K> keyExtractor,
                                                           Function<Set<? extends K>, Map<? extends K, ? extends E>> extraCollector,
                                                           BiFunction<? super S, Optional<? extends E>, ? extends T> resultMapper) {
        return
                associateSet(
                        source,
                        keyExtractor,
                        extraCollector,
                        (s, e) -> resultMapper.apply(s, Optional.ofNullable(e))
                );
    }

}
