package com.xwc1125.common.database.mybatis.entity;

import com.xwc1125.common.database.mybatis.config.PageConfig;
import com.xwc1125.common.util.servlet.ServletUtils;
import com.xwc1125.common.util.string.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description: 查询参数
 * @Author: xwc1125
 * @Date: 2019-03-07 22:22
 * @Copyright Copyright@2019
 */
public class Query extends LinkedHashMap<String, Object> {
    public final static int PAGE_LIMIT_10 = 10;
    public final static int PAGE_LIMIT_20 = 20;
    public final static int PAGE_LIMIT_25 = 25;
    public final static int PAGE_LIMIT_50 = 50;
    public final static int PAGE_LIMIT_100 = 100;
    public final static int PAGE_LIMIT_200 = 200;
    public final static int PAGE_LIMIT_500 = 500;
    /**
     * 当前页码
     */
    private Integer page;
    /**
     * 每页条数
     */
    private Integer limit;
    /**
     * 排序列
     */
    private String orderByColumn;
    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    private String isAsc;

    public Query() {

    }

    public Query(Map<String, Object> params) {
        this.putAll(params);
        //分页参数
        if (params.get(PageConfig.PAGE_NAME) != null) {
            this.page = Integer.parseInt(params.get(PageConfig.PAGE_NAME).toString());
        }
        if (params.get(PageConfig.LIMIT_NAME) != null) {
            this.limit = Integer.parseInt(params.get(PageConfig.LIMIT_NAME).toString());
        }
        this.remove(PageConfig.PAGE_NAME);
        this.remove(PageConfig.LIMIT_NAME);
    }

    public String getOrderBy() {
        if (StringUtils.isEmpty(orderByColumn)) {
            return "";
        }
        return StringUtils.toUnderScoreCase(orderByColumn) + " " + isAsc;
    }

    /**
     * 封装分页对象
     */
    public static Query getPageDate() {
        Query pageDate = new Query();
        pageDate.setPage(ServletUtils.getParameterToInt(PageConfig.PAGE_NAME));
        pageDate.setLimit(ServletUtils.getParameterToInt(PageConfig.LIMIT_NAME));
        pageDate.setOrderByColumn(ServletUtils.getParameter(PageConfig.ORDER_BY_COLUMN));
        pageDate.setIsAsc(ServletUtils.getParameter(PageConfig.IS_ASC));
        return pageDate;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getOrderByColumn() {
        return orderByColumn;
    }

    public void setOrderByColumn(String orderByColumn) {
        this.orderByColumn = orderByColumn;
    }

    public String getIsAsc() {
        return isAsc;
    }

    public void setIsAsc(String isAsc) {
        this.isAsc = isAsc;
    }
}
