package com.xwc1125.common.util.treemap;

import com.xwc1125.common.entity.CoreDataInfo;
import com.xwc1125.common.util.string.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author xwc1125
 * @ClassName TreeMapUtils
 * @Description
 * @date 2016年5月5日 下午4:56:26
 */
public class TreeMapUtils {

    /**
     * @param @param  map
     * @param @return
     * @return TreeMap<String, Object>
     * @Title treeMapStr2Obj
     * @Description TreeMap<String, String>To TreeMap<String, Object>
     * @author xwc1125
     * @date 2016年5月5日 下午4:58:57
     */
    public static TreeMap<String, Object> treeMapStr2Obj(TreeMap<String, String> map) {
        TreeMap<String, Object> treeMap = new TreeMap<String, Object>();
        for (Entry<String, String> en : map.entrySet()) {
            String name = en.getKey();
            String value = en.getValue();
            treeMap.put(name, value);
        }
        return treeMap;
    }

    /**
     * @param @param  map
     * @param @return
     * @return TreeMap<String, String>
     * @Title treeMapObj2Str
     * @Description TreeMap<String, Object>To TreeMap<String, String>
     * @author xwc1125
     * @date 2016年5月5日 下午4:59:14
     */
    public static TreeMap<String, String> treeMapObj2Str(TreeMap<String, Object> map) {
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        for (Entry<String, Object> en : map.entrySet()) {
            String name = en.getKey();
            String value = en.getValue() + "";
            treeMap.put(name, value);
        }
        return treeMap;
    }

    /**
     * @param @param treeMap 需要转移的数据
     * @param @param dataMap 得到的数据
     * @param @param isOverride 如果存在相同的字段是否进行重写
     * @return void
     * @Title treeMap2Map
     * @Description treeMap数据转移到dataMap
     * @author xwc1125
     * @date 2016年5月6日 上午9:32:06
     */
    public static void treeMap2Map(TreeMap<String, ?> treeMap, Map<String, String> dataMap, boolean isOverride) {
        for (Entry<String, ?> en : treeMap.entrySet()) {
            String key = en.getKey();
            String value = en.getValue() + "";
            if (isOverride) {
                dataMap.put(key, value);
            } else {
                if (!dataMap.containsKey(key)) {
                    dataMap.put(key, value);
                }
            }
        }
    }

    /**
     * @param @param treeMap
     * @param @param dataMap
     * @param @param isOverride 如果存在相同的字段是否进行重写
     * @return void
     * @Title treeMap2TreeMap
     * @Description treeMap数据转移到dataMap
     * @author xwc1125
     * @date 2016年5月6日 上午9:32:06
     */
    public static void treeMap2TreeMap(TreeMap<String, String> treeMap, TreeMap<String, Object> dataMap,
                                       boolean isOverride) {
        for (Entry<String, String> en : treeMap.entrySet()) {
            String key = en.getKey();
            String value = en.getValue();
            if (isOverride) {
                dataMap.put(key, value);
            } else {
                if (!dataMap.containsKey(key)) {
                    dataMap.put(key, value);
                }
            }
        }
    }

    /**
     * @param @param treeMap
     * @param @param dataMap
     * @param @param isOverride 如果存在相同的字段是否进行重写
     * @return void
     * @Title treeMap2CoreDataInfo3
     * @Description treeMap数据转移到dataMap
     * @author xwc1125
     * @date 2016年5月6日 上午9:32:06
     */
    public static void treeMap2CoreDataInfo3(TreeMap<String, String> treeMap, CoreDataInfo coreDataInfo3,
                                             boolean isOverride) {
        if (coreDataInfo3 == null) {
            coreDataInfo3 = new CoreDataInfo();
        }
        System.out.println("【Note】treemap转移到coredatainfo(+" + isOverride + "+)==>\ntreemap=" + treeMap
                + "\ncoreDataInfo3" + coreDataInfo3);
        for (Entry<String, String> en : treeMap.entrySet()) {
            String key = en.getKey();
            String value = en.getValue();
            if (isOverride) {
                coreDataInfo3.put(key, value);
            } else {
                if (!coreDataInfo3.containsKey(key)) {
                    coreDataInfo3.put(key, value);
                }
            }
        }
        System.out.println("【Note】treemap转移到coredatainfo后台" + "==>" + coreDataInfo3);
    }

    /**
     * <p>
     * Description: map转成字符串
     * </p>
     * <p>
     * <p>
     * </p>
     *
     * @param map
     * @param linkTag   连接符（默认=）
     * @param splitTag  分割符（默认&）
     * @param isURLCode 是否使用URLEncode
     * @param charset   默认（UTF-8）
     * @return
     * @author xwc1125
     * @date 2017年8月15日 下午4:09:07
     */
    @SuppressWarnings("rawtypes")
    public static String transMapToString(Map map, String linkTag, String splitTag, boolean isURLCode, String charset) {
        Entry entry;
        StringBuffer sb = new StringBuffer();
        if (StringUtils.isEmpty(linkTag)) {
            linkTag = "=";
        }
        if (StringUtils.isEmpty(splitTag)) {
            splitTag = "&";
        }
        if (StringUtils.isEmpty(charset)) {
            charset = "UTF-8";
        }
        for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); ) {
            entry = (Entry) iterator.next();
            String key = entry.getKey() + "";
            Object value = entry.getValue();
            String valueString = "";
            if (value != null) {
                valueString = value.toString();
                if (isURLCode) {
                    try {
                        valueString = URLEncoder.encode(valueString, charset);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
            sb.append(key).append(linkTag).append(valueString).append(iterator.hasNext() ? splitTag : "");
        }
        return sb.toString();
    }

    /**
     * <p>
     * Description: 字符串转成Map
     * </p>
     * <p>
     * <p>
     * </p>
     *
     * @param mapString
     * @param linkTag   连接符（默认=）
     * @param splitTag  分隔符（默认&）
     * @param isURLCode 是否使用URLEncode
     * @param charset   默认（UTF-8）
     * @return
     * @author xwc1125
     * @date 2017年8月15日 下午4:08:04
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Map transStringToMap(String mapString, String linkTag, String splitTag, boolean isURLCode,
                                       String charset) throws Exception {
        try {
            Map map = new HashMap();
            StringTokenizer items;
            if (StringUtils.isEmpty(linkTag)) {
                linkTag = "=";
            }
            if (StringUtils.isEmpty(splitTag)) {
                splitTag = "&";
            }
            if (StringUtils.isEmpty(charset)) {
                charset = "UTF-8";
            }
            StringTokenizer entrys2 = new StringTokenizer(mapString, splitTag);
            while (entrys2.hasMoreElements()) {
                String token = entrys2.nextToken();
                if (token != null) {
                    items = new StringTokenizer(token, linkTag);
                    if (items.hasMoreTokens()) {
                        String key = items.nextToken();
                        Object value = ((Object) (items.nextToken()));
                        if (value instanceof String) {
                            if (isURLCode) {
                                String valueString = value.toString();
                                try {
                                    valueString = URLDecoder.decode(valueString, charset);
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                map.put(key, valueString);
                            } else {
                                map.put(key, value);
                            }
                        } else {
                            map.put(key, value);
                        }
                    }
                }

            }
            // for (StringTokenizer entrys = new StringTokenizer(mapString,
            // splitTag); entrys.hasMoreTokens(); map
            // .put(items.nextToken(), items.hasMoreTokens() ? ((Object)
            // (items.nextToken())) : null)) {
            // items = new StringTokenizer(entrys.nextToken(), linkTag);
            // }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }
}
