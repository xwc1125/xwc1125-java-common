package com.xwc1125.common.database.mybatis.callback;

import org.apache.ibatis.session.SqlSession;

/**
 * @Description:
 * @Author: xwc1125
 * @Date: 2021/4/23 09:30
 * @Copyright Copyright@2021
 */
public interface BatchCallback<T> {

    /**
     * 执行的sql处理
     *
     * @param sqlSession
     * @throws Exception
     */
    void exec(SqlSession sqlSession) throws Exception;
}
