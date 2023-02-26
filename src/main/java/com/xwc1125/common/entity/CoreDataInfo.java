package com.xwc1125.common.entity;

import com.xwc1125.common.util.json.JSON;
import com.xwc1125.common.util.json.JSONObject;

import java.util.List;

/**
 * @Description: 服务器逻辑处理的核心数据
 * @Author: xwc1125
 * @Date: 2019-02-21 14:07
 * @Copyright Copyright@2019
 */
public class CoreDataInfo extends JSONObject implements java.io.Serializable {

    public String getString(String key) {
        try {
            return this.get(key).toString();
        } catch (Exception e) {
            return null;
        }
    }

    public Double getDouble(String key) {
        return Double.parseDouble(getString(key));

    }

    public <T> T getObject(String key, Class<T> valueType) throws Exception {
        return JSON.unmarshal(getString(key), valueType);
    }

    public <T> List<T> getObjectList(String key, Class<T> valueType) throws Exception {
        return JSON.unmarshalList(getString(key), valueType);
    }
}
