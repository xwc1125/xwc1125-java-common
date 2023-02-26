package com.xwc1125.common.entity;

import com.xwc1125.common.util.json.JSONObject;
import com.xwc1125.common.util.json.JsonUtils;

/**
 * @author xwc1125
 * @Description
 * @date 2016年3月28日 下午2:39:57
 */
public class DataInfo extends JSONObject {

    public DataInfo() {
        this.putData("t", System.currentTimeMillis());
    }

    public JSONObject putData(String name, Object value) {
        try {
            return (JSONObject) super.put(name, value);
        } catch (Exception e) {
            return this;
        }
    }

    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }
}
