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
    public static int DEFAULT_CURRENT_PAGE = 1;

    /**
     * 每页的条数
     */
    public static int DEFAULT_PAGE_SIZE = 10;

    protected Integer pageSize;

    protected Integer currentPage;

    protected Integer totalItem;

    protected Boolean searchCount;

    public PageInfo() {
        this.searchCount = false;
        this.pageSize = DEFAULT_PAGE_SIZE;
        this.currentPage = DEFAULT_CURRENT_PAGE;
    }

    public PageInfo(Integer totalItem) {
        this.totalItem = totalItem;
    }

    public PageInfo(Integer currentPage, Integer pageSize) {
        this.searchCount = false;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
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

    public static int getDefaultCurrentPage() {
        return DEFAULT_CURRENT_PAGE;
    }

    public static void setDefaultCurrentPage(int defaultCurrentPage) {
        DEFAULT_CURRENT_PAGE = defaultCurrentPage;
    }

    public static int getDefaultPageSize() {
        return DEFAULT_PAGE_SIZE;
    }

    public static void setDefaultPageSize(int defaultPageSize) {
        DEFAULT_PAGE_SIZE = defaultPageSize;
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
