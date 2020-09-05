package com.ezcoding.common.foundation.core.enums;

import com.ezcoding.common.foundation.core.message.type.MessageTypeEnum;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-09-05 21:18
 */
public class EnumScanner {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnumScanner.class);

    private static final List<EnumMappableStrategy> STRATEGIES = new CopyOnWriteArrayList<>();

    static {
        STRATEGIES.add(new InterfaceEnumMapping());
    }

    /**
     * 注册扫描规则
     *
     * @param enumMappableStrategy 待注册的扫描规则
     */
    public static void registerStrategy(EnumMappableStrategy enumMappableStrategy) {
        Optional
                .ofNullable(enumMappableStrategy)
                .ifPresent(STRATEGIES::add);
    }

    /**
     *
     */
    public synchronized static void scan(String[] packages) {
        if (packages == null || packages.length == 0) {
            return;
        }
        Set<URL> urls = Arrays
                .stream(packages)
                .map(ClasspathHelper::forPackage)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .setUrls(urls)
        );
        Set<Class<? extends Enum>> enums = reflections.getSubTypesOf(Enum.class);
        if (enums == null || enums.isEmpty()) {
            LOGGER.warn("can't find any class from packages [{}]", Arrays.toString(packages));
            return;
        }

        int count;
        for (Class<? extends Enum> e : enums) {
            count = 0;
            for (EnumMappableStrategy strategy : STRATEGIES) {
                if (strategy.canMap((Class<? extends Enum<?>>) e)) {
                    EnumMappableUtils.register(strategy.map((Class<? extends Enum<?>>) e));
                    count++;
                }
            }
            if (count == 0) {
                LOGGER.warn("can't find any strategy of enum [{}]", e.getName());
            }
        }
    }

    public static void main(String[] args) {

        EnumScanner.registerStrategy(new JacksonEnumMapping());
        EnumScanner.registerStrategy(new SimpleEnumMapping());

        EnumScanner.scan(new String[]{"com.ezcoding"});
        System.out.println(EnumMappableUtils.map(0, Test.class));
        System.out.println(EnumMappableUtils.map(1, Test.class));

        System.out.println(EnumMappableUtils.map("application/json", MessageTypeEnum.class));
//        EnumMappableUtils.register(MessageTypeEnum::getType);
//        System.out.println(EnumMappableUtils.map("application/json", MessageTypeEnum.class));
//        System.out.println(EnumMappableUtils.map("text/xml", MessageTypeEnum.class));

        System.out.println(EnumMappableUtils.map(0, Test2.class));
        System.out.println(EnumMappableUtils.map(1, Test2.class));

    }

    public static enum Test implements EnumMappable<Test, Integer> {

        ZERO(0),

        ONE(1);

        private final int id;

        Test(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        @Override
        public Integer map(Test test) {
            return test.getId();
        }
    }

    public static enum Test2 {

        ZERO(0),

        ONE(1);

        private final int id;

        Test2(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

    }

}
