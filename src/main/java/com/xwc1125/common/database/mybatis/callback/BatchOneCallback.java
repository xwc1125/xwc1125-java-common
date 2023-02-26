package com.xwc1125.common.database.mybatis.callback;

/**
 * @Description:
 * @Author: xwc1125
 * @Date: 2021/4/23 09:30
 * @Copyright Copyright@2021
 */
public interface BatchOneCallback<T> {

    /**
     * 执行的sql处理
     *
     * @param t
     * @throws Exception
     */
    void exec(T t) throws Exception;
}
