package com.xwc1125.common.util.json;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: jackson需要平铺的数据
 * @Author: xwc1125
 * @Date: 2021/2/24 15:29
 * @Copyright Copyright@2021
 * </br>
 * @JsonInclude(JsonInclude.Include.NON_EMPTY): 为空的不序列化
 * @JsonIgnoreProperties: 忽略指定字段
 * @JsonIgnore: 忽略字段
 * @JsonAnySetter: 用于属性或者方法，设置未反序列化的属性名和值作为键值存储到 map 中
 * @JsonAnyGetter: 用于方法 ，获取所有未序列化的属性
 * @JsonProperty: 重新定义字段
 * @JsonAlias: 别名
 * @JsonValue: 对象序列化时，取只取某值
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(value = "password")
public class Any {

    public Any() {
    }

    @JsonIgnore
    private Map<String, Object> any = new HashMap<>();

    /**
     * 用于属性或者方法，设置未反序列化的属性名和值作为键值存储到 map 中
     *
     * @param key
     * @param value
     */
    @JsonAnySetter
    public void addAny(String key, Object value) {
        any.put(key, value);
    }

    /**
     * 用于方法 ，获取所有未序列化的属性
     *
     * @return
     */
    @JsonAnyGetter
    public Map<String, Object> any() {
        return any;
    }

    public Object any(String key) {
        return this.any.get(key);
    }

    public Map<String, Object> getAny() {
        return any;
    }

    public void setAny(Map<String, Object> any) {
        this.any = any;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"any\":")
                .append(any);
        sb.append('}');
        return sb.toString();
    }
}
