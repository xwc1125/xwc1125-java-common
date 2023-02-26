/**
 *  @Project ZzxApp-1.0 
 *  @Package com.zzx.framework.util.uuid
 *
 * @Title UUIDHexGenerator.java
 * @Description
 * @Copyright Copyright (c) 2016
 * @author xwc1125
 * @date 2016年2月8日 下午9:13:28 
 * @version V1.0
 */
package com.xwc1125.common.util.uuid;

import java.io.Serializable;
import java.util.Properties;
import java.util.UUID;

/**
 * @ClassName UUIDHexGenerator
 * @Description UUID的生成类
 *
 *
 *              根据IP地址，JVM虚拟机，时间，同步序列号的情况下产生的相对唯一ID，可用于多线程，多服务器。 32位 系统的 UUID
 *              生成为36位
 *
 * @author xwc1125
 * @date 2016年2月8日 下午9:13:28
 */
public class UUID32Utils extends UUID32AbstractUUIDGenerator {
    public static final UUID32Utils DEFAULT = new UUID32Utils();

    private String sep = "";// 分隔符
    private String serverNo = "";// 服务器的编号

    protected boolean isEmpty(String... ss) {
        if (ss == null) {
            return true;
        }
        for (String s : ss) {
            if (s == null || s.length() < 1) {
                return true;
            }
        }
        return false;
    }

    protected String format(final int intval) {
        final String formatted = Integer.toHexString(intval);
        final StringBuffer buf = new StringBuffer("00000000");
        buf.replace(8 - formatted.length(), 8, formatted);
        return buf.toString();
    }

    protected String format(final short shortval) {
        final String formatted = Integer.toHexString(shortval);
        final StringBuffer buf = new StringBuffer("0000");
        buf.replace(4 - formatted.length(), 4, formatted);
        return buf.toString();
    }

    // public Serializable generate(final Object obj) {
    public Serializable generate(final String obj) {
        StringBuffer buffer = new StringBuffer();
        if (!isEmpty(obj)) {
            buffer.append(obj).append(sep);
        }
        if (!isEmpty(serverNo)) {
            buffer.append(serverNo).append(sep);
        }
        buffer.append(format(getIP())).append(sep).append(format(getJVM())).append(sep).append(format(getHiTime()))
                .append(sep).append(format(getLoTime())).append(sep).append(format(getCount()));
        return buffer.toString();
    }

    /**
     *
     * @Title configure
     * @Description 配置分隔符和服务器编号
     * @param @param
     *            params
     * @return void
     * @author xwc1125
     * @date 2016年2月14日 上午11:02:10
     */
    public void configure(final Properties params) {
        sep = params.getProperty("separator", "");
        serverNo = params.getProperty("serverNo", "");
    }

    /**
     *
     * <p>
     * Title: generator
     * </p>
     * <p>
     * Description: 生成自定义的uuid
     * </p>
     * <p>
     *
     * </p>
     *
     * @return
     *
     * @author xwc1125
     * @date 2017年3月19日 上午8:19:59
     */
    public static final String generator() {
        return String.valueOf(UUID32Utils.DEFAULT.generate(null));
    }

    /**
     *
     * <p>
     * Title: get32UUID
     * </p>
     * <p>
     * Description: 获取系统UUID的方法
     * </p>
     * <p>
     *
     * </p>
     *
     * @return
     *
     * @author xwc1125
     * @date 2017年3月19日 上午8:22:41
     */
    public static String get32UUID() {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }

    // public static final String generator(final Object obj) {
    // return String.valueOf(UUID32Utils.DEFAULT.generate(obj));
    // }
    public static final String generator(final String str) {
        return String.valueOf(UUID32Utils.DEFAULT.generate(str));
    }

    public static void main(final String[] args) {
        String sql6 = UUID32Utils.generator("xwc1125");
        String sql7 = UUID32Utils.generator("xwc1125");
        System.out.println(sql6);
        System.out.println(sql7);
        System.out.println(sql6.length());

        // 32位
        for (int i = 0; i < 10000; i++) {
            String sql = UUID32Utils.generator("xwc1125");
            System.out.println("++" + sql);
            String sql2 = UUID32Utils.generator();
            System.out.println("==" + sql2);
            String sql3 = UUID32Utils.generator();
            System.out.println("&&" + sql3);
            String sql5 = UUID32Utils.generator();
            System.out.println("##" + sql5);
            System.out.println(sql5.length());
        }
    }
}
