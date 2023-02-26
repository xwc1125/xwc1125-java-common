package com.xwc1125.common.util.fmt;

/**
 * @Description:
 * @Author: xwc1125
 * @Date: 2020/12/16 13:44
 * @Copyright Copyright@2020
 */
public class Fmt {

    public static String Info(String msg, Object... kvs) {
        StringBuilder builder = new StringBuilder(msg);
        builder.append("    ");
        if (kvs != null && kvs.length > 0) {
            int length = kvs.length;
            for (int i = 0; i < length; i = i + 2) {
                if (i + 2 <= length) {
                    builder.append(kvs[i]).append("=").append(kvs[i + 1]);
                } else {
                    builder.append(kvs[i]).append("=").append("NO DATA");
                }
                builder.append("    ");
            }
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        System.out.println(Info("test", "id", 1, "name", "小明", "age"));
    }
}
