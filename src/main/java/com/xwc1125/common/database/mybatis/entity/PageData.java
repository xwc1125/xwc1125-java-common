package com.xwc1125.common.database.mybatis.entity;

import com.xwc1125.common.util.json.JsonUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xwc1125
 */
@SuppressWarnings("serial")
public class PageData<T> {

    /**
     * 总条数
     */
    private long total = 0;
    /**
     * 数据
     */
    private List<T> rows = new ArrayList<>();
    /**
     * 当前页数
     */
    private Integer pageNum = 0;
    /**
     * 每页条数
     */
    private Integer pageSize = 0;
    /**
     * 总页数
     */
    private Integer pages;

    private int prePage;
    private int nextPage;

    public PageData() {
    }

    public PageData(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public PageData(long total, List<T> rows, Integer pageNum, Integer pages) {
        this.total = total;
        this.rows = rows;
        this.pageNum = pageNum;
        this.pages = pages;
    }

    public PageData(long total, List<T> rows, Integer pageNum, Integer pageSize, Integer pages, int prePage,
            int nextPage) {
        this.total = total;
        this.rows = rows;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pages = pages;
        this.prePage = prePage;
        this.nextPage = nextPage;
    }

    public static PageData parsePageInfo(com.github.pagehelper.PageInfo pageInfo) {
        PageData pageData = new PageData<>();
        pageData.setTotal(pageInfo.getTotal());
        pageData.setRows(pageInfo.getList());
        pageData.setPageNum(pageInfo.getPageNum());
        pageData.setPageSize(pageInfo.getPageSize());
        pageData.setPages(pageInfo.getPages());
        pageData.setPrePage(pageInfo.getPrePage());
        pageData.setNextPage(pageInfo.getNextPage());
        return pageData;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }
}
