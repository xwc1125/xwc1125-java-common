package com.xwc1125.common.util.excel;

/**
 * @Description:
 * @Author: xwc1125
 * @Date: 2019-03-14 13:25
 * @Copyright Copyright@2019
 */
public interface RowCallback<T> {
    T row(String line);
}
