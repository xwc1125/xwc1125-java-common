package com.xwc1125.common.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:
 * @Author: xwc1125
 * @Date: 2019-02-20 17:48
 * @Copyright Copyright@2019
 */
public class JsonUtils {

    private static Logger log = LoggerFactory.getLogger(JsonUtils.class);

    static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public static Gson getGson() {
        return gson;
    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static List<TreeMap<String, Object>> jsonToListMap(String json) throws JsonProcessingException {
        List<TreeMap<String, Object>> treeMaps = JSON.getObjectMapper().readValue(json, new TypeReference<List<TreeMap<String, Object>>>() {
        });
        return treeMaps;
    }

    /**
     * 从json HASH表达式中获取一个map，改map支持嵌套功能
     *
     * @param jsonString
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Map jsonToMap(String jsonString) {
        try {
            return (Map) JSON.unmarshal(jsonString, new TypeReference<HashMap<String, Object>>() {
            });
        } catch (Exception e) {
            log.error("Json解析有误", e);
        }
        return null;
    }
}
