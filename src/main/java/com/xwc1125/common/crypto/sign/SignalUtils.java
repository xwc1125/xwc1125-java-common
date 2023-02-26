package com.xwc1125.common.crypto.sign;

import com.xwc1125.common.util.json.JsonUtils;
import com.xwc1125.common.util.base64.Base64Utils;
import com.xwc1125.common.crypto.md5.MD5Utils;
import com.xwc1125.common.crypto.shift.ShiftUtils;
import com.xwc1125.common.util.string.StringUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通用密钥处理类
 *
 * @author xwc1125
 * @date 2015-7-13下午1:43:03
 */
public class SignalUtils {

    private static Logger log = LoggerFactory.getLogger(SignalUtils.class);
    private static final String VALUE_CHARSET = "UTF-8";

    public static TreeMap<String, String> paramToTreeMap(String paramStr) {
        String[] params = paramStr.split("&");
        TreeMap<String, String> resMap = new TreeMap<>();
        for (int i = 0; i < params.length; i++) {
            String[] param = params[i].split("=");
            if (param.length >= 2) {
                String key = param[0];
                String value = URLDecoder.decode(param[1]);
                for (int j = 2; j < param.length; j++) {
                    value += "=" + param[j];
                }
                resMap.put(key, value);
            }
        }
        return resMap;
    }

    public static Map<String, String> paramToMap(String paramStr) {
        String[] params = paramStr.split("&");
        Map<String, String> resMap = new HashMap<>();
        for (int i = 0; i < params.length; i++) {
            String[] param = params[i].split("=");
            if (param.length >= 2) {
                String key = param[0];
                String value = URLDecoder.decode(param[1]);
                for (int j = 2; j < param.length; j++) {
                    value += "=" + param[j];
                }
                resMap.put(key, value);
            }
        }
        return resMap;
    }

    /**
     * <p>
     * Title: createSign
     * </p>
     * <p>
     * Description: 生成签名sign
     * </p>
     * <p>
     *
     * </p>
     *
     * @param tm
     * @param key
     * @return
     * @author xwc1125
     * @date 2015-7-13下午1:43:16
     */
    public static String createSign(TreeMap<String, ?> tm, String key) {
        StringBuffer buf;
        if (StringUtils.isEmpty(key)) {
            buf = new StringBuffer();
        } else {
            buf = new StringBuffer(key);
        }
        for (Entry<String, ?> en : tm.entrySet()) {
            String name = en.getKey();
            String value = en.getValue() + "";
            if (value != null && value.length() > 0 && !"null".equals(value)) {
                buf.append(name).append('=').append(value).append('&');
            }
        }
        String _buf = buf.toString();
        return _buf.substring(0, _buf.length() - 1);
    }

    public static String createSignCharset(TreeMap<String, ?> tm, String key, String charset)
            throws UnsupportedEncodingException {
        if (StringUtils.isEmpty(charset)) {
            charset = "utf-8";
        }
        StringBuffer buf;
        if (StringUtils.isEmpty(key)) {
            buf = new StringBuffer();
        } else {
            buf = new StringBuffer(key);
        }
        for (Entry<String, ?> en : tm.entrySet()) {
            String name = en.getKey();
            String value = en.getValue() + "";
            if (value != null && value.length() > 0 && !"null".equals(value)) {
                buf.append(name).append('=').append(URLEncoder.encode(value, charset)).append('&');
            }
        }
        String _buf = buf.toString();
        return _buf.substring(0, _buf.length() - 1);
    }

