package com.ezcoding.test;

import com.ezcoding.common.core.enums.EnumMappable;
import com.ezcoding.common.core.enums.EnumMappableUtils;
import com.fasterxml.jackson.annotation.JsonValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-09-01 14:35
 */
public class EnumMappableTest {

    @Test
    public void testInterfaceMapping() {
        Assertions.assertEquals(EnumMappableUtils.map(0, TestEnum0.class), TestEnum0.TEST0);
        Assertions.assertEquals(EnumMappableUtils.map(1, TestEnum0.class), TestEnum0.TEST1);
        Assertions.assertEquals(EnumMappableUtils.map(2, TestEnum0.class), TestEnum0.TEST2);

        Assertions.assertEquals(EnumMappableUtils.mapIgnoreType("0", TestEnum0.class), TestEnum0.TEST0);
        Assertions.assertEquals(EnumMappableUtils.mapIgnoreType("1", TestEnum0.class), TestEnum0.TEST1);
        Assertions.assertEquals(EnumMappableUtils.mapIgnoreType("2", TestEnum0.class), TestEnum0.TEST2);
    }

    @Test
    public void testSimpleMapping() {
        Assertions.assertEquals(EnumMappableUtils.map(0, TestEnum1.class), TestEnum1.TEST0);
        Assertions.assertEquals(EnumMappableUtils.map(1, TestEnum1.class), TestEnum1.TEST1);
        Assertions.assertEquals(EnumMappableUtils.map(2, TestEnum1.class), TestEnum1.TEST2);

        Assertions.assertEquals(EnumMappableUtils.mapIgnoreType("0", TestEnum1.class), TestEnum1.TEST0);
        Assertions.assertEquals(EnumMappableUtils.mapIgnoreType("1", TestEnum1.class), TestEnum1.TEST1);
        Assertions.assertEquals(EnumMappableUtils.mapIgnoreType("2", TestEnum1.class), TestEnum1.TEST2);
    }

    @Test
    public void testJacksonEnumMapping() {
        Assertions.assertEquals(EnumMappableUtils.map(0, TestEnum2.class), TestEnum2.TEST0);
        Assertions.assertEquals(EnumMappableUtils.map(1, TestEnum2.class), TestEnum2.TEST1);
        Assertions.assertEquals(EnumMappableUtils.map(2, TestEnum2.class), TestEnum2.TEST2);

        Assertions.assertEquals(EnumMappableUtils.mapIgnoreType("0", TestEnum2.class), TestEnum2.TEST0);
        Assertions.assertEquals(EnumMappableUtils.mapIgnoreType("1", TestEnum2.class), TestEnum2.TEST1);
        Assertions.assertEquals(EnumMappableUtils.mapIgnoreType("2", TestEnum2.class), TestEnum2.TEST2);

        Assertions.assertEquals(EnumMappableUtils.map(0, TestEnum3.class), TestEnum3.TEST0);
        Assertions.assertEquals(EnumMappableUtils.map(1, TestEnum3.class), TestEnum3.TEST1);
        Assertions.assertEquals(EnumMappableUtils.map(2, TestEnum3.class), TestEnum3.TEST2);

        Assertions.assertEquals(EnumMappableUtils.mapIgnoreType("0", TestEnum3.class), TestEnum3.TEST0);
        Assertions.assertEquals(EnumMappableUtils.mapIgnoreType("1", TestEnum3.class), TestEnum3.TEST1);
        Assertions.assertEquals(EnumMappableUtils.mapIgnoreType("2", TestEnum3.class), TestEnum3.TEST2);
    }

    public enum TestEnum0 implements EnumMappable<Integer> {

        TEST0(0),

        TEST1(1),

        TEST2(2);

        private static final Map<Integer, TestEnum0> ALL = new HashMap<>();

        static {
            for (Field field : TestEnum0.class.getDeclaredFields()) {
                if (field.getType().equals(TestEnum0.class)) {
                    try {
                        TestEnum0 instance = (TestEnum0) field.get(null);
                        ALL.put(instance.id, instance);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        private final int id;

        TestEnum0(int id) {
            this.id = id;
        }

        @Override
        public Integer to() {
            return this.id;
        }

    }

    public enum TestEnum1 {

        TEST0(0),

        TEST1(1),

        TEST2(2);

        private final int id;

        TestEnum1(int id) {
            this.id = id;
        }

    }

    public enum TestEnum2 {

        TEST0(0),

        TEST1(1),

        TEST2(2);

        @JsonValue
        private final int id;

        TestEnum2(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    public enum TestEnum3 {

        TEST0(0),

        TEST1(1),

        TEST2(2);

        private final int id;

        TestEnum3(int id) {
            this.id = id;
        }

        @JsonValue
        public int getId() {
            return id;
        }
    }

}
