package com.xwc1125.common.database.mybatis.mapper;

import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Description:
 * IdsMapper 根据主建批量插入，批量删除
 * ConditionMapper 条件处理
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/12/6  18:24 <br>
 */
public interface BaseDao<T> extends Mapper<T>, IdsMapper<T>, ConditionMapper<T>, MySqlMapper<T> {
    // 这里可以放一些公共的方法
}
