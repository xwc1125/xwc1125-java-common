package com.xwc1125.common.constant;

import com.xwc1125.common.util.string.StringUtils;

/**
 * @Description: 客户端类型：Android：0，IOS：1，WEB：2，WINDOWS：3，MACBOOK：4，LINUX：5
 * @Author: xwc1125
 * @Date: 2019-02-21 14:28
 * @Copyright Copyright@2019
 */
public enum ClientOsType {
    /**
     * UNKNOWN：-1
     */
    UNKNOWN(-1, "UNKNOWN"),
    /**
     * Android：0
     */
    ANDROID(0, "ANDROID"),
    /**
     * IOS：1
     */
    IOS(1, "iOS"),
    /**
     * WEB：2
     */
    WEB(2, "WEB"),
    /**
     * WINDOWS：3
     */
    WINDOWS(3, "WINDOWS"),
    /**
     * MACBOOK：4
     */
    MACBOOK(4, "MACBOOK"),
    /**
     * LINUX：5
     */
    LINUX(5, "LINUX");
    public final int value;
    public final String msg;

    ClientOsType(int value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public static ClientOsType getOsTypeByName(String name) {
        if (StringUtils.isEmpty(name)) {
            return UNKNOWN;
        }
        if (ANDROID.msg.equalsIgnoreCase(name)) {
            return ANDROID;
        } else if (IOS.msg.equalsIgnoreCase(name)) {
            return IOS;
        } else if (WEB.msg.equalsIgnoreCase(name)) {
            return WEB;
        } else if (WINDOWS.msg.equalsIgnoreCase(name)) {
            return WINDOWS;
        } else if (MACBOOK.msg.equalsIgnoreCase(name)) {
            return MACBOOK;
        } else if (LINUX.msg.equalsIgnoreCase(name)) {
            return LINUX;
        } else {
            return UNKNOWN;
        }
    }

    public static ClientOsType getOsType(String valueStr) {
        int value = -1;
        if (StringUtils.isEmpty(valueStr)) {
            return getOsType(value);
        }
        try {
            value = Integer.parseInt(valueStr);
            return getOsType(value);
        } catch (Exception e) {
            return getOsType(value);
        }
    }

    public static ClientOsType getOsType(int value) {
        if (value == ANDROID.value) {
            return ANDROID;
        } else if (value == IOS.value) {
            return IOS;
        } else if (value == WEB.value) {
            return WEB;
        } else if (value == WINDOWS.value) {
            return WINDOWS;
        } else if (value == MACBOOK.value) {
            return MACBOOK;
        } else if (value == LINUX.value) {
            return LINUX;
        } else {
            return UNKNOWN;
        }
    }
}
