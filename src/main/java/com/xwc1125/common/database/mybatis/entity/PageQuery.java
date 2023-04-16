package com.xwc1125.common.database.mybatis.entity;

import com.github.pagehelper.IPage;
import com.xwc1125.common.util.servlet.ServletUtils;
import com.xwc1125.common.util.string.StringUtils;
import com.xwc1125.common.util.string.text.Convert;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description: 查询参数
 * @Author: xwc1125
 * @Date: 2019-03-07 22:22
 * @Copyright Copyright@2019
 */
public class PageQuery extends LinkedHashMap<String, Object> implements IPage {

    /**
     * 当前记录起始索引
     */
    public static String KEY_PAGE_NAME = "pageNum";

    /**
     * 每页显示记录数
     */
    public static String KEY_LIMIT_NAME = "pageSize";

    /**
     * 排序
     */
    public static String KEY_ORDER_BY = "orderBy";
    /**
     * 排序列
     */
    public static String KEY_ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 true:asc, false: desc
     */
    public static String KEY_IS_ASC = "isAsc";

    public final static int PAGE_LIMIT_1 = 1;
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
    private int page = 0;
    /**
     * 每页条数
     */
    private int limit = 10;
    /**
     * orderBy sql语句：如id asc
     */
    private String orderBy;
    /**
     * 排序列，如id
     */
    private String orderByColumn;
    /**
     * 排序的方向: true:asc, false: desc
     */
    private boolean isAsc = false;

    public PageQuery() {

    }

    public PageQuery(Map<String, Object> params) {
        this.putAll(params);
        //分页参数
        if (params.get(KEY_PAGE_NAME) != null) {
            this.page = Integer.parseInt(params.get(KEY_PAGE_NAME).toString());
        }
        if (params.get(KEY_LIMIT_NAME) != null) {
            this.limit = Integer.parseInt(params.get(KEY_LIMIT_NAME).toString());
        }
        if (params.get(KEY_ORDER_BY) != null) {
            this.orderBy = params.get(KEY_ORDER_BY).toString();
        } else if (params.get(KEY_ORDER_BY_COLUMN) != null) {
            this.orderByColumn = params.get(KEY_ORDER_BY_COLUMN).toString();
            Object isAsc = params.get(KEY_IS_ASC);
            if (isAsc != null) {
                if (isAsc instanceof Boolean) {
                    this.isAsc = ((Boolean) isAsc).booleanValue();
                } else if (isAsc instanceof String) {
                    this.isAsc = Convert.toBool(isAsc, false);
                }
            }
        }
        this.remove(KEY_PAGE_NAME);
        this.remove(KEY_LIMIT_NAME);
    }

    /**
     * 封装分页对象
     */
    public static PageQuery getPageDate() {
        PageQuery pageDate = new PageQuery();
        pageDate.setPage(ServletUtils.getParameterToInt(KEY_PAGE_NAME, 0));
        pageDate.setLimit(ServletUtils.getParameterToInt(KEY_LIMIT_NAME, PAGE_LIMIT_10));
        pageDate.setOrderByColumn(ServletUtils.getParameter(KEY_ORDER_BY_COLUMN));
        pageDate.setAsc(ServletUtils.getParameterToBool(KEY_IS_ASC, false));
        return pageDate;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        if (limit == 0) {
            limit = PAGE_LIMIT_10;
        }
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getOrderByColumn() {
        return orderByColumn;
    }

    public void setOrderByColumn(String orderByColumn) {
        this.orderByColumn = orderByColumn;
    }

    public boolean isAsc() {
        return isAsc;
    }

    public void setAsc(boolean asc) {
        isAsc = asc;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    @Override
    public Integer getPageNum() {
        return page;
    }

    @Override
    public Integer getPageSize() {
        if (limit == 0) {
            limit = PAGE_LIMIT_10;
        }
        return limit;
    }

    public String getOrderBy() {
        if (StringUtils.isNotEmpty(orderBy)) {
            return orderBy;
        }
        if (StringUtils.isEmpty(orderByColumn)) {
            return "";
        }
        String ascStr = "desc";
        if (isAsc()) {
            ascStr = "asc";
        }
        return StringUtils.toUnderScoreCase(orderByColumn) + " " + ascStr;
    }
}
