package com.ezcoding.test;

import com.ezcoding.common.core.enums.EnumMappable;
import com.ezcoding.common.core.enums.EnumMappableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-09-01 14:35
 */
public class EnumMappableTest {

    @Test
    public void testRegister() {
        EnumMappableUtils.register(new TestConverter());
        Assertions.assertEquals(EnumMappableUtils.map(0, EnumTest0.class), EnumTest0.ZERO);
        Assertions.assertEquals(EnumMappableUtils.map(1, EnumTest0.class), EnumTest0.ONE);
        Assertions.assertEquals(EnumMappableUtils.map(2, EnumTest0.class), EnumTest0.TWO);
    }

    public enum EnumTest0 {

        ZERO(0),

        ONE(1),

        TWO(2);

        private final int id;

        EnumTest0(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    public class TestConverter implements EnumMappable<EnumTest0, Integer> {

        @Override
        public Integer map(EnumTest0 enumTest0) {
            return enumTest0.getId();
        }

    }

}
