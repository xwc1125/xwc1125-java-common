package com.xwc1125.common.entity.response;

import java.util.HashMap;

/**
 * Description: 返回Hash对象
 * </p>
 *
 * @Author: xwc1125
 * @Date: 2021-04-23 09:48:23
 * @Copyright Copyright@2021
 */
public class ResponseMap extends HashMap<String, Object> {

    /**
     * 初始化一个新创建的 Message 对象
     */
    public ResponseMap() {
    }

    /**
     * 返回成功消息
     *
     * @param key   键值
     * @param value 内容
     * @return 成功消息
     */
    @Override
    public ResponseMap put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
