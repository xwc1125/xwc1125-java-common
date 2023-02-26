package com.xwc1125.common.util.uuid;

import com.xwc1125.common.util.string.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UUID10Utils {

    private static final Byte[] LOCK = new Byte[0];
    private static int no = 0;

    /**
     * Description：通用ID生成方法
     *
     * @return
     */
    public static String createId() {
        StringBuffer stringBuffer = new StringBuffer();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
        String now = sdf.format(new Date());
        // 应用编号

        int current = 1000;
        synchronized (LOCK) {
            if (no > 9999) {
                no = 1000;
            }
            current += no++;
        }
        stringBuffer.append(now).append(String.valueOf(current).substring(1));
        return stringBuffer.toString();
    }

    /**
     * @param @return
     * @return String
     * @Title create64Id
     * @Description 10位字符串
     * @author xwc1125
     * @date 2016年2月8日 下午11:50:50
     */
    public static String create64Id() {
        Long id = Long.valueOf(createId());
        return UUID10CompressEncoding.CompressNumber(id, 6);
    }

    public static String create64Id(String strId) {
        Long id = Long.valueOf(strId);
        return UUID10CompressEncoding.CompressNumber(id, 6);
    }

    /**
     * URLtoMap
     *
     * @param param
     * @return
     */
    public static Map<String, String> getUrlParams(String param) {
        Map<String, String> map = new HashMap<String, String>(0);
        if (StringUtils.isEmpty(param)) {
            return map;
        }
        String[] params = param.split("&");
        for (int i = 0; i < params.length; i++) {
            String[] p = params[i].split("=");
            if (p.length == 2) {
                map.put(p[0], p[1]);
            }
        }
        return map;
    }

    /**
     * 测试方法
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        String id = createId();
        System.out.println(id);
        for (int i = 0; i < 1000; i++) {
            System.out.println("【" + create64Id(id) + "】");
            System.out.println(create64Id());
            System.out.println("==" + next());
        }

        // Long _id = Long.valueOf("11151028183922608000");
        // String key = CompressEncodeing.CompressNumber(_id,6);
        // System.out.println(key);
        //
        //
        // id = "2"+"151028180018903008";
        // _id = Long.valueOf(id);
        // key = CompressEncodeing.CompressNumber(_id,6);
        // System.out.println(key);
        //
        // id = "3"+"151028180018903008";
        // _id = Long.valueOf(id);
        // key = CompressEncodeing.CompressNumber(_id,6);
        // System.out.println(key);
        //
        // id = "4"+"151028180018903008";
        // _id = Long.valueOf(id);
        // key = CompressEncodeing.CompressNumber(_id,6);
        // System.out.println(key);
    }

    private static Date date = new Date();
    private static StringBuilder buf = new StringBuilder();
    private static int seq = 0;
    private static final int ROTATION = 99999;

    /**
     * @param @return
     * @return long
     * @Title next
     * @Description 生成19位的字符串
     * @author xwc1125
     * @date 2016年2月8日 下午11:49:56
     */
    public static synchronized long next() {
        if (seq > ROTATION) {
            seq = 0;
        }
        buf.delete(0, buf.length());
        date.setTime(System.currentTimeMillis());
        String str = String.format("%1$tY%1$tm%1$td%1$tk%1$tM%1$tS%2$05d",
                date, seq++);
        return Long.parseLong(str);
    }
}
