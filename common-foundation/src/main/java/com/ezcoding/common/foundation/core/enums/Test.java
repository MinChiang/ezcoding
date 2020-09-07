package com.ezcoding.common.foundation.core.enums;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-09-07 9:58
 */
public enum Test implements EnumMappable<Test, Integer> {

    ZERO(0),

    ONE(1);

    private final int id;

    static {
        System.out.println("hahah");
    }

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
