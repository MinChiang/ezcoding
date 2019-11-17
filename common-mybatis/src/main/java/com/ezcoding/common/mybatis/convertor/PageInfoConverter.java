package com.ezcoding.common.mybatis.convertor;

import com.ezcoding.common.foundation.core.message.head.PageInfo;
import com.ezcoding.common.foundation.util.ConvertUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-10-21 20:03
 */
public class PageInfoConverter extends ConvertUtils.AbstractConverter<PageInfo> {

    private static final int SPLIT_LENGTH = 2;

    public PageInfoConverter(PageInfo defaultValue) {
        super(defaultValue);
    }

    @Override
    public PageInfo convertByNotBlank(String string) {
        String[] split = StringUtils.split(string, ',');
        if (split == null || split.length != SPLIT_LENGTH) {
            throw new IllegalArgumentException("转换参数个数不正确，请按照[currentPage],[pageSize]格式传参");
        }
        Integer pageSize = ConvertUtils.convert(split[0], int.class);
        Integer currentPage = ConvertUtils.convert(split[1], int.class);
        return new PageInfo(currentPage, pageSize);
    }
}
