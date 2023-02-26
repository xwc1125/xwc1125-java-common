package com.xwc1125.common.entity.server;

import com.xwc1125.common.util.math.ArithUtils;

/**
 * @Description: CPU相关信息
 * @Author: xwc1125
 * @Date: 2019-02-21 14:29
 * @Copyright Copyright@2019
 */
public class Cpu {
    /**
     * 核心数
     */
    private int cpuNum;

    /**
     * CPU总的使用率
     */
    private double total;

    /**
     * CPU系统使用率
     */
    private double sys;

    /**
     * CPU用户使用率
     */
    private double used;

    /**
     * CPU当前等待率
     */
    private double wait;

    /**
     * CPU当前空闲率
     */
    private double free;

    public int getCpuNum() {
        return cpuNum;
    }

    public void setCpuNum(int cpuNum) {
        this.cpuNum = cpuNum;
    }

    public double getTotal() {
        return ArithUtils.round(ArithUtils.mul(total, 100), 2);
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getSys() {
        return ArithUtils.round(ArithUtils.mul(sys / total, 100), 2);
    }

    public void setSys(double sys) {
        this.sys = sys;
    }

    public double getUsed() {
        return ArithUtils.round(ArithUtils.mul(used / total, 100), 2);
    }

    public void setUsed(double used) {
        this.used = used;
    }

    public double getWait() {
        return ArithUtils.round(ArithUtils.mul(wait / total, 100), 2);
    }

    public void setWait(double wait) {
        this.wait = wait;
    }

    public double getFree() {
        return ArithUtils.round(ArithUtils.mul(free / total, 100), 2);
    }

    public void setFree(double free) {
        this.free = free;
    }
}
