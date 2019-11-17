package com.ezcoding.common.mybatis.convertor;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezcoding.common.foundation.util.ConvertUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-10-31 21:28
 */
public class PageConvertor extends ConvertUtils.AbstractConverter<Page> {

    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 3;

    public PageConvertor(Page defaultValue) {
        super(defaultValue);
    }

    @Override
    public Page convertByNotBlank(String string) {
        String[] split = StringUtils.split(string, ',');
        if (split == null || split.length < MIN_LENGTH) {
            throw new IllegalArgumentException("转换参数个数不正确，请按照[currentPage],[pageSize],[searchCount]格式传参");
        }
        Integer currentPage = ConvertUtils.convert(split[0], int.class);
        Integer pageSize = ConvertUtils.convert(split[1], int.class);

        Boolean searchCount = false;
        if (split.length == MAX_LENGTH) {
            searchCount = ConvertUtils.convert(split[2], boolean.class);
        }
        Page page = new Page(currentPage, pageSize);
        //默认关闭不搜索条数
        page.setSearchCount(searchCount);
        return page;
    }
}
