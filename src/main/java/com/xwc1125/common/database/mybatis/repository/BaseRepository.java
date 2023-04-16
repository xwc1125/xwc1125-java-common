package com.xwc1125.common.database.mybatis.repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xwc1125.common.database.mybatis.callback.BatchCallback;
import com.xwc1125.common.database.mybatis.callback.BatchOneCallback;
import com.xwc1125.common.database.mybatis.entity.PageData;
import com.xwc1125.common.database.mybatis.entity.PageQuery;
import com.xwc1125.common.database.mybatis.mapper.BaseDao;
import com.xwc1125.common.util.sql.EntityUtils;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

/**
 * @Description: 直接继承就可用
 * @Author: xwc1125
 * @Date: 2019-03-07 22:13
 * @Copyright Copyright@2019
 */
public abstract class BaseRepository<M extends BaseDao<T>, T> {

    @Autowired
    protected M baseMapper;
    @Resource
    private SqlSessionFactory sqlSessionFactory;

    /**
     * Description: 查询出单条
     * </p>
     *
     * @param entity
     * @return T
     * @Author: xwc1125
     * @Date: 2019-03-07 22:02:25
     */
    public T selectOne(T entity) {
        return baseMapper.selectOne(entity);
    }

    /**
     * Description: 通过Id查询
     * </p>
     *
     * @param id
     * @return T
     * @Author: xwc1125
     * @Date: 2019-03-07 22:02:05
     */
    public T selectById(Object id) {
        return baseMapper.selectByPrimaryKey(id);
    }

    /**
     * 通过Ids查询
     *
     * @param ids
     * @return
     */
    public List<T> selectByIds(Object[] ids) {
        StringBuffer buf = new StringBuffer();
        for (Object s : ids) {
            buf.append("\"" + s + "\",");
        }
        String idsStr = buf.substring(0, buf.length() - 1);
        return baseMapper.selectByIds(idsStr);
    }

    /**
     * Description: 查询列表
     * </p>
     *
     * @param entity
     * @return java.util.List<T>
     * @Author: xwc1125
     * @Date: 2019-03-07 22:04:10
     */
    public List<T> selectList(T entity) {
        return baseMapper.select(entity);
    }

    /**
     * Description: 获取所有对象
     * </p>
     *
     * @param
     * @return java.util.List<T>
     * @Author: xwc1125
     * @Date: 2019-03-07 22:04:22
     */
    public List<T> selectListAll() {
        return baseMapper.selectAll();
    }

    /**
     * 分页返回所有结果
     *
     * @return
     */
    public PageData<T> selectListAllPage() {
        PageQuery pageQuery = PageQuery.getPageDate();
        PageHelper.startPage(pageQuery.getPage(), pageQuery.getLimit());
        List<T> listAll = baseMapper.selectAll();
        PageInfo<T> page = new PageInfo<>(listAll);
        return PageData.parsePageInfo(page);
    }

    /**
     * Description: 查询总记录数
     * </p>
     *
     * @param entity
     * @return java.lang.Long
     * @Author: xwc1125
     * @Date: 2019-03-07 22:04:31
     */
    public Long selectCount(T entity) {
        return Long.valueOf(baseMapper.selectCount(entity));
    }

    /***
     * Description: 插入
     * </p>
     * @param entity
     *
     * @return int
     * @Author: xwc1125
     * @Date: 2019-03-07 22:04:39
     */
    public int insert(T entity) {
        EntityUtils.setCreatAndUpdatInfo(entity);
        return baseMapper.insert(entity);
    }

    /**
     * Description: 插入 不插入null字段
     * </p>
     *
     * @param entity
     * @return int
     * @Author: xwc1125
     * @Date: 2019-03-07 22:04:48
     */
    public int insertSelective(T entity) {
        EntityUtils.setCreatAndUpdatInfo(entity);
        return baseMapper.insertSelective(entity);
    }

    /**
     * Description: 根据id更新
     * </p>
     *
     * @param entity
     * @return int
     * @Author: xwc1125
     * @Date: 2019-03-07 22:05:20
     */
    public int updateById(T entity) {
        EntityUtils.setUpdatedInfo(entity);
        return baseMapper.updateByPrimaryKey(entity);
    }

    /**
     * Description: 不update null
     * </p>
     *
     * @param entity
     * @return int
     * @Author: xwc1125
     * @Date: 2019-03-07 22:05:29
     */
    public int updateSelectiveById(T entity) {
        EntityUtils.setUpdatedInfo(entity);
        return baseMapper.updateByPrimaryKeySelective(entity);
    }

    /**
     * Description: 移除到垃圾箱
     * </p>
     *
     * @param entity
     * @return int
     * @Author: xwc1125
     * @Date: 2019-03-07 22:05:01
     */
    public int deleteToTrash(T entity) {
        EntityUtils.setDeleteInfo(entity);
        return baseMapper.updateByPrimaryKey(entity);
    }

    /**
     * Description: 从数据库彻底删除
     * </p>
     *
     * @param entity
     * @return int
     * @Author: xwc1125
     * @Date: 2019-03-07 22:05:01
     */
    public int delete(T entity) {
        return baseMapper.delete(entity);
    }

    /**
     * Description: 移除到垃圾箱
     * </p>
     *
     * @param entity
     * @return int
     * @Author: xwc1125
     * @Date: 2019-03-07 22:05:09
     */
    public int deleteToTrashById(T entity) {
        EntityUtils.setDeleteInfo(entity);
        return baseMapper.updateByPrimaryKeySelective(entity);
    }

    /**
     * Description: 根据Id从数据库彻底删除
     * </p>
     *
     * @param id
     * @return int
     * @Author: xwc1125
     * @Date: 2019-03-07 22:05:09
     */
    public int deleteById(Object id) {
        return baseMapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    public int deleteByIds(Object[] ids) {
        StringBuffer buf = new StringBuffer();
        for (Object s : ids) {
            buf.append("\"" + s + "\",");
        }
        String idsStr = buf.substring(0, buf.length() - 1);
        return baseMapper.deleteByIds(idsStr);
    }

    /**
     * Description: TODO
     * </p>
     *
     * @param example
     * @return java.util.List<T>
     * @Author: xwc1125
     * @Date: 2019-03-07 22:27:00
     */
    public List<T> selectByExample(Object example) {
        return baseMapper.selectByExample(example);
    }

    /**
     * Description: TODO
     * </p>
     *
     * @param example
     * @return int
     * @Author: xwc1125
     * @Date: 2019-03-07 22:27:05
     */
    public int selectCountByExample(Object example) {
        return baseMapper.selectCountByExample(example);
    }

    /**
     * Description: 分页查询
     * </p>
     *
     * @param pageQuery
     * @return com.xwc1125.common.entity.TableResponseInfo<T>
     * @Author: xwc1125
     * @Date: 2019-03-07 22:53:05
     */
    public PageData<T> selectByQuery(PageQuery pageQuery) {
        Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Example example = new Example(clazz);
        if (pageQuery.entrySet().size() > 0) {
            Example.Criteria criteria = example.createCriteria();
            for (Map.Entry<String, Object> entry : pageQuery.entrySet()) {
                criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
            }
        }
        PageHelper.startPage(pageQuery.getPage(), pageQuery.getLimit());
        List<T> list = baseMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(list);
        return PageData.parsePageInfo(pageInfo);
    }

    /**
     * 批量处理【单对象】
     *
     * @param dataList
     * @param callback
     * @throws Exception
     */
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
}
