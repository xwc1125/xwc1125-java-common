package com.xwc1125.common.entity;

import com.xwc1125.common.util.json.JsonUtils;

/**
 * sdk 信息
 *
 * @author xwc1125
 * @date 2016年3月24日下午5:47:40
 */
public class SdkInfo implements java.io.Serializable {

    /**
     * sdk版本号
     */
    private int c;
    /**
     * sdk名称
     */
    private String n;
    /**
     * sdk版本名称
     */
    private String v;
    /**
     * sdk 定制方
     */
    private String cm;
    /**
     * 随机码;
     */
    private Long r;

    public SdkInfo() {
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getCm() {
        return cm;
    }

    public void setCm(String cm) {
        this.cm = cm;
    }

    public Long getR() {
        return r;
    }

    public void setR(Long r) {
        this.r = r;
    }

    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }
}
