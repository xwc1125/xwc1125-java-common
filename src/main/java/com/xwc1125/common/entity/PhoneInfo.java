package com.xwc1125.common.entity;

import com.xwc1125.common.util.json.JsonUtils;

import java.util.List;

/**
 * 手机相关信息
 *
 * @author xwc1125
 * @date 2016年3月24日下午3:46:39
 */
public class PhoneInfo implements java.io.Serializable {

    /**
     * 随机码
     */
    private Long r;
    /**
     * mac地址
     */
    private String ma;
    /**
     * imei
     */
    private List<String> ies;
    /**
     * sim信息
     */
    private List<SimInfo> smis;
    /**
     * 是否使用的List：0:false，1:true
     */
    private int isList;

    public PhoneInfo() {
    }

    public Long getR() {
        return r;
    }

    public void setR(Long r) {
        this.r = r;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public List<String> getIes() {
        return ies;
    }

    public void setIes(List<String> ies) {
        this.ies = ies;
    }

    public List<SimInfo> getSmis() {
        return smis;
    }

    public void setSmis(List<SimInfo> smis) {
        this.smis = smis;
    }

    public int getIsList() {
        return isList;
    }

    public void setIsList(int isList) {
        this.isList = isList;
    }

    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }
}
