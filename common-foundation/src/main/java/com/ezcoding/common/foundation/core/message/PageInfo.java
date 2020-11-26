package com.ezcoding.common.foundation.core.message;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public class PageInfo implements Cloneable, Serializable {

    private static final long serialVersionUID = -2554220564325298703L;

    /**
     * 当前页码
     */
    public static final int DEFAULT_CURRENT_PAGE = 1;

    /**
     * 每页的条数
     */
    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 是否搜索全部数量
     */
    public static final boolean DEFAULT_SEARCH_COUNT = false;

    protected Integer pageSize;

    protected Integer currentPage;

    protected Integer totalItem;

    protected Boolean searchCount;

    public PageInfo() {
    }

    public PageInfo(Integer totalItem) {
        this.totalItem = totalItem;
    }

    public PageInfo(Integer currentPage, Integer pageSize) {
        this(currentPage, pageSize, DEFAULT_SEARCH_COUNT);
    }

    public PageInfo(Integer currentPage, Integer pageSize, Boolean searchCount) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.searchCount = searchCount;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(Integer totalItem) {
        this.totalItem = totalItem;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Boolean getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(Boolean searchCount) {
        this.searchCount = searchCount;
    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "pageSize=" + pageSize +
                ", currentPage=" + currentPage +
                ", totalItem=" + totalItem +
                ", searchCount=" + searchCount +
                '}';
    }

}
