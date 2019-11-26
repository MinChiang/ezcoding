package com.ezcoding.common.mybatis.convertor;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezcoding.common.foundation.core.message.head.PageInfo;
import com.ezcoding.common.foundation.util.ConvertUtils;
import com.ezcoding.common.mybatis.constant.MybatisConstants;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-10-31 21:28
 */
public class PageConvertor implements Converter {

    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 3;

    public PageConvertor() {
    }

    @Override
    public <T> T convert(Class<T> type, Object value) {
        if (!String.class.equals(value)) {
            return (T) new Page(PageInfo.getDefaultCurrentPage(), PageInfo.getDefaultPageSize(), MybatisConstants.SEARCH_COUNT);
        }

        String[] split = StringUtils.split(value.toString(), ConvertUtils.SPLITTER);
        if (split == null || split.length < MIN_LENGTH) {
            throw new IllegalArgumentException("转换参数个数不正确，请按照currentPage,pageSize,[searchCount]格式传参");
        }
        Integer currentPage = (Integer) ConvertUtils.convert(split[0], Integer.class);
        Integer pageSize = (Integer) ConvertUtils.convert(split[1], Integer.class);

        Boolean searchCount = MybatisConstants.SEARCH_COUNT;
        if (split.length == MAX_LENGTH) {
            searchCount = (Boolean) ConvertUtils.convert(split[2], Boolean.class);
        }
        return (T) new Page(currentPage == null ? PageInfo.getDefaultCurrentPage() : currentPage,
                pageSize == null ? PageInfo.getDefaultPageSize() : pageSize,
                searchCount == null ? MybatisConstants.SEARCH_COUNT : searchCount
        );
    }

}