    /**
     * <p>
     * Title: CalculateSign
     * </p>
     * <p>
     * Description: 提取HttpServletRequest的参数;并去除空值
     * </p>
     * <p>
     *
     * </p>
     *
     * @param request
     * @return
     * @author xwc1125
     * @date 2015-7-13下午1:49:56
     */
    public static TreeMap<String, String> CalculateSign(HttpServletRequest request) {
        try {
            Map<String, String[]> rmap = request.getParameterMap();
            TreeMap<String, String> tm = new TreeMap<String, String>();
            for (Entry<String, String[]> en : rmap.entrySet()) {
                String key = en.getKey();
                if (key != null && !"file".equals(key) && !key.startsWith("_")) {
                    String[] vs = en.getValue();
                    if (vs != null && vs.length > 0) {
                        String value = vs[0];
                        if (value != null && value.length() > 0 && !"null".equals(value)) {
                            tm.put(key, value);
                        }
                    }
                }
            }
            return tm;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static void CalculateHeader(HttpServletRequest request, TreeMap<String, String> tm) {
        if (tm == null) {
            tm = new TreeMap<String, String>();
        }
        List<String> headerKey = new ArrayList<String>();
        headerKey.add("sign");
        headerKey.add("nettype");
        headerKey.add("api-protocol");
        headerKey.add("rsa");

        System.out.print("\n--------------Hearder----------------------");
        String api_protocol = "";// 协议版本
        @SuppressWarnings("rawtypes")
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            System.out.print("\n【Hearder】" + key + "=" + value);
            if (StringUtils.isNotEmpty(key, value)) {
                if (key.equals("api-protocol")) {
                    api_protocol = value;
                } else {
                    if (StringUtils.isListContain(key, headerKey)) {
                        tm.put(key, value);
                    }
                }
            }
        }
        System.out.print("\n--------------Hearder Finish----------------------\n\n");
    }

    /**
     * @param @param  request
     * @param @return
     * @return TreeMap<String, String>
     * @Title CalculateSignBase64
     * @Description 手机端传过来的数据是base64.其中app*，sign的数据没有进行base64
     * @author xwc1125
     * @date 2016年3月8日 下午3:51:38
     */
    public static TreeMap<String, String> CalculateSignBase64(HttpServletRequest request) {
        return CalculateSignBase64(request, "sign");
    }

    /**
     * @param @param  request
     * @param @param  sign 其中所有的sign是不会被base64的
     * @param @return
     * @return TreeMap<String, String>
     * @Title CalculateSignBase64
     * @Description 手机端传过来的数据是base64.其中app*，sign的数据没有进行base64
     * @author xwc1125
     * @date 2016年3月8日 下午3:52:18
     */
    public static TreeMap<String, String> CalculateSignBase64(HttpServletRequest request, String... sign) {
        if (StringUtils.isEmpty(sign)) {
            sign = new String[]{"sign"};
        }
        try {
            Map<String, String[]> rmap = request.getParameterMap();
            TreeMap<String, String> tm = new TreeMap<String, String>();
            for (Entry<String, String[]> en : rmap.entrySet()) {
                String key = en.getKey();
                if (key != null && !"file".equals(key) && !key.startsWith("_")) {
                    String[] vs = en.getValue();
                    if (vs != null && vs.length > 0) {
                        String value = vs[0];
                        if (value != null && value.length() > 0 && !"null".equals(value)) {
                            if (key.startsWith("app") || isSign(key, sign)) {
                                tm.put(key, value);
                            } else {
                                tm.put(key, new String(Base64Utils.decode(value)));
                            }
                        }
                    }
                }
            }
            return tm;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * <p>
     * Title: CalculateSignByOneKey
     * </p>
     * <p>
     * Description: 提取HttpServletRequest的参数;并去除空值
     * </p>
     * <p>
     * 传过来的数据全部被存在主键中
     * </p>
     *
     * @tags @param request
     * @tags @return
     * @author xwc1125
     * @date 2015年9月8日下午3:35:54
     */
    @SuppressWarnings("unchecked")
    public static TreeMap<String, String> CalculateSignByOneKey(HttpServletRequest request) {
        Map<String, String[]> rmap = request.getParameterMap();
        TreeMap<String, String> tm = new TreeMap<String, String>();
        for (Entry<String, String[]> en : rmap.entrySet()) {
            String key = en.getKey();
            log.info("传过来的数据全部被存在主键中：" + key);
            if (StringUtils.isNotEmpty(key)) {
                Map<String, Object> mp = JsonUtils.jsonToMap(key);
                if (mp == null || mp.size() == 0) {
                    return tm;
                }
                for (Entry<String, Object> mEntry : mp.entrySet()) {
                    String key2 = mEntry.getKey();
                    if (key2 != null && !"file".equals(key2) && !key2.startsWith("_")) {
                        Object vs = mEntry.getValue();
                        if (vs != null) {
                            String value2 = vs.toString();
                            if (value2 != null && value2.length() > 0 && !"null".equals(value2)) {
                                tm.put(key2, value2);
                            }
                        }
                    }
                }
            }
        }
        return tm;
    }

    public static String getSignKey3(String api, String md5key) {
        api = api.replaceAll("//", "/");
        return ShiftUtils.getKeyValue(md5key, 2) + api + "?";
    }

    public static String getSignKey(String api, String md5key) {
        api = api.replaceAll("//", "/");
        if (api.contains("//")) {
            return getSignKey(api, md5key);
        }
        return md5key + api + "?";
    }

    /**
     * @param @param  key
     * @param @param  sign
     * @param @return
     * @return boolean
     * @Title isSign
     * @Description 判断key是否是sign
     * @author xwc1125
     * @date 2016年3月8日 下午3:50:38
     */
    private static boolean isSign(String key, String... sign) {
        if (StringUtils.isEmpty(sign) || StringUtils.isEmpty(key)) {
            return false;
        } else {
            for (String str : sign) {
                if (StringUtils.isNotEmpty(str) && str.equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param @param  request
     * @param @param  sign 其中所有的sign是不会被base64的
     * @param @return
     * @return TreeMap<String, String>
     * @Title CalculateSignBase64
     * @Description 手机端传过来的数据是base64.其中app*，sign的数据没有进行base64
     * @author xwc1125
     * @date 2016年3月8日 下午3:52:18
     */
    public static TreeMap<String, String> CalculateSignBase64_V3(HttpServletRequest request) {
        return CalculateSignBase64_V3(request, null);
    }

    public static TreeMap<String, String> CalculateSignBase64_V3(HttpServletRequest request,
                                                                 List<String> filterProcotolList) {
        TreeMap<String, String> tm = new TreeMap<String, String>();
        if (filterProcotolList == null) {
            filterProcotolList = new ArrayList<String>();
        }
        filterProcotolList.add("sign");
        filterProcotolList.add("nettype");
        List<String> headerKey = new ArrayList<String>();
        headerKey.add("sign");
        headerKey.add("nettype");
        headerKey.add("api-protocol");
        headerKey.add("rsa");

        System.out.print("\n--------------Hearder----------------------");
        String api_protocol = "";// 协议版本
        @SuppressWarnings("rawtypes")
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            System.out.print("\n【Hearder】" + key + "=" + value);
            if (StringUtils.isNotEmpty(key, value)) {
                if (key.equals("api-protocol")) {
                    api_protocol = value;
                } else {
                    if (StringUtils.isListContain(key, headerKey)) {
                        if (StringUtils.isListContain(key, filterProcotolList)) {
                            tm.put(key, value);
                        } else {
                            tm.put(key, new String(Base64Utils.decode(value)));
                        }
                    }
                }
            }
        }
        System.out.print("\n--------------Hearder Finish----------------------\n\n");
        try {
            Map<String, String[]> rmap = request.getParameterMap();
            for (Entry<String, String[]> en : rmap.entrySet()) {
                String key = en.getKey();
                if (key != null && !"file".equals(key) && !key.startsWith("_")) {
                    String[] vs = en.getValue();
                    if (vs != null && vs.length > 0) {
                        String value = vs[0];
                        if (value != null && value.length() > 0 && !"null".equals(value)) {
                            if (StringUtils.isNotEmpty(api_protocol) && api_protocol.equals("1.1")) {
                                if (StringUtils.isListContain(key, headerKey)) {
                                    continue;
                                }
                            }
                            if (StringUtils.isListContain(key, filterProcotolList)) {
                                tm.put(key, value);
                            } else {
                                tm.put(key, new String(Base64Utils.decode(value)));
                            }
                        }
                    }
                }
            }
            return tm;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static TreeMap<String, String> CalculateSignDroid(HttpServletRequest request) {
        TreeMap<String, String> tm = new TreeMap<String, String>();
        List<String> headerKey = new ArrayList<String>();
        headerKey.add("sign");
        headerKey.add("nettype");
        headerKey.add("api-protocol");
        headerKey.add("rsa");

        System.out.print("\n--------------Hearder----------------------");
        String api_protocol = "";// 协议版本
        @SuppressWarnings("rawtypes")
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            System.out.print("\n【Hearder】" + key + "=" + value);
            if (StringUtils.isNotEmpty(key, value)) {
                if (key.equals("api-protocol")) {
                    api_protocol = value;
                } else {
                    if (StringUtils.isListContain(key, headerKey)) {
                        tm.put(key, value);
                    }
                }
            }
        }
        System.out.print("\n--------------Hearder Finish----------------------\n\n");
        try {
            Map<String, String[]> rmap = request.getParameterMap();
            for (Entry<String, String[]> en : rmap.entrySet()) {
                String key = en.getKey();
                if (key != null && !"file".equals(key) && !key.startsWith("_")) {
                    String[] vs = en.getValue();
                    if (vs != null && vs.length > 0) {
                        String value = vs[0];
                        if (value != null && value.length() > 0 && !"null".equals(value)) {
                            if (StringUtils.isNotEmpty(api_protocol) && api_protocol.equals("1.1")) {
                                if (StringUtils.isListContain(key, headerKey)) {
                                    continue;
                                }
                            }
                            tm.put(key, value);
                        }
                    }
                }
            }
            return tm;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static TreeMap<String, String> CalculateSignWebBase64_V3(HttpServletRequest request) {
        return CalculateSignWebBase64_V3(request, null);
    }

    public static TreeMap<String, String> CalculateSignIOS(HttpServletRequest request) {
        TreeMap<String, String> tm = new TreeMap<String, String>();
        List<String> headerKey = new ArrayList<String>();
        headerKey.add("sign");
        headerKey.add("nettype");
        headerKey.add("api-protocol");
        headerKey.add("rsa");

        System.out.print("\n--------------Hearder----------------------");
        String api_protocol = "";// 协议版本
        @SuppressWarnings("rawtypes")
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            System.out.print("\n【Hearder】" + key + "=" + value);
            if (StringUtils.isNotEmpty(key, value)) {
                if (key.equals("api-protocol")) {
                    api_protocol = value;
                } else {
                    if (StringUtils.isListContain(key, headerKey)) {
                        tm.put(key, value);
                    }
                }
            }
        }
        System.out.print("\n--------------Hearder Finish----------------------\n\n");
        try {
            Map<String, String[]> rmap = request.getParameterMap();
            for (Entry<String, String[]> en : rmap.entrySet()) {
                String key = en.getKey();
                if (key != null && !"file".equals(key) && !key.startsWith("_")) {
                    String[] vs = en.getValue();
                    if (vs != null && vs.length > 0) {
                        String value = vs[0];
                        if (value != null && value.length() > 0 && !"null".equals(value)) {
                            if (StringUtils.isNotEmpty(api_protocol) && api_protocol.equals("1.1")) {
                                if (StringUtils.isListContain(key, headerKey)) {
                                    continue;
                                }
                            }
                            tm.put(key, value);
                        }
                    }
                }
            }
            return tm;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * @param @param  request
     * @param @param  filterList
     * @param @return
     * @return TreeMap<String, String>
     * @Title CalculateSignWebBase64_V3
     * @Description 来自网页的加密方法
     * @author xwc1125
     * @date 2016年4月22日 上午9:58:43
     */
    public static TreeMap<String, String> CalculateSignIOSBase64_V3(HttpServletRequest request,
                                                                    List<String> filterList) {
        TreeMap<String, String> tm = new TreeMap<String, String>();
        if (filterList == null) {
            filterList = new ArrayList<String>();
        }
        filterList.add("sign");
        // filterList.add("nettype");
        List<String> headerKey = new ArrayList<String>();
        headerKey.add("sign");
        // headerKey.add("nettype");
        System.out.print("\n--------------Hearder----------------------");
        String api_protocol = "";// 协议版本
        @SuppressWarnings("rawtypes")
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            System.out.print("\n【Hearder】" + key + "=" + value);
            if (StringUtils.isNotEmpty(key, value)) {
                if (key.equals("api-protocol")) {
                    api_protocol = value;
                    System.out.println(api_protocol);
                } else {
                    if (StringUtils.isListContain(key, headerKey)) {
                        if (StringUtils.isListContain(key, filterList)) {
                            tm.put(key, value);
                        } else {
                            tm.put(key, new String(Base64Utils.decode(value)));
                        }
                    }
                }
            }
        }
        System.out.print("\n--------------Hearder Finish----------------------\n\n");

        try {
            Map<String, String[]> rmap = request.getParameterMap();
            for (Entry<String, String[]> en : rmap.entrySet()) {
                String key = en.getKey();
                if (key != null && !"file".equals(key) && !key.startsWith("_")) {
                    String[] vs = en.getValue();
                    if (vs != null && vs.length > 0) {
                        String value = vs[0];
                        if (value != null && value.length() > 0 && !"null".equals(value)) {
                            if (StringUtils.isListContain(key, filterList)) {
                                tm.put(key, value);
                            } else {
                                // MyBase64Utils.decode_toStr(value);
                                // byte[] b =Base64Utils.decode(value);
                                // String s = new String(b, "utf-8");
                                String s = new String(Base64Utils.decode(value));
                                tm.put(key, s);
                            }
                        }
                    }
                }
            }
            return tm;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * @param @param  request
     * @param @param  filterList
     * @param @return
     * @return TreeMap<String, String>
     * @Title CalculateSignWebBase64_V3
     * @Description 来自网页的加密方法
     * @author xwc1125
     * @date 2016年4月22日 上午9:58:43
     */
    public static TreeMap<String, String> CalculateSignWebBase64_V3(HttpServletRequest request,
                                                                    List<String> filterList) {
        TreeMap<String, String> tm = new TreeMap<String, String>();
        if (filterList == null) {
            filterList = new ArrayList<String>();
        }
        filterList.add("sign");
        filterList.add("nettype");
        filterList.add("logUuid");
        List<String> headerKey = new ArrayList<String>();
        headerKey.add("sign");
        headerKey.add("nettype");
        System.out.print("\n--------------Hearder----------------------");
        String api_protocol = "";// 协议版本
        @SuppressWarnings("rawtypes")
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            System.out.print("\n【Hearder】" + key + "=" + value);
            if (StringUtils.isNotEmpty(key, value)) {
                if (key.equals("api-protocol")) {
                    api_protocol = value;
                    System.out.println(api_protocol);
                } else {
                    if (StringUtils.isListContain(key, headerKey)) {
                        if (StringUtils.isListContain(key, filterList)) {
                            tm.put(key, value);
                        } else {
                            tm.put(key, new String(Base64Utils.decode(value)));
                        }
                    }
                }
            }
        }
        System.out.print("\n--------------Hearder Finish----------------------\n\n");
        try {
            Map<String, String[]> rmap = request.getParameterMap();
            for (Entry<String, String[]> en : rmap.entrySet()) {
                String key = en.getKey();
                if (key != null && !"file".equals(key) && !key.startsWith("_")) {
                    String[] vs = en.getValue();
                    if (vs != null && vs.length > 0) {
                        String value = vs[0];
                        if (value != null && value.length() > 0 && !"null".equals(value)) {
                            if (StringUtils.isListContain(key, filterList)) {
                                tm.put(key, value);
                            } else {
                                byte[] b = Base64Utils.decode(value);
                                String s = new String(b, "utf-8");
                                tm.put(key, s);
                            }
                        }
                    }
                }
            }
            return tm;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * <p>
     * Title: getHttpParames
     * </p>
     * <p>
     * Description: 获取参数消息
     * </p>
     * <p>
     *
     * </p>
     *
     * @param tm
     * @return
     * @author zhangqy
     * @date 2016年1月20日下午6:01:56
     */
    public static String getHttpParames(Map<String, Object> tm, List<String> filter, int base64Type) {
        StringBuilder stringBuilder = null;
        try {
            if (tm == null || tm.size() < 1) {
                return null;
            }
            stringBuilder = new StringBuilder();
            for (Entry<String, Object> entry : tm.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value != null && StringUtils.isNotEmpty(key)) {
                    String encodeValue = URLEncoder.encode(value.toString(), VALUE_CHARSET);
                    if (StringUtils.isListContain(key, filter) || base64Type == 0) {
                        // 如果是App的固有属性，则不进行mybase64加密
                        stringBuilder.append(key).append("=").append(encodeValue).append("&");
                    } else {
                        if (base64Type == 1) {
                            // 其他的进行base64加密
                            stringBuilder.append(key).append("=").append(new String(Base64Utils.decode(encodeValue)))
                                    .append("&");
                        } else if (base64Type == 2) {
                            stringBuilder.append(key).append("=").append(Base64Utils.encode(encodeValue.getBytes()))
                                    .append("&");
                        }
                    }
                }
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        } catch (Exception e) {
        }
        return stringBuilder.toString();
    }

    public static String getHttpParames(Map<String, Object> tm) {
        StringBuilder stringBuilder = null;
        try {
            if (tm == null || tm.size() < 1) {
                return null;
            }
            stringBuilder = new StringBuilder();
            for (Entry<String, Object> entry : tm.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value != null && StringUtils.isNotEmpty(key)) {
                    String encodeValue = URLEncoder.encode(value.toString(), VALUE_CHARSET);
                    stringBuilder.append(key).append("=").append(encodeValue).append("&");
                }
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        } catch (Exception e) {
        }
        return stringBuilder.toString();
    }

    /**
     * @param @param  uri
     * @param @param  paramMap
     * @param @param  md5Key
     * @param @return
     * @return String
     * @Title sign
     * @Description 签名
     * @author xwc1125
     * @date 2016年5月4日 下午4:34:45
     */
    public static String sign(String uri, Map<String, ?> paramMap, String md5Key) {
        if (paramMap == null) {
            return null;
        }
        StringBuffer buf = new StringBuffer(md5Key);
        buf.append(uri).append('?');
        for (Entry<String, ?> entry : paramMap.entrySet()) {
            String name = entry.getKey();
            String value = entry.getValue() + "";
            if (entry.getValue() != null && value.length() > 0 && !"null".equals(value)) {
                if (!"sign".equals(name) && !name.startsWith("_") && !"file".equals(name)) {
                    buf.append(name).append('=').append(entry.getValue()).append('&');
                }
            }
        }
        if (buf.charAt(buf.length() - 1) == '&') {
            buf.deleteCharAt(buf.length() - 1);
        }
        String sign = MD5Utils.Encrypt(buf.toString());
        return sign;
    }

    /**
     * <p>
     * Title: SignAuth
     * </p>
     * <p>
     * Description: sign密钥认证
     * </p>
     * <p>
     *
     * </p>
     *
     * @param treeMap
     * @param _key
     * @return 返回ture 说明认证是正确的
     * @author xwc1125
     * @date 2015-7-20下午1:26:30
     */
    public static boolean AuthBySign(TreeMap<String, String> treeMap, String _key) {
        log.info("签名key【" + _key + "】");
        // 获得参数秘钥 清除删除列表中sign
        String sign = treeMap.get("sign");
        if (sign != null) {
            treeMap.remove("sign");// 清除删除列表中sign
            String temSign = SignalUtils.createSign(treeMap, _key);// 进行运算sign并md5加密
            // 与传入值对
            String _sign = MD5Utils.Encrypt(temSign);
            if (!sign.equals(_sign)) {
                log.info("校验失败");
                return false;
            } else {
                log.info("校验成功");
                return true;
            }
        } else {
            log.info("校验失败");
            return false;
        }
    }

    /**
     * <p>
     * Title: SignAuth
     * </p>
     * <p>
     * Description: sign密钥认证【根据验签的key进行验签】
     * </p>
     * <p>
     *
     * </p>
     *
     * @param treeMap
     * @param _key
     * @return 返回ture 说明认证是正确的
     * @author xwc1125
     * @date 2015-7-20下午1:26:30
     */
    public static boolean AuthBySign(TreeMap<String, String> treeMap, String _key, String... signLables) {
        log.info("签名key【" + _key + "】");
        if (signLables == null || signLables.length == 0) {
            signLables = new String[]{"sign"};
        }
        boolean flag = true;
        // 获得参数秘钥 清除删除列表中sign
        for (int i = 0; i < signLables.length; i++) {
            String sign = treeMap.get(signLables[i]);
            if (sign != null) {
                treeMap.remove(signLables[i]);// 清除删除列表中sign
                String temSign = SignalUtils.createSign(treeMap, _key);// 进行运算sign并md5加密
                // 与传入值对
                String _sign = MD5Utils.Encrypt(temSign);
                if (!sign.equals(_sign)) {
                    log.info("校验失败");
                    flag = false;
                    return flag;
                } else {
                    log.info(signLables[i] + "校验成功");
                }
            } else {
                log.info("校验失败");
                flag = false;
                return flag;
            }
        }
        return flag;
    }
}
