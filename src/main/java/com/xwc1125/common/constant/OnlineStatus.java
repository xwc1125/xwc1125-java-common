package com.xwc1125.common.constant;

/**
 * @Description: 用户会话
 * @Author: xwc1125
 * @Date: 2019-02-21 14:29
 * @Copyright Copyright@2019
 */
public enum OnlineStatus {
    /**
     * 用户状态
     */
    on_line("在线"), off_line("离线");

    private final String info;

    private OnlineStatus(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
