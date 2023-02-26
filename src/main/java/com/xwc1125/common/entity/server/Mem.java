package com.xwc1125.common.entity.server;

import com.xwc1125.common.util.math.ArithUtils;

/**
 * @Description: 內存相关信息
 * @Author: xwc1125
 * @Date: 2019-02-21 14:29
 * @Copyright Copyright@2019
 */
public class Mem {
    /**
     * 内存总量
     */
    private double total;

    /**
     * 已用内存
     */
    private double used;

    /**
     * 剩余内存
     */
    private double free;

    public double getTotal() {
        return ArithUtils.div(total, (1024 * 1024 * 1024), 2);
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public double getUsed() {
        return ArithUtils.div(used, (1024 * 1024 * 1024), 2);
    }

    public void setUsed(long used) {
        this.used = used;
    }

    public double getFree() {
        return ArithUtils.div(free, (1024 * 1024 * 1024), 2);
    }

    public void setFree(long free) {
        this.free = free;
    }

    public double getUsage() {
        return ArithUtils.mul(ArithUtils.div(used, total, 4), 100);
    }
}
