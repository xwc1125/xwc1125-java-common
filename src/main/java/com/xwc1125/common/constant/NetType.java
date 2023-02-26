package com.xwc1125.common.constant;

/**
 * @Description: 网络类型
 * @Author: xwc1125
 * @Date: 2019-02-21 14:30
 * @Copyright Copyright@2019
 */
public enum NetType {
    /**
     * 未知运营商：-1
     */
    UNKNOWN(-1, "UNKNOWN"),
    /**
     * WIFI：0
     */
    WIFI(0, "WIFI"),
    /**
     * NET：1
     */
    NET(1, "NET");
    public final int value;
    public final String msg;

    NetType(int value, String msg) {
        this.value = value;
        this.msg = msg;
    }
}
