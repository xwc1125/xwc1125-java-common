package com.xwc1125.common.database.mybatis.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xwc1125.common.database.mybatis.config.PageConfig;
import com.xwc1125.common.database.mybatis.entity.Query;
import com.xwc1125.common.database.mybatis.callback.BatchCallback;
import com.xwc1125.common.database.mybatis.callback.BatchOneCallback;
import com.xwc1125.common.database.mybatis.service.BaseService;
import com.xwc1125.common.util.string.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.BaseMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description:
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/12/21  19:38 <br>
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> implements BaseService<T> {

    @Autowired
    protected M baseMapper;
    @Resource
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public T selectOne(T entity) {
        return baseMapper.selectOne(entity);
    }

    @Override
    public T selectById(Object id) {
        return baseMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<T> selectList(T entity) {
        return baseMapper.select(entity);
    }

    @Override
    public List<T> selectListAll() {
        return baseMapper.selectAll();
    }

    @Override
    public Long selectCount(T entity) {
        return Long.valueOf(baseMapper.selectCount(entity));
    }

    @Override
    public int insert(T t) {
        return baseMapper.insert(t);
    }

    @Override
    public int insertSelective(T entity) {
        return baseMapper.insertSelective(entity);
    }

    @Override
    public int updateById(T entity) {
        return baseMapper.updateByPrimaryKey(entity);
    }

    @Override
    public int updateSelectiveById(T entity) {
        return baseMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public int delete(T entity) {
        return baseMapper.delete(entity);
    }

    @Override
    public int deleteById(Object id) {
        return baseMapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量处理【单对象】
     *
     * @param dataList
     * @param callback
     * @throws Exception
     */
    @Override
    public void batch(List<T> dataList, BatchOneCallback callback) throws Exception {
        if (dataList == null || dataList.size() == 0) {
            throw new Exception("data is empty");
        }
        if (callback == null) {
            throw new Exception("callback is null");
        }
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        for (int i = 0; i < dataList.size(); i++) {
            callback.exec(dataList.get(i));
        }
        sqlSession.commit();
        sqlSession.clearCache();
        sqlSession.close();
    }

    /**
     * 批量处理
     *
     * @param callback
     * @throws Exception
     */
    @Override
    public void batch(BatchCallback callback) throws Exception {
        if (callback == null) {
            throw new Exception("callback is null");
        }
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        callback.exec(sqlSession);
        sqlSession.commit();
        sqlSession.clearCache();
        sqlSession.close();
    }

    /**
     * 启动分页查询
     */
    protected Page<T> startPage(Query params) {
        if (StringUtils.isEmpty(params.get(PageConfig.PAGE_NAME).toString())) {
            params.put("pageNum", "1");
        }
        if (StringUtils.isEmpty(params.get(PageConfig.LIMIT_NAME).toString())) {
            params.put("pageSize", "10");
        }
//        if (StringUtils.isEmpty(params.get(PageConfig.ORDER_BY_COLUMN).toString())) {
//            params.put("orderBy", "id desc");
//        }
        return PageHelper.startPage(params);
    }

    /**
     * 启动分页查询
     *
     * @return
     */
    protected Page<T> startPage(int pageNum, int pageSize) {
        return PageHelper.startPage(pageNum, pageSize);
    }
}
