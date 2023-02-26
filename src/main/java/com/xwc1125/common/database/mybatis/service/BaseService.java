package com.xwc1125.common.database.mybatis.service;

import com.xwc1125.common.database.mybatis.callback.BatchCallback;
import com.xwc1125.common.database.mybatis.callback.BatchOneCallback;

import java.util.List;

/**
 * Description:
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/12/21  19:38 <br>
 */
public interface BaseService<T> {

    /**
     * Description: 通过Id查询
     * </p>
     *
     * @param id
     * @return T
     * @Author: xwc1125
     * @Date: 2019-03-07 22:02:05
     */
    T selectById(Object id);

    /**
     * Description: 查询出单条
     * </p>
     *
     * @param entity
     * @return T
     * @Author: xwc1125
     * @Date: 2019-03-07 22:02:25
     */
    T selectOne(T entity);

    /**
     * Description: 查询列表
     * </p>
     *
     * @param entity
     * @return java.util.List<T>
     * @Author: xwc1125
     * @Date: 2019-03-07 22:04:10
     */
    List<T> selectList(T entity);

    /**
     * Description: 获取所有对象
     * </p>
     *
     * @param
     * @return java.util.List<T>
     * @Author: xwc1125
     * @Date: 2019-03-07 22:04:22
     */
    List<T> selectListAll();

    /**
     * Description: 查询总记录数
     * </p>
     *
     * @param entity
     * @return java.lang.Long
     * @Author: xwc1125
     * @Date: 2019-03-07 22:04:31
     */
    Long selectCount(T entity);

    /***
     * Description: 插入
     * </p>
     * @param entity
     *
     * @return int
     * @Author: xwc1125
     * @Date: 2019-03-07 22:04:39
     */
    int insert(T entity);

    /**
     * Description: 插入 不插入null字段
     * </p>
     *
     * @param entity
     * @return int
     * @Author: xwc1125
     * @Date: 2019-03-07 22:04:48
     */
    int insertSelective(T entity);

    /**
     * Description: 根据id更新
     * </p>
     *
     * @param entity
     * @return int
     * @Author: xwc1125
     * @Date: 2019-03-07 22:05:20
     */
    int updateById(T entity);

    /**
     * Description: 不update null
     * </p>
     *
     * @param entity
     * @return int
     * @Author: xwc1125
     * @Date: 2019-03-07 22:05:29
     */
    int updateSelectiveById(T entity);

    /**
     * Description: 删除
     * </p>
     *
     * @param entity
     * @return int
     * @Author: xwc1125
     * @Date: 2019-03-07 22:05:01
     */
    int delete(T entity);

    /**
     * Description: 根据Id删除
     * </p>
     *
     * @param id
     * @return int
     * @Author: xwc1125
     * @Date: 2019-03-07 22:05:09
     */
    int deleteById(Object id);

    /**
     * 批量处理【单对象】
     *
     * @param dataList
     * @param callback
     * @throws Exception
     */
    void batch(List<T> dataList, BatchOneCallback callback) throws Exception;

    /**
     * 批量处理
     *
     * @param callback
     * @throws Exception
     */
    void batch(BatchCallback callback) throws Exception;
}
