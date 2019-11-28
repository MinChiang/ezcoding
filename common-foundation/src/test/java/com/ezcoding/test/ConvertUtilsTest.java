package com.ezcoding.test;

import com.ezcoding.common.foundation.core.message.head.PageInfo;
import com.ezcoding.common.foundation.util.ConvertUtils;
import org.junit.Test;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-28 15:17
 */
public class ConvertUtilsTest {

    @Test
    public void convertTest() {
        ConvertUtils.init();

        Integer integer = (Integer) ConvertUtils.convert((String) null, Integer.class);
        System.out.println(integer);

        PageInfo convert = (PageInfo) ConvertUtils.convert("1,2", PageInfo.class);
        System.out.println(convert.getCurrentPage());
        System.out.println(convert.getPageSize());
        System.out.println(convert.getTotalItem());
    }

}
