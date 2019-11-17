package com.ezcoding.common.user.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezcoding.common.foundation.core.message.head.PageInfo;

/**
 * 分页工具类
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-10-31 10:45
 */
public class PageUtils {

    private static int defaultCurrentPage = 1;
    private static int defaultPageSize = 10;

    private PageUtils() {

    }

    /**
     * 根据标准报文的分页获取内部分页信息
     *
     * @return 分页信息
     */
    public static <T> Page<T> convertToPage(PageInfo pageInfo) {
        if (pageInfo == null) {
            return null;
        }
        if (pageInfo.getCurrentPage() == null && pageInfo.getPageSize() == null) {
            return null;
        }
        Integer currentPage = pageInfo.getCurrentPage();
        Integer pageSize = pageInfo.getPageSize();
        Page<T> page = new Page<>(currentPage == null ? defaultCurrentPage : currentPage,
                pageSize == null ? defaultPageSize : pageSize);
        page.setSearchCount(false);
        return page;
    }

    /**
     * 获取默认的分页信息
     *
     * @return 分页信息
     */
    public static <T> Page<T> defaultPagination() {
        Page<T> page = new Page<>(defaultCurrentPage, defaultPageSize);
        page.setSearchCount(false);
        return page;
    }

    /**
     * 将mybatisplus中的分页转换为标准分页信息
     *
     * @param page 分页包装类
     * @return 标准分页信息
     */
    public static PageInfo convertToPageInfo(Page<?> page) {
        if (page.isSearchCount()) {
            return new PageInfo(page.getTotal());
        }
        return new PageInfo();
    }

    public static int getDefaultCurrentPage() {
        return defaultCurrentPage;
    }

    public static void setDefaultCurrentPage(int defaultCurrentPage) {
        PageUtils.defaultCurrentPage = defaultCurrentPage;
    }

    public static int getDefaultPageSize() {
        return defaultPageSize;
    }

    public static void setDefaultPageSize(int defaultPageSize) {
        PageUtils.defaultPageSize = defaultPageSize;
    }

}
