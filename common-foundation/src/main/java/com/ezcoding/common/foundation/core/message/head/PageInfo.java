package com.ezcoding.common.foundation.core.message.head;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    @JsonProperty
    protected Integer pageSize;

    @JsonProperty
    protected Integer currentPage;

    @JsonProperty
    protected Long totalItem;

    public PageInfo() {
    }

    public PageInfo(Long totalItem) {
        this.totalItem = totalItem;
    }

    public PageInfo(Integer currentPage, Integer pageSize) {
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

    public Long getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(Long totalItem) {
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

}
