package com.xwc1125.common.database.mybatis.config;


/**
 * @Description:
 * @Author: xwc1125
 * @Date: 2019-03-10 16:18
 * @Copyright Copyright@2019
 */
public class PageConfig {
    /**
     * 当前记录起始索引
     */
    public static String PAGE_NAME = "pageNum";

    /**
     * 每页显示记录数
     */
    public static String LIMIT_NAME = "pageSize";

    /**
     * 排序列
     */
    public static String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static String IS_ASC = "isAsc";

    public PageConfig() {
    }

    public static String getPageName() {
        return PAGE_NAME;
    }

    public static void setPageName(String pageName) {
        PAGE_NAME = pageName;
    }

    public static String getLimitName() {
        return LIMIT_NAME;
    }

    public static void setLimitName(String limitName) {
        LIMIT_NAME = limitName;
    }

    public static String getOrderByColumn() {
        return ORDER_BY_COLUMN;
    }

    public static void setOrderByColumn(String orderByColumn) {
        ORDER_BY_COLUMN = orderByColumn;
    }

    public static String getIsAsc() {
        return IS_ASC;
    }

    public static void setIsAsc(String isAsc) {
        IS_ASC = isAsc;
    }
}
