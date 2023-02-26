package com.xwc1125.common.entity.response;

import com.github.pagehelper.PageInfo;
import com.xwc1125.common.util.json.JsonUtils;

import java.util.List;

/**
 * @author xwc1125
 */
@SuppressWarnings("serial")
public class TableData<T> {

    /**
     * 总条数
     */
    private long total;
    /**
     * 数据
     */
    private List<T> rows;
    /**
     * 当前页数
     */
    private Integer pageNum;
    /**
     * 总页数
     */
    private Integer pages;

    public TableData() {
    }

    public TableData(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public TableData(long total, List<T> rows, Integer pageNum, Integer pages) {
        this.total = total;
        this.rows = rows;
        this.pageNum = pageNum;
        this.pages = pages;
    }

    public static TableData parsePageInfo(PageInfo pageInfo) {
        return new TableData(pageInfo.getTotal(), pageInfo.getList(), pageInfo.getPageNum(), pageInfo.getPages());
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

    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }
}
